package nl.ipo.cds.etl.theme.vrn.validation;

import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_BRONHOUDER;
import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_DOEL_REALISATIE;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.sql.DataSource;

import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.domain.ImportJob;
import nl.ipo.cds.domain.ValidateJob;
import nl.ipo.cds.etl.AbstractValidator;
import nl.ipo.cds.etl.postvalidation.IBulkValidator;
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
import nl.ipo.cds.validation.domain.OverlapValidationPair;
import nl.ipo.cds.validation.execute.CompilerException;
import nl.ipo.cds.validation.geometry.GeometryExpression;
import nl.ipo.cds.validation.gml.CodeExpression;
import nl.ipo.cds.validation.gml.codelists.CodeList;
import nl.ipo.cds.validation.gml.codelists.CodeListException;
import nl.ipo.cds.validation.gml.codelists.CodeListFactory;
import nl.ipo.cds.validation.logical.AndExpression;

import org.deegree.commons.uom.Measure;
import org.deegree.geometry.Geometry;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author annes
 * 
 *         Base class for IMNa validation. Specifies the validations that are required for all IMNa themes
 * @param <T>
 */
public abstract class AbstractVrnValidator<T extends AbstractGebied> extends AbstractValidator<T, Message, Context> {
	
	protected final Constant<Message, Context, String> doelRealisatieCodeSpace = constant(CODESPACE_DOEL_REALISATIE);

	private static final String METER = "urn:ogc:def:uom:EPSG:6.3:9001";

	private IGeometryStore<AbstractGebied> geometryStore;

	private IBulkValidator<AbstractGebied> bulkValidator;

	private ManagerDao managerDao;

	@Inject
	public void setGeometryStore(IGeometryStore<AbstractGebied> geometryStore) {
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

		final UnaryCallback<Message, Context, Boolean, AbstractGebied> saveFeatureCallback = new UnaryCallback<Message, Context, Boolean, AbstractGebied>() {

			/**
			 * Each feature will get inserted into the GeoDB (H2) database.
			 */
			@Override
			public Boolean call(final AbstractGebied abstractGebied, final Context context) throws Exception {

				// Only import and validate jobs have a geometrystore allocated when reaching this code.
				// Other job types have a NULL data source.
				if ((context.getDataSource() != null) && (abstractGebied.getGeometrie()!=null) ) {
					geometryStore.addToStore(context.getDataSource(), abstractGebied.getGeometrie(), abstractGebied);
				}
				return true;
			}
		};

		/**
		 * Build expression which has the sole purpose of inserting the feature into the feature store.
		 */
		return validate(callback(Boolean.class, abstractGebiedExpression, saveFeatureCallback));
	}

	/**
	 * Overlap validation hook after job.
	 */
	@Override
	public void afterJob(final EtlJob job, final Reporter reporter,
			final Context context) {

		// Only Import and Validate jobs do overlap validation (and have a geometry store saved on disk when reaching
		// this code).
		// This check is redundant because other types of jobs do not run through this code. But it is safer to be on
		// the defense.
		if (!(job instanceof ImportJob || job instanceof ValidateJob)) {
			return;
		}

        try {
            List<OverlapValidationPair<AbstractGebied>> overlapList = bulkValidator
                    .overlapValidation(context.getDataSource());
            if (!overlapList.isEmpty()) {
                for (OverlapValidationPair<AbstractGebied> overlap : overlapList) {
                    reporter.logEvent(context, Message.OVERLAP_DETECTED, overlap.f1.getId(), overlap.f1.getIdentificatie(), overlap.f2.getId(), overlap.f2.getIdentificatie());
                }
            }
        } catch (ClassNotFoundException | IOException | SQLException e) {
            reporter.logEvent(context, Message.OVERLAP_DETECTION_FAILED, e.getMessage());
        } finally {
            geometryStore.destroyStore(context.getDataSource());
		}
	}

	@Inject
	public void setBulkValidator(IBulkValidator<AbstractGebied> bulkValidator) {
		this.bulkValidator = bulkValidator;
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
