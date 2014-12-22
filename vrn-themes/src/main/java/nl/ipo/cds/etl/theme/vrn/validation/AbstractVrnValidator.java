package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.AbstractValidator;
import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.AbstractGebied;
import nl.ipo.cds.validation.AttributeExpression;
import nl.ipo.cds.validation.ValidationReporter;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.constants.Constant;
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
public class AbstractVrnValidator<T extends AbstractGebied> extends AbstractValidator<T, Message, Context> {

	private final GeometryExpression<Message, Context, Geometry> geometrie = geometry("geometrie");

	private final Constant<Message, Context, String> doelRealisatieCodeSpace = constant("http://codeList/");
	private final Constant<Message, Context, String> bronhouderCodeSpace = constant("");
	private final Constant<Message, Context, String> typeBeheerderEnEigenaarCodeSpace = constant("");

	private final AttributeExpression<Message, Context, String> beginTijd = stringAttr("BeginTijd");
	private final AttributeExpression<Message, Context, String> eindTijd = stringAttr("EindTijd");
	private final AttributeExpression<Message, Context, String> identificatie = stringAttr("Identificatie");
	private final AttributeExpression<Message, Context, String> relatieNummer = stringAttr("RelatieNummer");
	private final AttributeExpression<Message, Context, String> contractNummer = stringAttr("ContractNummer");
	/**
	 * codelijst doel realisatie is voor zowel doelbeheer als doelverwerving als doelinrichting
	 */
	private final CodeExpression<Message, Context> doelRealisatie = code("DoelRealisatie");
	private final CodeExpression<Message, Context> bronhouder = code("bronhouder");
	private final CodeExpression<Message, Context> typeBeheerderEnEigenaar = code("typeBeheerderEnEigenaar");

	
	
	public AbstractVrnValidator(final Map<Object, Object> validatorMessages, Class<T> clazz) throws CompilerException {
		super(Context.class, clazz, validatorMessages);
		compile();
	}

	@Override
	public Context beforeJob(final EtlJob job, final CodeListFactory codeListFactory, final ValidationReporter<Message, Context> reporter) {
		return new Context(codeListFactory, reporter);
	}

	/*
	 * TODO: IMNa validatie op attribuut, type, kardinaliteit, codeList (xsd validatie)
	 */

	public Validator<Message, Context> getBeginTijdValidator(){
		return validate(ifExp(beginTijd.isNull(), constant(true), 
		validate(not(isBlank(beginTijd.value()))).message(Message.ATTRIBUTE_EMPTY)));
	}
	
	public Validator<Message, Context> getEindTijdValidator(){
		return validate(ifExp(eindTijd.isNull(), constant(true), 
		validate(not(isBlank(eindTijd.value()))).message(Message.ATTRIBUTE_EMPTY)));
	}
	
	public Validator<Message, Context> getIdentificatieValidator(){
		return validate(ifExp(identificatie.isNull(), constant(true), 
		validate(not(isBlank(identificatie.value()))).message(Message.ATTRIBUTE_EMPTY)));
	}
	
	public Validator<Message, Context> getRelatieNummerValidator(){
		return validate(ifExp(relatieNummer.isNull(), constant(true), 
		validate(not(isBlank(relatieNummer.value()))).message(Message.ATTRIBUTE_EMPTY)));
	}
	
	public Validator<Message, Context> getContractNummerValidator(){
		return validate(ifExp(contractNummer.isNull(), constant(true), 
		validate(not(isBlank(contractNummer.value()))).message(Message.ATTRIBUTE_EMPTY)));
	}
	
	/*
	 * codeLijst validaties
	 */
	public Validator<Message, Context> getDoelRealisatieValidator() {
		return validate(ifExp(doelRealisatie.isNull(), constant(true), and(
		validate(doelRealisatie.hasCodeSpace(doelRealisatieCodeSpace)).message(Message.ATTRIBUTE_CODE_CODESPACE_INVALID, doelRealisatie.codeSpace(),
		constant(doelRealisatie.name), doelRealisatieCodeSpace),
		validate(not(isBlank(doelRealisatie.code()))).message(Message.ATTRIBUTE_EMPTY, constant(doelRealisatie.name)),
		validate(doelRealisatie.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, doelRealisatie.code(), constant(doelRealisatie.name),
		doelRealisatieCodeSpace)).shortCircuit()));
	}

	public Validator<Message, Context> getBronhouderValidator() {
		return validate(ifExp(bronhouder.isNull(), constant(true), and(
		validate(bronhouder.hasCodeSpace(bronhouderCodeSpace)).message(Message.ATTRIBUTE_CODE_CODESPACE_INVALID, bronhouder.codeSpace(),
		constant(bronhouder.name), bronhouderCodeSpace),
		validate(not(isBlank(bronhouder.code()))).message(Message.ATTRIBUTE_EMPTY, constant(bronhouder.name)),
		validate(bronhouder.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, bronhouder.code(), constant(bronhouder.name),
		bronhouderCodeSpace)).shortCircuit()));
	}
	
	public Validator<Message, Context> getTypeBeheerderEnEigenaarValidator() {
		return validate(ifExp(typeBeheerderEnEigenaar.isNull(), constant(true), and(
		validate(typeBeheerderEnEigenaar.hasCodeSpace(typeBeheerderEnEigenaarCodeSpace)).message(Message.ATTRIBUTE_CODE_CODESPACE_INVALID, typeBeheerderEnEigenaar.codeSpace(),
		constant(typeBeheerderEnEigenaar.name), typeBeheerderEnEigenaarCodeSpace),
		validate(not(isBlank(typeBeheerderEnEigenaar.code()))).message(Message.ATTRIBUTE_EMPTY, constant(typeBeheerderEnEigenaar.name)),
		validate(typeBeheerderEnEigenaar.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, typeBeheerderEnEigenaar.code(), constant(typeBeheerderEnEigenaar.name),
		typeBeheerderEnEigenaarCodeSpace)).shortCircuit()));
	}
	
	/*
	 * Valideer aanwezigheid metadata gedaan door: VerifyDataSchema.processDataset or .run
	 * 
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
	private Validator<Message, Context> getNotNullSurfaceGeometryValidator(GeometryExpression<Message, Context, Geometry> surfaceGeometry) {
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
		validate(not(surfaceGeometry.hasInteriorRingOutsideExterior())).message(Message.GEOMETRY_INTERIOR_RING_OUTSIDE_EXTERIOR, lastLocation()),
		// Nested Holes
		validate(not(surfaceGeometry.hasInteriorRingsWithin())).message(Message.GEOMETRY_INTERIOR_RINGS_WITHIN),
		// Disconnected Interior
		validate(not(surfaceGeometry.isInteriorDisconnected())).message(Message.GEOMETRY_INTERIOR_DISCONNECTED),
		// self-intersection
		validate(not(surfaceGeometry.hasCurveSelfIntersection())).message(Message.GEOMETRY_SELF_INTERSECTION),
		// Ring Self Intersection
		validate(not(surfaceGeometry.hasRingSelfIntersection())).message(Message.GEOMETRY_RING_SELF_INTERSECTION),
		// Ring Not Closed
		validate(not(surfaceGeometry.hasUnclosedRing())).message(Message.GEOMETRY_RING_NOT_CLOSED),
		validate(not(surfaceGeometry.hasCurveDiscontinuity())).message(Message.GEOMETRY_DISCONTINUITY)).shortCircuit(),
		// Invalid coordinate
		/*
		 * TODO: create validation!
		 */
		
		// SRS validations:
		and(validate(surfaceGeometry.hasSrs()).message(Message.GEOMETRY_SRS_NULL),
		validate(surfaceGeometry.isSrs(constant("28992"))).message(Message.GEOMETRY_SRS_NOT_RD, surfaceGeometry.srsName())).shortCircuit()));

	}

	/**
	 * Multiparts validation (1 deel met uniek IMNa Id per polygon)
	 */

	/**
	 * Overlap percelen validatie met H2?
	 */

}
