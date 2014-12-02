package nl.ipo.cds.etl.theme.riscisor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.AbstractValidator;
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
import org.deegree.geometry.primitive.Point;

public class KwetsbaarObjectValidator extends AbstractValidator<KwetsbaarObject, Message, Context> {

	public KwetsbaarObjectValidator (final Map<Object, Object> validatorMessages) throws CompilerException {
		super (Context.class, KwetsbaarObject.class, validatorMessages);
		
		compile ();
	}
	
	@Override
	public Context beforeJob (final EtlJob job, final CodeListFactory codeListFactory, final ValidationReporter<Message, Context> reporter) {
		return new Context (codeListFactory, reporter);
	}
	
	// =========================================================================
	// Validation rules:
	// =========================================================================
	// Attributes:
	protected final AttributeExpression<Message, Context, String> risicokaartMedewerkerNaam = stringAttr ("risicokaartMedewerkerNaam");
	protected final AttributeExpression<Message, Context, String> externId = stringAttr ("externId");
	protected final AttributeExpression<Message, Context, Timestamp> laatsteMutatiedatumtijd = attribute ("laatsteMutatiedatumtijd", Timestamp.class);
	protected final CodeExpression<Message, Context> instellingcode = code ("instellingcode");
	protected final AttributeExpression<Message, Context, String> naam = stringAttr ("naam");
	protected final AttributeExpression<Message, Context, String> postcode = stringAttr ("postcode");
	protected final AttributeExpression<Message, Context, Integer> huisnummer = intAttr ("huisnummer");
	protected final AttributeExpression<Message, Context, String> huisletter = stringAttr ("huisletter");
	protected final AttributeExpression<Message, Context, String> huisnummertoevoeging = stringAttr ("huisnummertoevoeging");
	protected final AttributeExpression<Message, Context, String> identificatiecodeNummeraanduidingBAG = stringAttr ("identificatiecodeNummeraanduidingBAG");
	protected final GeometryExpression<Message, Context, Geometry> geometrie = geometry ("geometrie");
	protected final AttributeExpression<Message, Context, Date> autorisatiedatum = attribute ("autorisatiedatum", Date.class);
	protected final AttributeExpression<Message, Context, Integer> aantalAanwezigen = intAttr ("aantalAanwezigen");
	protected final AttributeExpression<Message, Context, Integer> aantalBouwlagen = intAttr ("aantalBouwlagen");
	protected final CodeExpression<Message, Context> prevapcode = code ("prevapcode");
	protected final AttributeExpression<Message, Context, String> prevapPrio = stringAttr ("prevapPrio");
	
	// Constant values:
	private final Constant<Message, Context, Integer> externIdLength = constant (36);
	private final Constant<Message, Context, Integer> naamLength = constant (240);
	private final Constant<Message, Context, Integer> identificatiecodeNummeraanduidingBAGLength = constant (16);
	private final Constant<Message, Context, String> instellingcodeCodeSpace = constant ("http://www.risicokaart.nl/codes/instellingen");
	private final Constant<Message, Context, String> prevapcodeCodeSpace = constant ("http://www.risicokaart.nl/codes/prevapcodes");
	private final Constant<Message, Context, String> postcodePattern = constant ("^[1-9][0-9]{3}[A-Z]{2}$");
	private final Constant<Message, Context, String> huisletterPattern = constant ("^[a-z,A-Z]$");
	private final Constant<Message, Context, String> huisnummertoevoegingPattern = constant ("^[a-z,A-Z,0-9]{1,4}$");
	
	// Domain values:
	private final static Set<String> mutatiesoortTypes = new HashSet<> ();
	static {
		mutatiesoortTypes.add ("T");
		mutatiesoortTypes.add ("W");
		mutatiesoortTypes.add ("V");
	}
	
	private final static Set<String> prevapPrioTypes = new HashSet<> ();
	static {
		prevapPrioTypes.add ("1");
		prevapPrioTypes.add ("2");
		prevapPrioTypes.add ("3");
		prevapPrioTypes.add ("4");
		prevapPrioTypes.add ("-");
		prevapPrioTypes.add ("onbekend");
	}
	
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
	
	public Validator<Message, Context> getExternIdValidator () {
		final UnaryCallback<Message, Context, Boolean, String> uniqueIdCallback = new UnaryCallback<Message, Context, Boolean, String> () {
			@Override
			public Boolean call (final String input, final Context context) throws Exception {
				if (context.hasId (input)) {
					return false;
				}
				
				context.addId (input);
				
				return true;
			}
		};
		
		return validate (
				and (
					validate (not (externId.isNull ())).message (Message.EXTERN_ID_NULL),
					validate (not (isBlank (externId))).message (Message.EXTERN_ID_EMPTY),
					and (
						validate (lte (length (externId), externIdLength)).message (Message.EXTERN_ID_TOO_LONG, length (externId), externIdLength),
						validate (callback (Boolean.class, externId, uniqueIdCallback)).message (Message.EXTERN_ID_DUPLICATE, externId)
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
					validate (not (laatsteMutatiedatumtijd.isNull ())).message (Message.LAATSTE_MUTATIEDATUMTIJD_NULL),
					validate (lte (laatsteMutatiedatumtijd.as (Date.class), callback (Date.class, now))).message (Message.LAATSTE_MUTATIEDATUMTIJD_FUTURE)
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getInstellingcodeValidator () {
		return validate (
				and (
					validate (not (instellingcode.isNull ())).message (Message.INSTELLINGCODE_NULL),
					validate (not (instellingcode.code ().isNull ())).message (Message.INSTELLINGCODE_NULL),
					
					validate (instellingcode.hasCodeSpace (instellingcodeCodeSpace)).message (Message.INSTELLINGCODE_INVALID_CODESPACE),

					validate (instellingcode.isValid ()).message (Message.INSTELLINGCODE_INVALID, instellingcode.code ())
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getNaamValidator () {
		return validate (
				and (
					validate (not (naam.isNull ())).message (Message.NAAM_NULL),
					validate (not (isBlank (naam))).message (Message.NAAM_EMPTY),
					validate (lte (length (naam), naamLength)).message (Message.NAAM_TOO_LONG, length (naam), naamLength)
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getPostcodeValidator () {
		return validate (
				ifExp (
					postcode.isNull (), 
					constant (true),
					validate (matches (postcode, postcodePattern)).message (Message.POSTCODE_INVALID, postcode)
				)
			);
	}
	
	public Validator<Message, Context> getHuisnummerValidator () {
		return validate (
				ifExp (
					huisnummer.isNull (),
					constant (true),
					validate (gt (huisnummer, constant (0))).message (Message.HUISNUMMER_INVALID, huisnummer) 
				)
			);
	}
	
	public Validator<Message, Context> getHuisletterValidator () {
		return validate (
				ifExp (
					huisletter.isNull (),
					constant (true),
					validate (matches (huisletter, huisletterPattern)).message (Message.HUISLETTER_INVALID, huisletter) 
				)
			);
	}
	
	public Validator<Message, Context> getHuisnummertoevoegingValidator () {
		return validate (
				ifExp (
					huisnummertoevoeging.isNull (),
					constant (true),
					validate (matches (huisnummertoevoeging, huisnummertoevoegingPattern)).message (Message.HUISNUMMERTOEVOEGING_INVALID, huisnummertoevoeging)
				)
			);
	}
	
	public Validator<Message, Context> getIdentificatiecodeNummeraanduidingBAGValidator () {
		return validate (
				ifExp (
					identificatiecodeNummeraanduidingBAG.isNull (),
					constant (true),
					and (
						validate (not (isBlank (identificatiecodeNummeraanduidingBAG))).message (Message.IDENTIFICATIECODE_NUMMERAANDUIDING_BAG_EMPTY),
						validate (lte (length (identificatiecodeNummeraanduidingBAG), identificatiecodeNummeraanduidingBAGLength)).message (Message.IDENTIFICATIECODE_NUMMERAANDUIDING_BAG_TOO_LONG, identificatiecodeNummeraanduidingBAG)
					)
				)
			);
	}
	
	public Validator<Message, Context> getGeometrieValidator () {
		return validate (
				and (
					validate (not (geometrie.isNull ())).message (Message.COORDINAAT_NULL),
					validate (geometrie.is (Point.class)).message (Message.COORDINAAT_NOT_POINT),
					validate (geometrie.hasSrs ()).message (Message.COORDINAAT_NO_SRS),
					validate (geometrie.isSrs (constant ("28992"))).message (Message.COORDINAAT_NOT_RD, geometrie.srsName ())
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getAutorisatiedatumValidator () {
		// Nothing to validate.
		return validate (constant (true));
	}
	
	public Validator<Message, Context> getAantalAanwezigenValidator () {
		return validate (
				ifExp (
					aantalAanwezigen.isNull (),
					constant (true),
					validate (gte (aantalAanwezigen, constant (0))).message (Message.AANTAL_AANWEZIGEN_INVALID, aantalAanwezigen)
				)
			);
	}
	
	public Validator<Message, Context> getAantalBouwlagenValidator () {
		return validate (
				ifExp (
					aantalBouwlagen.isNull (),
					constant (true),
					validate (gte (aantalBouwlagen, constant (0))).message (Message.AANTAL_BOUWLAGEN_INVALID, aantalBouwlagen)
				)
			);
	}
	
	public Validator<Message, Context> getPrevapcodeValidator () {
		return validate (
				and (
					validate (not (prevapcode.isNull ())).message (Message.PREVAPCODE_NULL),
					validate (not (prevapcode.code ().isNull ())).message (Message.PREVAPCODE_NULL),
					
					validate (prevapcode.hasCodeSpace (prevapcodeCodeSpace)).message (Message.PREVAPCODE_INVALID_CODESPACE),
			
					validate (prevapcode.isValid ()).message (Message.PREVAPCODE_INVALID, prevapcode.code ())
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getPrevapPrioValidator () {
		return validate (
				ifExp (
					prevapPrio.isNull (),
					constant (true),
					validate (in (prevapPrio, constant (prevapPrioTypes))).message (Message.PREVAP_PRIO_INVALID, prevapPrio)	
				)
			);
	}
	
	public Validator<Message, Context> getNummeraanduidingOrAdresValidator () {
		return validate (
				//and (
					// Both cannot be null:
					validate (not (and (identificatiecodeNummeraanduidingBAG.isNull (), postcode.isNull (), huisnummer.isNull ()))).message (Message.NUMMERAANDUIDING_AND_POSTCODE_NULL)
					
				/*
					ifExp (
						identificatiecodeNummeraanduidingBAG.isNull (),
						
						// Postcode and huisnummer must be set:
						and (
							validate (not (postcode.isNull ())).message (Message.POSTCODE_NULL),
							validate (not (huisnummer.isNull ())).message (Message.HUISNUMMER_NULL)
						),
						
						// Postcode, huisnummer and huisletter and huisnummerToevoeging must be null:
						and (
							validate (postcode.isNull ()).message (Message.POSTCODE_MUST_BE_NULL),
							validate (huisnummer.isNull ()).message (Message.HUISNUMMER_MUST_BE_NULL),
							validate (huisletter.isNull ()).message (Message.HUISLETTER_MUST_BE_NULL),
							validate (huisnummertoevoeging.isNull ()).message (Message.HUISNUMMERTOEVOEGING_MUST_BE_NULL)
						)
					)
				).shortCircuit ()
				*/
			);
	}
}
