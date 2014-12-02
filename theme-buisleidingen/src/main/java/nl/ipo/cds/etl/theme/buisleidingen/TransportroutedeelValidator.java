package nl.ipo.cds.etl.theme.buisleidingen;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.validation.AttributeExpression;
import nl.ipo.cds.validation.ValidationReporter;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.callbacks.Callback;
import nl.ipo.cds.validation.callbacks.UnaryCallback;
import nl.ipo.cds.validation.constants.Constant;
import nl.ipo.cds.validation.execute.CompilerException;
import nl.ipo.cds.validation.geometry.GeometryExpression;
import nl.ipo.cds.validation.gml.CodeExpression;
import nl.ipo.cds.validation.gml.codelists.CodeListFactory;

import org.deegree.geometry.Geometry;
import org.deegree.geometry.multi.MultiCurve;
import org.deegree.geometry.primitive.LineString;

public class TransportroutedeelValidator extends AbstractBuisleidingenValidator<Transportroutedeel> {

	private static final Set<String> buisleidingTypes = new HashSet<> (Arrays.asList (new String[] {
		"31",
		"32",
		"33",
		"34",
		"35",
		"36",
		"37"
	}));
	
	private static final Set<String> statusValues = new HashSet<> (Arrays.asList (new String[] {
		"Bestaand",
		"Gepland"
	}));
	
	private static final Set<String> effectScenarioTypes = new HashSet<> (Arrays.asList (new String[] {
		"1",
		"2",
		"3",
		"4"
	}));

	public TransportroutedeelValidator (final Map<Object, Object> validatorMessages) throws CompilerException {
		super (Transportroutedeel.class, validatorMessages);

		compile ();
	}
	
	@Override
	public Context beforeJob (final EtlJob job, final CodeListFactory codeListFactory, final ValidationReporter<Message, Context> reporter) {
		return new Context (codeListFactory, reporter);
	}
	
	// =========================================================================
	// Validation rules:
	// =========================================================================
	private final AttributeExpression<Message, Context, String> risicokaartMedewerkerNaam = stringAttr ("risicokaartMedewerkerNaam");
	private final AttributeExpression<Message, Context, String> transportrouteId = stringAttr ("transportrouteId");
	private final AttributeExpression<Message, Context, String> transportroutedeelId = stringAttr ("transportroutedeelId");
	private final AttributeExpression<Message, Context, Timestamp> laatsteMutatiedatumtijd = attribute ("laatsteMutatiedatumtijd", Timestamp.class);
	private final AttributeExpression<Message, Context, String> transportrouteNaam = stringAttr ("transportrouteNaam");
	private final AttributeExpression<Message, Context, String> omschrijving = stringAttr ("omschrijving");
	private final AttributeExpression<Message, Context, String> buisleidingType = stringAttr ("buisleidingType");
	private final AttributeExpression<Message, Context, String> naamEigenaar = stringAttr ("naamEigenaar");
	private final AttributeExpression<Message, Context, Integer> uitwendigeDiameter = intAttr ("uitwendigeDiameter");
	private final AttributeExpression<Message, Context, Integer> wanddikte = intAttr ("wanddikte");
	private final AttributeExpression<Message, Context, Integer> maximaleWerkdruk = intAttr ("maximaleWerkdruk");
	private final GeometryExpression<Message, Context, Geometry> geometrie = geometry ("geometrie");
	private final AttributeExpression<Message, Context, Double> liggingBovenkant = doubleAttr ("liggingBovenkant");
	private final AttributeExpression<Message, Context, String> materiaalsoort = stringAttr ("materiaalsoort");
	private final CodeExpression<Message, Context> casNrMaatgevendeStof = code ("casNrMaatgevendeStof");
	private final AttributeExpression<Message, Context, String> transportroutedeelToestand = stringAttr ("transportroutedeelToestand");
	private final AttributeExpression<Message, Context, Integer> effectafstandDodelijk = intAttr ("effectafstandDodelijk");
	private final AttributeExpression<Message, Context, String> maatgevendScenarioDodelijk = stringAttr ("maatgevendScenarioDodelijk");
	
	private final Constant<Message, Context, Integer> transportroutedeelIdLength = constant (30);
	private final Constant<Message, Context, Integer> omschrijvingLength = constant (240);
	private final Constant<Message, Context, Integer> naamEigenaarLength = constant (240);
	private final Constant<Message, Context, Integer> materiaalsoortLength = constant (40);
	
	public Validator<Message, Context> getRisicokaartMedewerkerNaamValidator () {
		final UnaryCallback<Message, Context, Boolean, String> isMedewerkerNaamUnique = new UnaryCallback<Message, Context, Boolean, String> () {
			@Override
			public Boolean call (final String input, final Context context) throws Exception {
				return context.isMedewerkerNaamConstant (input);
			}
		};
		
		return validate (
				and (
					validate (not (risicokaartMedewerkerNaam.isNull ())).message (Message.RISICOKAART_MEDEWERKER_NAAM_NULL),
					validate (not (isBlank (risicokaartMedewerkerNaam))).message (Message.RISICOKAART_MEDEWERKER_NAAM_EMPTY),
					
					validate (callback (Boolean.class, risicokaartMedewerkerNaam, isMedewerkerNaamUnique)).message (Message.RISICOKAART_MEDEWERKER_NAAM_NOT_CONSTANT, risicokaartMedewerkerNaam)
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getTransportroutedeelIdValidator () {
		return validate (
				and (
					validate (not (transportroutedeelId.isNull ())).message (Message.TRANSPORTROUTEDEEL_ID_NULL),
					validate (not (isBlank (transportroutedeelId))).message (Message.TRANSPORTROUTEDEEL_ID_EMPTY),
					and (
						validate (callback (Boolean.class, transportrouteId, transportroutedeelId, isUniqueCompoundKeyCallback)).message (Message.TRANSPORTROUTEDEEL_ID_DUPLICATE),
						validate (lte (length (transportroutedeelId), transportroutedeelIdLength)).message (Message.TRANSPORTROUTEDEEL_ID_TOO_LONG, transportroutedeelIdLength)
					)
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getLaatsteMutatiedatumtijdValidator () {
		final Callback<Message, Context, Date> now = new Callback<Message, Context, Date> () {
			@Override
			public Date call (final Context context) throws Exception {
				return new Date ();
			}
		};
		
		return validate (
				and (
					validate (not (laatsteMutatiedatumtijd.isNull ())).message (Message.LAATSTE_MUTATIEDATUM_NULL),
					validate (lte (laatsteMutatiedatumtijd.as (Date.class), callback (Date.class, now))).message (Message.LAATSTE_MUTATIEDATUM_FUTURE, laatsteMutatiedatumtijd)
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getTransportrouteNaamValidator () {
		return validate (
				and (
					validate (not (transportrouteNaam.isNull ())).message (Message.TRANSPORTROUTE_NAAM_NULL),
					validate (not (isBlank (transportrouteNaam))).message (Message.TRANSPORTROUTE_NAAM_EMPTY),
					validate (callback (Boolean.class, transportrouteId, transportrouteNaam, isTransportroutenaamConsistent)).message (Message.TRANSPORTROUTE_NAAM_CHANGED, transportrouteNaam)
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getOmschrijvingValidator () {
		return validate (
				and (
					validate (not (omschrijving.isNull ())).message (Message.OMSCHRIJVING_NULL),
					validate (not (isBlank (omschrijving))).message (Message.OMSCHRIJVING_EMPTY),
					validate (lte (length (omschrijving), omschrijvingLength)).message (Message.OMSCHRIJVING_TOO_LONG, omschrijvingLength)
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getBuisleidingTypeValidator () {
		return validate (
				and (
					validate (not (buisleidingType.isNull ())).message (Message.BUISLEIDING_TYPE_NULL),
					validate (in (buisleidingType, constant (buisleidingTypes))).message (Message.BUISLEIDING_TYPE_INVALID, buisleidingType)
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getNaamEigenaarValidator () {
		return validate (
				and (
					validate (not (naamEigenaar.isNull ())).message (Message.NAAM_EIGENAAR_NULL),
					validate (not (isBlank (naamEigenaar))).message (Message.NAAM_EIGENAAR_EMPTY),
					validate (lte (length (naamEigenaar), naamEigenaarLength)).message (Message.NAAM_EIGENAAR_TOO_LONG, naamEigenaarLength)
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getUitwendigeDiameterValidator () {
		return validate (
				and (
					validate (not (uitwendigeDiameter.isNull ())).message (Message.UITWENDIGE_DIAMETER_NULL),
					validate (gt (uitwendigeDiameter, constant (0))).message (Message.UITWENDIGE_DIAMETER_INVALID, uitwendigeDiameter)
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getWanddikteValidator () {
		return validate (
				and (
					validate (not (wanddikte.isNull ())).message (Message.WAND_DIKTE_NULL),
					validate (gt (wanddikte, constant (0))).message (Message.WAND_DIKTE_INVALID, wanddikte)
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getMaximaleWerkdrukValidator () {
		return validate (
				and (
					validate (not (maximaleWerkdruk.isNull ())).message (Message.MAXIMALE_WERKDRUK_NULL),
					validate (gt (maximaleWerkdruk, constant (0))).message (Message.MAXIMALE_WERKDRUK_INVALID, maximaleWerkdruk)
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getGeometrieValidator () {
		final GeometryExpression<Message, Context, Geometry> g = geometrie;
		
		return validate (
				and (
					validate (not (g.isNull ())).message (Message.GEOMETRIE_NULL),
					validate (or (g.is (LineString.class), g.is (MultiCurve.class))).message (Message.GEOMETRIE_NOT_LINESTRING),
					and (
						// SRS:
						and (
							validate (g.hasSrs ()).message (Message.GEOMETRIE_NO_SRS),
							validate (g.isSrs (constant ("28992"))).message (Message.GEOMETRIE_NOT_RD, g.srsName ())
						).shortCircuit (),
						
						validate (not (g.hasCurveDuplicatePoint ())).message (Message.GEOMETRIE_POINT_DUPLICATION, lastLocation ()),
						validate (not (g.hasCurveDiscontinuity ())).message (Message.GEOMETRIE_DISCONTINUITY),
						validate (not (g.hasCurveSelfIntersection ())).message (Message.GEOMETRIE_SELF_INTERSECTION, lastLocation ()).nonBlocking ()
					)
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getLiggingBovenkantValidator () {
		return validate (not (liggingBovenkant.isNull ())).message (Message.LIGGING_BOVENKANT_NULL);
	}
	
	public Validator<Message, Context> getMateriaalsoortValidator () {
		return validate (
				and (
					validate (not (materiaalsoort.isNull ())).message (Message.MATERIAAL_SOORT_NULL),
					validate (not (isBlank (materiaalsoort))).message (Message.MATERIAAL_SOORT_EMPTY),
					validate (lte (length (materiaalsoort), materiaalsoortLength)).message (Message.MATERIAAL_SOORT_TOO_LONG, materiaalsoortLength)
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getCasNrMaatgevendeStofValidator () {
		return validate (
				and (
					validate (not (casNrMaatgevendeStof.isNull ())).message (Message.CAS_NR_MAATGEVENDE_STOF_NULL),
					validate (not (isBlank (casNrMaatgevendeStof.code ()))).message (Message.CAS_NR_MAATGEVENDE_STOF_EMPTY),

					validate (casNrMaatgevendeStof.isValid ()).message (Message.CAS_NR_MAATGEVENDE_STOF_INVALID, casNrMaatgevendeStof.code ())
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getTransportroutedeelToestandValidator () {
		return validate (
				and (
					validate (not (transportroutedeelToestand.isNull ())).message (Message.STATUS_NULL),
					validate (in (transportroutedeelToestand, constant (statusValues))).message (Message.STATUS_INVALID, transportroutedeelToestand)
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getEffectafstandDodelijkValidator () {
		return validate (
				ifExp (
					effectafstandDodelijk.isNull (),
					constant (true),
					validate (gte (effectafstandDodelijk, constant (0))).message (Message.EFFECTAFSTAND_DODELIJK_INVALID, effectafstandDodelijk)
				)
			);
	}
	
	public Validator<Message, Context> getMaatgevendScenarioDodelijkValidator () {
		return validate (
				ifExp (
					maatgevendScenarioDodelijk.isNull (),
					constant (true),
					validate (in (maatgevendScenarioDodelijk, constant (effectScenarioTypes))).message (Message.MAATGEVEND_SCENARIO_DODELIJK_INVALID, maatgevendScenarioDodelijk)
				)
			);
	}
}
