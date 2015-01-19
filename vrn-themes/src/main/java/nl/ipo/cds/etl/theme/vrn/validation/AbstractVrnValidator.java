package nl.ipo.cds.etl.theme.vrn.validation;

import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_BRONHOUDER;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.sql.DataSource;

import nl.idgis.commons.jobexecutor.JobLogger;
import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.AbstractValidator;
import nl.ipo.cds.etl.log.EventLogger;
import nl.ipo.cds.etl.postvalidation.IBulkValidator;
import nl.ipo.cds.etl.postvalidation.IGeometryStore;
import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.AbstractGebied;
import nl.ipo.cds.validation.AttributeExpression;
import nl.ipo.cds.validation.ValidationReporter;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.callbacks.UnaryCallback;
import nl.ipo.cds.validation.constants.Constant;
import nl.ipo.cds.validation.domain.OverlapValidationPair;
import nl.ipo.cds.validation.execute.CompilerException;
import nl.ipo.cds.validation.geometry.GeometryExpression;
import nl.ipo.cds.validation.gml.CodeExpression;
import nl.ipo.cds.validation.gml.codelists.CodeListFactory;

import org.deegree.geometry.Geometry;

/**
 * @author annes
 * 
 *         Base class for IMNa validation. Specifies the validations that are required for all IMNa themes
 * @param <T>
 */
public abstract class AbstractVrnValidator<T extends AbstractGebied> extends
		AbstractValidator<T, Message, Context> {

	@Inject
	private IGeometryStore<AbstractGebied> geometryStore;

	@Inject
	private IBulkValidator<AbstractGebied> bulkValidator;


	private final GeometryExpression<Message, Context, Geometry> geometrie = geometry("geometrie");
	private final AbstractGebiedExpression<Message, Context, AbstractGebied> abstractGebiedExpression = new AbstractGebiedExpression<>("abstractGebied", AbstractGebied.class);

	private final Constant<Message, Context, String> imnaBronhouderCodeSpace = constant(CODESPACE_BRONHOUDER);

	private final AttributeExpression<Message, Context, Timestamp> begintijd = timestampAttr("begintijd");
	private final AttributeExpression<Message, Context, String> identificatie = stringAttr("identificatie");
	/**
	 * codelijst doel realisatie is voor zowel doelbeheer als doelverwerving als doelinrichting
	 */
	//private final CodeExpression<Message, Context> doelRealisatie = code("doelRealisatie");
	private final CodeExpression<Message, Context> imnaBronhouder = code("imnaBronhouder");

	public AbstractVrnValidator(final Map<Object, Object> validatorMessages, Class<T> clazz) throws CompilerException {
		super(Context.class, clazz, validatorMessages);
	}

	@Override
	public Context beforeJob(final EtlJob job,
			final CodeListFactory codeListFactory,
			final ValidationReporter<Message, Context> reporter) {

		DataSource ds = null;

		// Both Import and Validate jobs do validation.
        try {
            ds = geometryStore.createStore(UUID.randomUUID().toString());
        } catch (SQLException e) {
            throw new RuntimeException("Error creating geometryStore: " + e);
        }

		return new Context(codeListFactory, reporter, ds);
	}

	/**
	 * Note: using the auto-mapping, it is expected to get a DATE string, which is converted to Timestamp.
	 * If a Timestamp/Datetime string is provided, the automapping will convert the string to NULL instead.
	 */
	public Validator<Message, Context> getBegintijdValidator() {
		return validate(not(begintijd.isNull())).message(Message.ATTRIBUTE_NULL, constant(begintijd.name));
	}

	/**
	 * Note: using the auto-mapping, it is expected to get a DATE string, which is converted to Timestamp.
	 * If a Timestamp/Datetime string is provided, the automapping will convert the string to NULL instead.
	 */
	public Validator<Message, Context> getEindtijdValidator() {
		// eindtijd may be null.
		return validate(constant(true));
		//return validate(not(eindtijd.isNull())).message(Message.ATTRIBUTE_NULL, constant(eindtijd.name));
	}

	public Validator<Message, Context> getIdentificatieValidator() {
		return validate(and(
				validate(not(identificatie.isNull())).message(Message.ATTRIBUTE_NULL, constant(identificatie.name)),
				validate(not(isBlank(identificatie))).message(Message.ATTRIBUTE_EMPTY, constant(identificatie.name))
		).shortCircuit());
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
				validate(not(geometrie.isPolygon())).message(Message.GEOMETRY_ONLY_SURFACE)),

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
								Message.GEOMETRY_SELF_INTERSECTION),
						// Ring Self Intersection
						validate(not(surfaceGeometry.hasRingSelfIntersection())).message(
								Message.GEOMETRY_RING_SELF_INTERSECTION),
						// Ring Not Closed
						validate(not(surfaceGeometry.hasUnclosedRing())).message(Message.GEOMETRY_RING_NOT_CLOSED),
						validate(not(surfaceGeometry.hasCurveDiscontinuity())).message(Message.GEOMETRY_DISCONTINUITY))
						.shortCircuit(),
				// Invalid coordinate
				/*
				 * TODO: create validation!
				 */

				// SRS validations:
				and(
						validate(surfaceGeometry.hasSrs()).message(Message.GEOMETRY_SRS_NULL),
						validate(surfaceGeometry.isSrs(constant("28992"))).message(Message.GEOMETRY_SRS_NOT_RD,
								surfaceGeometry.srsName())).shortCircuit()));


	}

	/**
	 * Multiparts validation (1 deel met uniek IMNa Id per polygon)
	 */
	public Validator<Message, Context> getGeometryIntersectionValidator() {

		final UnaryCallback<Message, Context, Boolean, AbstractGebied> saveFeatureCallback = new UnaryCallback<Message, Context, Boolean, AbstractGebied>() {

			/**
			 * Each feature will get inserted into the database.
			 */
			@Override
			public Boolean call(final AbstractGebied abstractGebied,
					final Context context) throws Exception {

				geometryStore.addToStore(context.getDataSource(),
						abstractGebied.getGeometrie(), abstractGebied);

				return true;
			}
		};

		/**
		 * Build expression which has the sole purpose of inserting the feature into the feature store.
		 */
		return validate(callback(Boolean.class, abstractGebiedExpression, saveFeatureCallback));
	}

	/**
	 * Overlap percelen validatie met H2?
	 */
	@Override
	public void afterJob(final EtlJob job, final EventLogger<Message> logger,
			final Context context) {

        try {
            List<OverlapValidationPair<AbstractGebied>> overlapList = bulkValidator
                    .overlapValidation(context.getDataSource());
            if (!overlapList.isEmpty()) {
                for (OverlapValidationPair<AbstractGebied> overlap : overlapList) {
                    logger.logEvent(job, Message.OVERLAP_DETECTED, JobLogger.LogLevel.ERROR, overlap.f1.getIdentificatie(), overlap.f2.getIdentificatie());
                }
            }
			geometryStore.destroyStore(context.getDataSource());
        } catch (ClassNotFoundException | IOException | SQLException e) {
            logger.logEvent(job, Message.OVERLAP_DETECTION_FAILED, JobLogger.LogLevel.ERROR, e.getMessage());
        }
	}

	public void setBulkValidator(IBulkValidator bulkValidator) {
		this.bulkValidator = bulkValidator;
	}

}
