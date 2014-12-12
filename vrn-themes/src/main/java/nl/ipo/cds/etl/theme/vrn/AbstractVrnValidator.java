package nl.ipo.cds.etl.theme.vrn;

import static nl.ipo.cds.etl.theme.vrn.Message.ATTRIBUTE_CODE_CODESPACE_INVALID;
import static nl.ipo.cds.etl.theme.vrn.Message.ATTRIBUTE_CODE_INVALID;
import static nl.ipo.cds.etl.theme.vrn.Message.ATTRIBUTE_EMPTY;
import static nl.ipo.cds.etl.theme.vrn.Message.ATTRIBUTE_NULL;
import static nl.ipo.cds.etl.theme.vrn.Message.GEOMETRY_DISCONTINUITY;
import static nl.ipo.cds.etl.theme.vrn.Message.GEOMETRY_EMPTY_MULTIGEOMETRY;
import static nl.ipo.cds.etl.theme.vrn.Message.GEOMETRY_EXTERIOR_RING_CW;
import static nl.ipo.cds.etl.theme.vrn.Message.GEOMETRY_INTERIOR_DISCONNECTED;
import static nl.ipo.cds.etl.theme.vrn.Message.GEOMETRY_INTERIOR_RINGS_TOUCH;
import static nl.ipo.cds.etl.theme.vrn.Message.GEOMETRY_INTERIOR_RINGS_WITHIN;
import static nl.ipo.cds.etl.theme.vrn.Message.GEOMETRY_INTERIOR_RING_CCW;
import static nl.ipo.cds.etl.theme.vrn.Message.GEOMETRY_INTERIOR_RING_TOUCHES_EXTERIOR;
import static nl.ipo.cds.etl.theme.vrn.Message.GEOMETRY_ONLY_SURFACE_OR_MULTISURFACE;
import static nl.ipo.cds.etl.theme.vrn.Message.GEOMETRY_POINT_DUPLICATION;
import static nl.ipo.cds.etl.theme.vrn.Message.GEOMETRY_RING_NOT_CLOSED;
import static nl.ipo.cds.etl.theme.vrn.Message.GEOMETRY_RING_SELF_INTERSECTION;
import static nl.ipo.cds.etl.theme.vrn.Message.GEOMETRY_SELF_INTERSECTION;
import static nl.ipo.cds.etl.theme.vrn.Message.GEOMETRY_SRS_NOT_RD;
import static nl.ipo.cds.etl.theme.vrn.Message.GEOMETRY_SRS_NULL;

import java.util.Map;

import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.AbstractValidator;
import nl.ipo.cds.validation.AttributeExpression;
import nl.ipo.cds.validation.ValidationReporter;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.constants.Constant;
import nl.ipo.cds.validation.execute.CompilerException;
import nl.ipo.cds.validation.geometry.GeometryExpression;
import nl.ipo.cds.validation.gml.CodeExpression;
import nl.ipo.cds.validation.gml.codelists.CodeListFactory;

import org.deegree.geometry.Geometry;

public class AbstractVrnValidator extends AbstractValidator<AbstractGebied, Message, Context> {

	private final AttributeExpression<Message, Context, String> inspireIdLocalId = stringAttr("inspireIdLocalId");
	private final GeometryExpression<Message, Context, Geometry> geometry = geometry("geometry");

	private final Constant<Message, Context, String> doelRealisatieCodeSpace = constant("http://codeList/");

	private final AttributeExpression<Message, Context, String> beginTijd = stringAttr("BeginTijd");
	private final AttributeExpression<Message, Context, String> eindTijd = stringAttr("EindTijd");
	private final AttributeExpression<Message, Context, String> identificatie = stringAttr("Identificatie");
	private final AttributeExpression<Message, Context, String> relatieNummer = stringAttr("RelatieNummer");
	private final AttributeExpression<Message, Context, String> contractNummer= stringAttr("ContractNummer");
	
	/**
	 * codelijst doel realisatie is voor zowel doelbeheer als verwerving als inrichting
	 */
	private final CodeExpression<Message, Context> doelRealisatie = code("DoelRealisatie");
	private final CodeExpression<Message, Context> bronhouder = code("bronhouder");
	private final CodeExpression<Message, Context> typeBeheerderEnEigenaar = code("typeBeheerderEnEigenaar");
	
	/**
	 * specifiek per type
	 */
	private final CodeExpression<Message, Context> statusVerwerving = code("StatusVerwerving");
	private final CodeExpression<Message, Context> statusIngericht = code("statusIngericht");
	private final CodeExpression<Message, Context> statusBeheer = code("statusBeheer");
	private final CodeExpression<Message, Context> beheerPakket = code("beheerPakket");

	public AbstractVrnValidator(final Map<Object, Object> validatorMessages) throws CompilerException {
		super(Context.class, AbstractGebied.class, validatorMessages);
		compile();
	}

	@Override
	public Context beforeJob(final EtlJob job, final CodeListFactory codeListFactory, final ValidationReporter<Message, Context> reporter) {
		return new Context(codeListFactory, reporter);
	}

	/**
	 * IMNa validatie op attribuut, type, kardinaliteit, codeList (xsd validatie)
	 */

	public Validator<Message, Context> getDoelRealisatieValidator() {
		return validate(ifExp(doelRealisatie.isNull(), constant(true), and(
		validate(doelRealisatie.hasCodeSpace(doelRealisatieCodeSpace)).message(ATTRIBUTE_CODE_CODESPACE_INVALID, doelRealisatie.codeSpace(),
		constant(doelRealisatie.name), doelRealisatieCodeSpace),
		validate(not(isBlank(doelRealisatie.code()))).message(ATTRIBUTE_EMPTY, constant(doelRealisatie.name)),
		validate(doelRealisatie.isValid()).message(ATTRIBUTE_CODE_INVALID, doelRealisatie.code(), constant(doelRealisatie.name),
		doelRealisatieCodeSpace)).shortCircuit()));
	}

	/**
	 * Welke validaties moeten er gedaan?
	 * 
	 */
	public Validator<Message, Context> getInspireIdLocalIdValidator() {
		return validate(and(validate(not(inspireIdLocalId.isNull())).message(Message.ATTRIBUTE_NULL, constant(inspireIdLocalId.name)),
		validate(not(isBlank(inspireIdLocalId))).message(Message.ATTRIBUTE_EMPTY, constant(inspireIdLocalId.name))).shortCircuit());
	}

	/**
	 * Valideer aanwezigheid metadata gedaan door: VerifyDataSchema.processDataset or .run
	 * 
	 */

	/**
	 * Geometrische validatie: welke geometrische beperkingen?
	 */
	public Validator<Message, Context> getGeometryValidator() {
		return getNotNullSurfaceGeometryValidator(geometry);
	}

	
	/**
	 * alleen vlakken, en alleen single polygon, alleen valide geometrie: OGC checks. FO bijlage 4, regel 3
	 * @param surfaceGeometry
	 * @return
	 */
	private Validator<Message, Context> getNotNullSurfaceGeometryValidator(GeometryExpression<Message, Context, Geometry> surfaceGeometry) {
		return validate(and(
		// The following validations short-circuit, there must be a non-empty, Surface geometry:
		validate(not(geometry.isNull())).message(ATTRIBUTE_NULL, constant(surfaceGeometry.name)),
		validate(not(surfaceGeometry.isEmptyMultiGeometry())).message(GEOMETRY_EMPTY_MULTIGEOMETRY),
		validate(surfaceGeometry.isSurfaceOrMultiSurface()).message(GEOMETRY_ONLY_SURFACE_OR_MULTISURFACE),
		// Non short-circuited validations:
		and(
		// Short circuit to prevent the interiorDisconnected validation if
		// any of the other validations fail:
		and(
		and(validate(not(surfaceGeometry.hasCurveDuplicatePoint())).message(GEOMETRY_POINT_DUPLICATION, lastLocation()), validate(
		not(surfaceGeometry.hasCurveDiscontinuity())).message(GEOMETRY_DISCONTINUITY), validate(not(surfaceGeometry.hasCurveSelfIntersection()))
		.message(GEOMETRY_SELF_INTERSECTION, lastLocation()), validate(not(surfaceGeometry.hasUnclosedRing())).message(GEOMETRY_RING_NOT_CLOSED),
		validate(not(surfaceGeometry.hasRingSelfIntersection())).message(GEOMETRY_RING_SELF_INTERSECTION, lastLocation()), validate(
		not(surfaceGeometry.hasTouchingInteriorRings())).message(GEOMETRY_INTERIOR_RINGS_TOUCH, lastLocation()), validate(
		not(surfaceGeometry.hasInteriorRingsWithin())).message(GEOMETRY_INTERIOR_RINGS_WITHIN)),
		validate(not(surfaceGeometry.isInteriorDisconnected())).message(GEOMETRY_INTERIOR_DISCONNECTED)).shortCircuit(),

		// Non-blocking validations:
		validate(not(surfaceGeometry.hasExteriorRingCW())).nonBlocking().message(GEOMETRY_EXTERIOR_RING_CW), validate(
		not(surfaceGeometry.hasInteriorRingCCW())).nonBlocking().message(GEOMETRY_INTERIOR_RING_CCW), validate(
		not(surfaceGeometry.hasInteriorRingTouchingExterior())).nonBlocking().message(GEOMETRY_INTERIOR_RING_TOUCHES_EXTERIOR, lastLocation()),
		validate(not(surfaceGeometry.hasInteriorRingOutsideExterior())).nonBlocking().message(GEOMETRY_DISCONTINUITY),

		// SRS validations:
		and(validate(surfaceGeometry.hasSrs()).message(GEOMETRY_SRS_NULL),
		validate(surfaceGeometry.isSrs(constant("28992"))).message(GEOMETRY_SRS_NOT_RD, surfaceGeometry.srsName())).shortCircuit())).shortCircuit());
	}

	/**
	 * Multiparts validation (1 deel met uniek IMNa Id per polygon)
	 */

	/**
	 * Overlap percelen validatie met H2?
	 */

}
