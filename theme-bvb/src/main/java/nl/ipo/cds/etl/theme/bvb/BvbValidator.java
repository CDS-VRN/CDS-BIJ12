
package nl.ipo.cds.etl.theme.bvb;

import static nl.ipo.cds.etl.theme.bvb.Message.ATTRIBUTE_EMPTY;
import static nl.ipo.cds.etl.theme.bvb.Message.ATTRIBUTE_NULL;
import static nl.ipo.cds.etl.theme.bvb.Message.GEOMETRY_POINT_REQUIRED;
import static nl.ipo.cds.etl.theme.bvb.Message.GEOMETRY_SRS_NOT_RD;
import static nl.ipo.cds.etl.theme.bvb.Message.GEOMETRY_SRS_NULL;

import java.util.Map;

import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.AbstractValidator;
import nl.ipo.cds.validation.AttributeExpression;
import nl.ipo.cds.validation.ValidationReporter;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.execute.CompilerException;
import nl.ipo.cds.validation.geometry.GeometryExpression;
import nl.ipo.cds.validation.gml.codelists.CodeListFactory;

import org.deegree.geometry.Geometry;

public class BvbValidator extends AbstractValidator<Bvb, Message, Context> {

	private final AttributeExpression<Message, Context, Integer> identificatie = intAttr("identificatie");

	private final AttributeExpression<Message, Context, Integer> gemeente = intAttr("gemeente");

	private final AttributeExpression<Message, Context, String> dossier = stringAttr("dossier");

	private final AttributeExpression<Message, Context, String> bedrijfsPchn = stringAttr("bedrijfsPchn");

	private final AttributeExpression<Message, Context, String> gebouwnummer = stringAttr("gebouwnummer");

	private final AttributeExpression<Message, Context, String> emissienummer = stringAttr("emissienummer");
	
	private final AttributeExpression<Message, Context, String> stalnummer = stringAttr("stalnummer");	

	private final GeometryExpression<Message, Context, Geometry> geometry = geometry("geometry");

	private final AttributeExpression<Message, Context, String> herkomstXy = stringAttr("herkomstXy");
	
	private final AttributeExpression<Message, Context, String> beschikking = stringAttr("beschikking");	

	private final AttributeExpression<Message, Context, String> datumBesluit = stringAttr("datumBesluit");

	private final AttributeExpression<Message, Context, String> status = stringAttr("status");

	private final AttributeExpression<Message, Context, String> diercategorie = stringAttr("diercategorie");

	private final AttributeExpression<Message, Context, String> ravCode = stringAttr("ravCode");
	
	private final AttributeExpression<Message, Context, String> additioneleTechniekRav = stringAttr("additioneleTechniekRav");

	private final AttributeExpression<Message, Context, Integer> dieraantal = intAttr("dieraantal");

	private final AttributeExpression<Message, Context, Double> nh3Factor = doubleAttr("nh3Factor");

	private final AttributeExpression<Message, Context, Double> gebouwhoogte = doubleAttr("gebouwhoogte");

	private final AttributeExpression<Message, Context, Double> diameter = doubleAttr("diameter");

	private final AttributeExpression<Message, Context, Double> schoorsteenhoogte = doubleAttr("schoorsteenhoogte");

	private final AttributeExpression<Message, Context, String> herkomstEpInfo = stringAttr("herkomstEpInfo");

	public BvbValidator(final Map<Object, Object> validatorMessages) throws CompilerException {
		super(Context.class, Bvb.class, validatorMessages);
		compile();			
	}

	@Override
	public Context beforeJob(final EtlJob job,
			final CodeListFactory codeListFactory,
			final ValidationReporter<Message, Context> reporter) {
		return new Context(codeListFactory, reporter);
	}

	public Validator<Message, Context> getIdentificatieValidator() {
		return getNotNullValidator(identificatie);
	}

	public Validator<Message, Context> getGemeenteValidator() {
		return getNotNullValidator(gemeente);		
	}

	public Validator<Message, Context> getDossierValidator() {
		return getNullOrNotEmptyValidator(dossier);		
	}

	public Validator<Message, Context> getBedrijfsPchnValidator() {
		return getNotNullAndNotEmptyValidator(bedrijfsPchn);		
	}

	public Validator<Message, Context> getGebouwnummerValidator() {
		return getNullOrNotEmptyValidator(gebouwnummer);	
	}

	public Validator<Message, Context> getEmissienummerValidator() {
		return getNullOrNotEmptyValidator(emissienummer);
	}

	public Validator<Message, Context> getStalnummerValidator() {
		return getNullOrNotEmptyValidator(stalnummer);
	}
	
	public Validator<Message, Context> getGeometryValidator() {
		return validate(
			and(
				validate(not(geometry.isNull())).message(ATTRIBUTE_NULL, constant(geometry.name)),
				validate(geometry.isPoint()).message(GEOMETRY_POINT_REQUIRED, constant(geometry.name)),				
				validate(geometry.hasSrs()).message(GEOMETRY_SRS_NULL),
				validate(geometry.isSrs(constant("28992"))).message(GEOMETRY_SRS_NOT_RD, geometry.srsName()
			)
		).shortCircuit());
	}

	public Validator<Message, Context> getHerkomstXyValidator() {
		return getNotNullAndNotEmptyValidator(herkomstXy);
	}

	public Validator<Message, Context> getDatumBesluitValidator() {
		return getNullOrNotEmptyValidator(datumBesluit);		
	}

	public Validator<Message, Context> getStatusValidator() {
		return getNullOrNotEmptyValidator(status);		
	}

	public Validator<Message, Context> getDiercategorieValidator() {
		return getNullOrNotEmptyValidator(diercategorie);
	}

	public Validator<Message, Context> getRavCodeValidator() {
		return getNullOrNotEmptyValidator(ravCode);
	}

	public Validator<Message, Context> getAdditioneleTechniekRavValidator() {
		return getNullOrNotEmptyValidator(additioneleTechniekRav);
	}	
	
	public Validator<Message, Context> getHerkomstEpInfoValidator() {
		return getNullOrNotEmptyValidator(herkomstEpInfo);
	}

	private Validator<Message, Context> getNotNullValidator(AttributeExpression<Message, Context, ?> attr) {
		return validate(not(attr.isNull())).message(ATTRIBUTE_NULL, constant(attr.name));
	}

	private Validator<Message, Context> getNotNullAndNotEmptyValidator(AttributeExpression<Message, Context, String> attr) {
		return validate(
			and (
				validate (not(attr.isNull())).message(ATTRIBUTE_NULL, constant(attr.name)),
			    validate (not(isBlank(attr))).message(ATTRIBUTE_EMPTY, constant(attr.name))
			).shortCircuit()
		);
	}
	
	private Validator<Message, Context> getNullOrNotEmptyValidator(AttributeExpression<Message, Context, String> attr) {
		return validate(
			ifExp (
				attr.isNull(),
			    constant(true),
			    validate (not(isBlank(attr))).message(ATTRIBUTE_EMPTY, constant(attr.name))
			)
		);
	}
	
}
