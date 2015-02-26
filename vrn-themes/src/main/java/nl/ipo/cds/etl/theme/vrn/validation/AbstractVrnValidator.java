package nl.ipo.cds.etl.theme.vrn.validation;

import geodb.GeoDB;
import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.domain.ImportJob;
import nl.ipo.cds.domain.ValidateJob;
import nl.ipo.cds.etl.AbstractValidator;
import nl.ipo.cds.etl.postvalidation.IGeometryStore;
import nl.ipo.cds.etl.theme.vrn.Constants;
import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.AbstractGebied;
import nl.ipo.cds.validation.AbstractUnaryTestExpression;
import nl.ipo.cds.validation.AttributeExpression;
import nl.ipo.cds.validation.ValidationReporter;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.callbacks.UnaryCallback;
import nl.ipo.cds.validation.constants.Constant;
import nl.ipo.cds.validation.execute.CompilerException;
import nl.ipo.cds.validation.geometry.GeometryExpression;
import nl.ipo.cds.validation.gml.CodeExpression;
import nl.ipo.cds.validation.gml.codelists.CodeList;
import nl.ipo.cds.validation.gml.codelists.CodeListException;
import nl.ipo.cds.validation.gml.codelists.CodeListFactory;
import nl.ipo.cds.validation.logical.AndExpression;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.deegree.commons.uom.Measure;
import org.deegree.geometry.Envelope;
import org.deegree.geometry.Geometry;
import org.deegree.geometry.io.WKBWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_BRONHOUDER;
import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_DOEL_REALISATIE;

/**
 * @author annes
 * 
 *         Base class for IMNa validation. Specifies the validations that are required for all IMNa themes
 * @param <T>
 */
public abstract class AbstractVrnValidator<T extends AbstractGebied> extends AbstractValidator<T, Message, Context> {

	private static final Log technicalLog = LogFactory.getLog(AbstractVrnValidator.class);

	protected final Constant<Message, Context, String> doelRealisatieCodeSpace = constant(CODESPACE_DOEL_REALISATIE);

	private static final String METER = "urn:ogc:def:uom:EPSG:6.3:9001";

	private IGeometryStore geometryStore;


	private ManagerDao managerDao;

	@Inject
	public void setGeometryStore(IGeometryStore geometryStore) {
		this.geometryStore = geometryStore;
	}

	@Inject
	public void setManagerDao(ManagerDao managerDao) {
		this.managerDao = managerDao;
	}

	@Value("${bronhouderAreaMargin}")
	private String bronhouderAreaMargin;

	private final GeometryExpression<Message, Context, Geometry> geometrie = geometry("geometrie");
	private final AbstractGebiedExpression<Message, Context, AbstractGebied> abstractGebiedExpression = new AbstractGebiedExpression<>(
			"abstractGebied", AbstractGebied.class);

	private final Constant<Message, Context, String> imnaBronhouderCodeSpace = constant(CODESPACE_BRONHOUDER);

	private final AttributeExpression<Message, Context, Timestamp> begintijd = timestampAttr("begintijd");
	private final AttributeExpression<Message, Context, String> identificatie = stringAttr("identificatie");
	/**
	 * codelijst doel realisatie is voor zowel doelbeheer als doelverwerving als doelinrichting
	 */
	// private final CodeExpression<Message, Context> doelRealisatie = code("doelRealisatie");
	private final CodeExpression<Message, Context> imnaBronhouder = code("imnaBronhouder");

	public AbstractVrnValidator(final Map<Object, Object> validatorMessages, Class<T> clazz) throws CompilerException {
		super(Context.class, clazz, validatorMessages);
	}

	@Override
	public Context beforeJob(final EtlJob job, final CodeListFactory codeListFactory,
			final ValidationReporter<Message, Context> reporter) {

		DataSource ds = null;

		// This check is redundant because other types of jobs do not run through this code. But it is safer to be on
		// the defense.
		if (job instanceof ImportJob || job instanceof ValidateJob) {
			// Both Import and Validate jobs do validation.
			try {
				ds = geometryStore.createStore(UUID.randomUUID().toString());
			} catch (SQLException e) {
				throw new RuntimeException("Error creating geometryStore: " + e);
			}
		}

		// Can be null for "Landelijke Bronhouders".
		Geometry bronhouderGeometry = managerDao.getBronhouderGeometry(job.getBronhouder());

		return new Context(codeListFactory, reporter, ds, bronhouderGeometry);
	}

	/**
	 * Note: using the auto-mapping, it is expected to get a DATE string, which is converted to Timestamp. If a
	 * Timestamp/Datetime string is provided, the automapping will convert the string to NULL instead.
	 */
	public Validator<Message, Context> getBegintijdValidator() {
		return validate(not(begintijd.isNull())).message(Message.ATTRIBUTE_NULL, constant(begintijd.name));
	}

	/**
	 * Note: using the auto-mapping, it is expected to get a DATE string, which is converted to Timestamp. If a
	 * Timestamp/Datetime string is provided, the automapping will convert the string to NULL instead.
	 */
	public Validator<Message, Context> getEindtijdValidator() {
		// eindtijd may be null.
		return validate(constant(true));
		// return validate(not(eindtijd.isNull())).message(Message.ATTRIBUTE_NULL, constant(eindtijd.name));
	}

	public Validator<Message, Context> getIdentificatieValidator() {
		return validate(and(
				validate(not(identificatie.isNull())).message(Message.ATTRIBUTE_NULL, constant(identificatie.name)),
				validate(not(isBlank(identificatie))).message(Message.ATTRIBUTE_EMPTY, constant(identificatie.name)))
				.shortCircuit());
	}

	/*
	 * codeLijst validaties
	 */

	public Validator<Message, Context> getImnaBronhouderValidator() {
		return validate(and(
				validate(not(imnaBronhouder.isNull())).message(Message.ATTRIBUTE_NULL, constant(imnaBronhouder.name)),
				validate(not(isBlank(imnaBronhouder.code()))).message(Message.ATTRIBUTE_EMPTY,
						constant(imnaBronhouder.name)),
				validate(imnaBronhouder.hasCodeSpace(imnaBronhouderCodeSpace)).message(
						Message.ATTRIBUTE_CODE_CODESPACE_INVALID, imnaBronhouder.codeSpace(),
						constant(imnaBronhouder.name), imnaBronhouderCodeSpace),
				validate(imnaBronhouder.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, imnaBronhouder.code(),
						constant(imnaBronhouder.name), imnaBronhouderCodeSpace)).shortCircuit());
	}

	/*
	 * Valideer aanwezigheid metadata gedaan door: VerifyDataSchema.processDataset or .run
	 */

	/**
	 * Geometrische validatie: alleen (single) polygon, alleen valide geometrie. FO bijlage 4, regel 3
	 */
	public Validator<Message, Context> getGeometryValidator() {
		return getNotNullSurfaceGeometryValidator(geometrie);
	}

	/**
	 * @param surfaceGeometry
	 * @return
	 */
	private Validator<Message, Context> getNotNullSurfaceGeometryValidator(
			GeometryExpression<Message, Context, Geometry> surfaceGeometry) {
		return validate(

		and(
		// The following validations short-circuit, there must be a non-empty, Surface geometry:
		validate(not(geometrie.isNull())).message(Message.ATTRIBUTE_NULL, constant(surfaceGeometry.name),
		// Single polygon
				validate((geometrie.isSurface())).message(Message.GEOMETRY_ONLY_SURFACE)),

		// Short circuit to prevent the interiorDisconnected validation if
		// any of the other validations fail:
				and(
						// Hole Outside Shell
						validate(not(surfaceGeometry.hasInteriorRingOutsideExterior())).message(
								Message.GEOMETRY_INTERIOR_RING_OUTSIDE_EXTERIOR, lastLocation()),
						// Nested Holes
						validate(not(surfaceGeometry.hasInteriorRingsWithin())).message(
								Message.GEOMETRY_INTERIOR_RINGS_WITHIN),
						// Disconnected Interior
						validate(not(surfaceGeometry.isInteriorDisconnected())).message(
								Message.GEOMETRY_INTERIOR_DISCONNECTED),
						// self-intersection
						validate(not(surfaceGeometry.hasCurveSelfIntersection())).message(
								Message.GEOMETRY_SELF_INTERSECTION, lastLocation()),
						// Ring Self Intersection
						validate(not(surfaceGeometry.hasRingSelfIntersection())).message(
								Message.GEOMETRY_RING_SELF_INTERSECTION, lastLocation()),
						// Curve Duplicate Point
						validate(not(surfaceGeometry.hasCurveDuplicatePoint())).message(
								Message.GEOMETRY_POINT_DUPLICATION),
						// Ring Not Closed
						validate(not(surfaceGeometry.hasUnclosedRing())).message(Message.GEOMETRY_RING_NOT_CLOSED),
						validate(not(surfaceGeometry.hasCurveDiscontinuity())).message(Message.GEOMETRY_DISCONTINUITY))
						.shortCircuit(),

				// SRS validations:
				and(
						validate(surfaceGeometry.hasSrs()).message(Message.GEOMETRY_SRS_NULL),
						validate(surfaceGeometry.isSrs(constant("28992"))).message(Message.GEOMETRY_SRS_NOT_RD,
								surfaceGeometry.srsName()),
						// check invalid coordinates
						validate(surfaceGeometry.hasValidCoordinateRD()).message(Message.GEOMETRY_INVALID_COORDINATES)
				).shortCircuit()));

	}

	/**
	 * Check that the geometry of a feature is within the bounds of the bronhouder it is uploaded to/by. The margin of
	 * the bronhouder area can be specified in the property file. This is a helper method, because this check should
	 * only be done on LandelijkGebiedX, and not ProvinciaalGebiedX.
	 */
	protected Validator<Message, Context> getGeometryWithinBronhouderGeometryHelper() {

		final AbstractUnaryTestExpression<Message, Context, Geometry> geometryInBronhouderTest = new AbstractUnaryTestExpression<Message, Context, Geometry>(
				geometrie, "geometrie") {

			@Override
			public boolean test(Geometry value, Context context) {

				return context.getBronhouderGeometry() == null ||
						value.isWithin(context.getBronhouderGeometry().getBuffer(new Measure(bronhouderAreaMargin, METER)));
			}
		};

		/**
		 * Build expression which has the sole purpose of inserting the feature into the feature store.
		 */
		return validate(geometryInBronhouderTest).message(Message.GEOMETRY_OUTSIDE_BRONHOUDER_AREA);
	}

	/**
	 * Instead of validating, insert feature and its geometry into a temporary GeoDB (H2) database.
	 * 
	 * @return
	 */
	public Validator<Message, Context> getGeometryIntersectionValidator() {

		final AbstractUnaryTestExpression<Message, Context, AbstractGebied> geometryOverlapTest = new
				AbstractUnaryTestExpression<Message, Context, AbstractGebied>(abstractGebiedExpression,
						"abstractGebied") {

			/**
			 * Test the current geometry against the geometries of the features that were are already validated to
			 * see if there is overlap.
			 */
			@Override
			public boolean test(final AbstractGebied abstractGebied, final Context context) {
				Geometry geom = abstractGebied.getGeometrie();
				boolean hasOverlap = false;
				if (context.getDataSource() == null || geom == null) {
					return true;
				}

				// Extract envelope of current geometry.
				Envelope e = geom.getEnvelope();
				double x1 = e.getMin().get0();
				double x2 = e.getMax().get0();
				double y1 = e.getMin().get1();
				double y2 = e.getMax().get1();
				String sql = String.format(Locale.US,
						"select feature_identifier, feature_local_id " +
						"from geometries " +
						"where " +
								"id IN (select cast(hatbox_join_id as int) from HATBOX_MBR_INTERSECTS_ENV('PUBLIC', 'GEOMETRIES', %f, %f, %f, %f)) and " +
								"ST_Intersects(geometry,:geometry) and " +
								"not ST_Touches(geometry, :geometry)", x1, x2, y1, y2);

				try {
					NamedParameterJdbcTemplate t = new NamedParameterJdbcTemplate(context.getDataSource());

					final Map<String, Object> params = new HashMap<>();
					params.put("geometry", GeoDB.ST_GeomFromWKB(WKBWriter.write(geom), 28992));
					List<Map<String, Object>> res = t.queryForList(sql, params);

					for (Map<String, Object> row : res) {
						technicalLog.debug(String.format("Overlap detected between %s and %s", abstractGebied
								.getIdentificatie(), row.get("FEATURE_IDENTIFIER")));

						// We need to add a duplicate ID because this is removed in the Reporter for some reason.
						String[] errParams = new String[5];
						errParams[0] = abstractGebied.getId();
						errParams[1] = abstractGebied.getId();
						errParams[2] = abstractGebied.getIdentificatie();
						errParams[3] = (String)row.get("FEATURE_LOCAL_ID");
						errParams[4] = (String)row.get("FEATURE_IDENTIFIER");
						context.getReporter().reportValidationError(getGeometryIntersectionValidator(), context,
								Message.OVERLAP_DETECTED, errParams);
						hasOverlap = true;
					}
					geometryStore.addToStore(context.getDataSource(), geom, abstractGebied.getIdentificatie(),
							abstractGebied.getId());
				} catch(Exception ex) {
					throw new RuntimeException(ex);
				}
				return hasOverlap;
			}
		};
		return validate(not(geometryOverlapTest));
	}


	/**
	 * Check validity of doelRealisatie attribute. Note that the 'doel' attributes can contain multiple codes, seperated
	 * by ';' characters.
	 * 
	 * @param constantDoelBeheer
	 * @param doelBeheer 
	 * @return
	 */
	protected AndExpression<Message, Context> validateDoelRealisatie(
			final Constant<Message, Context, String> constantDoelBeheer, CodeExpression<Message, Context> doelBeheer) {
		// we need a dedicated callback to validate whether the code is valid for codetype DoelRealisatie; this cannot
		// be done by ordinary CodeType validation because we have multiple codes combined in a single string.
		final UnaryCallback<Message, Context, Boolean, String> validateDoelRealisatieCode = new UnaryCallback<Message, Context, Boolean, String>() {
			@Override
			public Boolean call(final String input, final Context context) throws Exception {
				try {

					final CodeList codeList = context.getCodeListFactory().getCodeList(
							Constants.CODESPACE_DOEL_REALISATIE);
					if (codeList == null) {
						return false;
					}

					return codeList.hasCode(input);
				} catch (CodeListException e) {
					return false;
				}
			}
		};
		return and(
		// when not null, needs to have the correct codespace
				validate(doelBeheer.hasCodeSpace(doelRealisatieCodeSpace)).message(
						Message.ATTRIBUTE_CODE_CODESPACE_INVALID, doelBeheer.codeSpace(), constant(doelBeheer.name),
						doelRealisatieCodeSpace),
				// split the code into values seperated by ';'
				validate(split(stringAttr("doelRealisatieValue"),
						constant(";"),
						// each value must not be null and have a valid code
						validate(forEach(
								"i",
								attr("values", String[].class),
								validate(and(
										validate(not(isBlank(stringAttr("i")))).message(Message.ATTRIBUTE_EMPTY,
												constantDoelBeheer),
										validate(callback(Boolean.class, stringAttr("i"), validateDoelRealisatieCode))
												.message(Message.ATTRIBUTE_CODE_INVALID, stringAttr("i"),
														constantDoelBeheer, doelRealisatieCodeSpace)).shortCircuit()))))))
				.shortCircuit();
	}

}
