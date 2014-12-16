package nl.ipo.cds.etl.theme.riscisor;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import nl.ipo.cds.etl.test.GeometryConstants;
import nl.ipo.cds.etl.test.ValidationRunner;

import org.deegree.commons.tom.ows.CodeType;
import org.junit.Before;
import org.junit.Test;

public class KwetsbaarObjectValidatorTest {

	private KwetsbaarObjectValidator validator;
	private ValidationRunner<KwetsbaarObject, Message, Context> runner;
	private GeometryConstants geom;
	
	@Before
	public void createValidator () throws Exception {
		validator = new KwetsbaarObjectValidator (Collections.emptyMap ());
		runner = new ValidationRunner<> (validator, KwetsbaarObject.class);
		geom = new GeometryConstants ("EPSG:28992");
	}
	
	private ValidationRunner<KwetsbaarObject, Message, Context>.Runner run (final String validationName) {
		return runner.validation (validationName);
	}
	
	public @Test void testRisicokaartMedewerkerNaam () {
		run ("risicokaartMedewerkerNaam")
			.with (null)
			.assertOnlyKey (Message.RISICOKAART_MEDEWERKER_NAAM_NULL);
		
		run ("risicokaartMedewerkerNaam")
			.with ("")
			.assertOnlyKey (Message.RISICOKAART_MEDEWERKER_NAAM_EMPTY);
		
		run ("risicokaartMedewerkerNaam")
			.with ("a")
			.with ("b")
			.assertOnlyKey (Message.RISICOKAART_MEDEWERKER_NAAM_NOT_CONSTANT);
		
		run ("risicokaartMedewerkerNaam")
			.with ("a")
			.with ("b")
			.with ("b")
			.assertOnlyKey (Message.RISICOKAART_MEDEWERKER_NAAM_NOT_CONSTANT, 2);
		
		run ("risicokaartMedewerkerNaam")
			.with ("a")
			.with ("b")
			.with ("b")
			.with ("a")
			.assertOnlyKey (Message.RISICOKAART_MEDEWERKER_NAAM_NOT_CONSTANT, 2);
		
		run ("risicokaartMedewerkerNaam")
			.with ("a")
			.with ("a")
			.with ("a")
			.with ("a")
			.assertNoMessages ();
	}
	
	public @Test void testExternIdValidator () throws Exception {
		run ("externId")
			.with (null)
			.assertOnlyKey (Message.EXTERN_ID_NULL);
		
		run ("externId")
			.with ("")
			.assertOnlyKey (Message.EXTERN_ID_EMPTY);

		run ("externId")
			.with ("1234")
			.assertNoMessages ();
		
		run ("externId")
			.with ("1234")
			.with ("1234")
			.assertOnlyKey (Message.EXTERN_ID_DUPLICATE);
		
		run ("externId")
			.with ("abcd")
			.with ("abcd")
			.with ("1234")
			.with ("1234")
			.assertOnlyKey (Message.EXTERN_ID_DUPLICATE, 2);
		
		run ("externId")
			.with ("0123456789012345678901234567890123456789")
			.assertOnlyKey (Message.EXTERN_ID_TOO_LONG);
	}
	
	public @Test void testLaatsteMutatiedatumtijdValidator () throws Exception {
		run ("laatsteMutatiedatumtijd")
			.with (null)
			.assertOnlyKey (Message.LAATSTE_MUTATIEDATUMTIJD_NULL);
		
		run ("laatsteMutatiedatumtijd")
			.with (new Timestamp (new Date ().getTime () + 10000))
			.assertOnlyKey (Message.LAATSTE_MUTATIEDATUMTIJD_FUTURE);
		
		run ("laatsteMutatiedatumtijd")
			.with (new Timestamp (new Date ().getTime () - 10000))
			.assertNoMessages ();
		
		run ("laatsteMutatiedatumtijd")
			.with (new Timestamp (new Date ().getTime ()))
			.assertNoMessages ();
	}
	
	public @Test void testInstellingcodeValidator () throws Exception {
		run ("instellingcode")
			.withCodeList ("http://www.risicokaart.nl/codes/instellingen", "code")
			.with (null)
			.assertOnlyKey (Message.INSTELLINGCODE_NULL);
		
		run ("instellingcode")
			.withCodeList ("http://www.risicokaart.nl/codes/instellingen", "code")
			.with (new CodeType ("code"))
			.assertOnlyKey (Message.INSTELLINGCODE_INVALID_CODESPACE);
		
		run ("instellingcode")
			.withCodeList ("http://www.risicokaart.nl/codes/instellingen", "code")
			.with (new CodeType ("code", "http://www.idgis.nl"))
			.assertOnlyKey (Message.INSTELLINGCODE_INVALID_CODESPACE);
		
		run ("instellingcode")
			.withCodeList ("http://www.risicokaart.nl/codes/instellingen", "code")
			.with (new CodeType ("invalid.code", "http://www.risicokaart.nl/codes/instellingen"))
			.assertOnlyKey (Message.INSTELLINGCODE_INVALID);
		
		run ("instellingcode")
			.withCodeList ("http://www.risicokaart.nl/codes/instellingen", "code")
			.with (new CodeType ("code", "http://www.risicokaart.nl/codes/instellingen"))
			.assertNoMessages ();
	}
	
	public @Test void testNaamValidator () throws Exception {
		run ("naam")
			.with (null)
			.assertOnlyKey (Message.NAAM_NULL);
		
		run ("naam")
			.with ("")
			.assertOnlyKey (Message.NAAM_EMPTY);
		
		final char[] longName = new char[300];
		Arrays.fill (longName, 'a');
		
		run ("naam")
			.with (new String (longName))
			.assertOnlyKey (Message.NAAM_TOO_LONG);
		
		run ("naam")
			.with ("Hello, World!")
			.assertNoMessages ();
	}
	
	public @Test void testPostcodeValidator () throws Exception {
		run ("postcode")
			.with ("12345AA")
			.assertKey (Message.POSTCODE_INVALID);
		
		run ("postcode")
			.with ("1234 AA")
			.assertKey (Message.POSTCODE_INVALID);
		
		run ("postcode")
			.with ("1234aa")
			.assertKey (Message.POSTCODE_INVALID);
		
		run ("postcode")
			.with ("1234AA")
			.assertNoMessages ();
	}
	
	public @Test void testHuisnummerValidator () throws Exception {
		run ("huisnummer")
			.with (0)
			.assertOnlyKey (Message.HUISNUMMER_INVALID);
		
		run ("huisnummer")
			.with (-1)
			.assertOnlyKey (Message.HUISNUMMER_INVALID);
		
		run ("huisnummer")
			.with (123)
			.assertNoMessages ();
	}
	
	public @Test void testHuisletterValidator () throws Exception {
		run ("huisletter")
			.with ("aa")
			.assertOnlyKey (Message.HUISLETTER_INVALID);
		
		run ("huisletter")
			.with ("1")
			.assertOnlyKey (Message.HUISLETTER_INVALID);
		
		run ("huisletter")
			.with (".")
			.assertOnlyKey (Message.HUISLETTER_INVALID);
		
		run ("huisletter")
			.with ("a")
			.assertNoMessages ();
		
		run ("huisletter")
			.with ("A")
			.assertNoMessages ();
	}
	
	public @Test void testHuisnummertoevoegingValidator () throws Exception {
		run ("huisnummertoevoeging")
			.with ("")
			.assertOnlyKey (Message.HUISNUMMERTOEVOEGING_INVALID);
		
		run ("huisnummertoevoeging")
			.with (".")
			.assertOnlyKey (Message.HUISNUMMERTOEVOEGING_INVALID);
		
		run ("huisnummertoevoeging")
			.with (" abc")
			.assertOnlyKey (Message.HUISNUMMERTOEVOEGING_INVALID);
		
		run ("huisnummertoevoeging")
			.with ("abc ")
			.assertOnlyKey (Message.HUISNUMMERTOEVOEGING_INVALID);
		
		run ("huisnummertoevoeging")
			.with ("abcde")
			.assertOnlyKey (Message.HUISNUMMERTOEVOEGING_INVALID);
		
		run ("huisnummertoevoeging")
			.with ("12345")
			.assertOnlyKey (Message.HUISNUMMERTOEVOEGING_INVALID);
		
		run ("huisnummertoevoeging")
			.with ("abcd")
			.assertNoMessages ();
		
		run ("huisnummertoevoeging")
			.with ("1234")
			.assertNoMessages ();
		
		run ("huisnummertoevoeging")
			.with ("1a3b")
			.assertNoMessages ();
		
		run ("huisnummertoevoeging")
			.with ("1234")
			.assertNoMessages ();
		
		run ("huisnummertoevoeging")
			.with ("1A3B")
			.assertNoMessages ();
	}
	
	public @Test void testIdentificatiecodeNummeraanduidingBAGValidator () throws Exception {
		run ("identificatiecodeNummeraanduidingBAG")
			.with ("")
			.assertOnlyKey (Message.IDENTIFICATIECODE_NUMMERAANDUIDING_BAG_EMPTY);
		
		run ("identificatiecodeNummeraanduidingBAG")
			.with ("01234567890123456789")
			.assertOnlyKey (Message.IDENTIFICATIECODE_NUMMERAANDUIDING_BAG_TOO_LONG);
		
		run ("identificatiecodeNummeraanduidingBAG")
			.with ("01234567890123456")
			.assertOnlyKey (Message.IDENTIFICATIECODE_NUMMERAANDUIDING_BAG_TOO_LONG);
		
		run ("identificatiecodeNummeraanduidingBAG")
			.with ("0123456789012345")
			.assertNoMessages ();
		
		run ("identificatiecodeNummeraanduidingBAG")
			.with ("0")
			.assertNoMessages ();
	}
	
	public @Test void testGeometrieValidator () throws Exception {
		run ("geometrie")
			.with (geom.point (100, 400))
			.assertNoMessages ();
		
		run ("geometrie")
			.with (null)
			.assertOnlyKey (Message.COORDINAAT_NULL);
		
		run ("geometrie")
			.with (geom.point (100, 400, null))
			.assertOnlyKey (Message.COORDINAAT_NO_SRS);
		
		run ("geometrie")
			.with (geom.lineString ())
			.assertOnlyKey (Message.COORDINAAT_NOT_POINT);
		
		run ("geometrie")
			.with (geom.point (100, 400, geom.getSrs ("EPSG:900913")))
			.assertOnlyKey (Message.COORDINAAT_NOT_RD);
	}
	
	public @Test void testAutorisatiedatumValidator () throws Exception {
		// This field currently has no validation contraints.
	}
	
	public @Test void testAantalAanwezigenValidator () throws Exception {
		run ("aantalAanwezigen")
			.with (-1)
			.assertOnlyKey (Message.AANTAL_AANWEZIGEN_INVALID);
		
		run ("aantalAanwezigen")
			.with (0)
			.assertNoMessages ();
		
		run ("aantalAanwezigen")
			.with (1)
			.assertNoMessages ();
	}
	
	public @Test void testAantalBouwlagenValidator () throws Exception {
		run ("aantalBouwlagen")
			.with (-1)
			.assertOnlyKey (Message.AANTAL_BOUWLAGEN_INVALID);
		
		run ("aantalBouwlagen")
			.with (0)
			.assertNoMessages ();
		
		run ("aantalBouwlagen")
			.with (1)
			.assertNoMessages ();
	}
	
	public @Test void testPrevapcodeValidator () throws Exception {
		run ("prevapcode")
			.withCodeList ("http://www.risicokaart.nl/codes/prevapcodes", "prevap")
			.with (null)
			.assertOnlyKey (Message.PREVAPCODE_NULL);
		
		run ("prevapcode")
			.withCodeList ("http://www.risicokaart.nl/codes/prevapcodes", "prevap")
			.with (new CodeType ("prevap"))
			.assertOnlyKey (Message.PREVAPCODE_INVALID_CODESPACE);
	
		run ("prevapcode")
			.withCodeList ("http://www.risicokaart.nl/codes/prevapcodes", "prevap")
			.with (new CodeType ("prevap", "http://www.idgis.nl"))
			.assertOnlyKey (Message.PREVAPCODE_INVALID_CODESPACE);
		
		run ("prevapcode")
			.withCodeList ("http://www.risicokaart.nl/codes/prevapcodes", "prevap")
			.with (new CodeType ("invalidprevap", "http://www.risicokaart.nl/codes/prevapcodes"))
			.assertOnlyKey (Message.PREVAPCODE_INVALID);
		
		run ("prevapcode")
			.withCodeList ("http://www.risicokaart.nl/codes/prevapcodes", "prevap")
			.with (new CodeType ("invalidprevap", "http://www.risicokaart.nl/codes/prevapcodes.invalid"))
			.assertOnlyKey (Message.PREVAPCODE_INVALID_CODESPACE);
		
		run ("prevapcode")
			.withCodeList ("http://www.risicokaart.nl/codes/prevapcodes", "prevap")
			.with (new CodeType ("prevap", "http://www.risicokaart.nl/codes/prevapcodes"))
			.assertNoMessages ();
	}
	
	public @Test void testPrevapPrioValidator () throws Exception {
		run ("prevapPrio")
			.with ("0")
			.assertOnlyKey (Message.PREVAP_PRIO_INVALID);
		
		run ("prevapPrio")
			.with ("a")
			.assertOnlyKey (Message.PREVAP_PRIO_INVALID);
		
		run ("prevapPrio")
			.with ("invalid")
			.assertOnlyKey (Message.PREVAP_PRIO_INVALID);
		
		run ("prevapPrio")
			.with ("+")
			.assertOnlyKey (Message.PREVAP_PRIO_INVALID);
		
		run ("prevapPrio").with ("1").assertNoMessages ();
		run ("prevapPrio").with ("2").assertNoMessages ();
		run ("prevapPrio").with ("3").assertNoMessages ();
		run ("prevapPrio").with ("4").assertNoMessages ();
		run ("prevapPrio").with ("-").assertNoMessages ();
		run ("prevapPrio").with ("onbekend").assertNoMessages ();
	}
	
	public @Test void testNummeraanduidingOrAdresValidator () throws Exception {
		// Cannot both be null:
		run ("nummeraanduidingOrAdres")
			.withFeature ()
				.with ("identificatiecodeNummeraanduidingBAG", null)
				.with ("postcode", null)
				.with ("huisnummer", null)
				.finish ()
			.assertOnlyKey (Message.NUMMERAANDUIDING_AND_POSTCODE_NULL);

		/*
		// Nummeraanduiding is null:
		run ("nummeraanduidingOrAdres")
			.withFeature ()
				.with ("identificatiecodeNummeraanduidingBAG", null)
				.with ("postcode", "1234AA")
				.with ("huisnummer", 1)
				.finish ()
			.assertNoMessages ();
		run ("nummeraanduidingOrAdres")
			.withFeature ()
				.with ("identificatiecodeNummeraanduidingBAG", null)
				.with ("postcode", null)
				.with ("huisnummer", 1)
				.finish ()
			.assertOnlyKey (Message.POSTCODE_NULL);
		run ("nummeraanduidingOrAdres")
			.withFeature ()
				.with ("identificatiecodeNummeraanduidingBAG", null)
				.with ("postcode", "1234AA")
				.with ("huisnummer", null)
				.finish ()
			.assertOnlyKey (Message.HUISNUMMER_NULL);
		
		// Nummeraanduiding is non-null:
		run ("nummeraanduidingOrAdres")
			.withFeature ()
				.with ("identificatiecodeNummeraanduidingBAG", "abc")
				.with ("postcode", null)
				.with ("huisnummer", null)
				.with ("huisletter", null)
				.with ("huisnummertoevoeging", null)
				.finish ()
			.assertNoMessages ();
		run ("nummeraanduidingOrAdres")
			.withFeature ()
				.with ("identificatiecodeNummeraanduidingBAG", "abc")
				.with ("postcode", "1234AA")
				.with ("huisnummer", null)
				.with ("huisletter", null)
				.with ("huisnummertoevoeging", null)
				.finish ()
			.assertOnlyKey (Message.POSTCODE_MUST_BE_NULL);
		run ("nummeraanduidingOrAdres")
			.withFeature ()
				.with ("identificatiecodeNummeraanduidingBAG", "abc")
				.with ("postcode", null)
				.with ("huisnummer", 1)
				.with ("huisletter", null)
				.with ("huisnummertoevoeging", null)
				.finish ()
			.assertOnlyKey (Message.HUISNUMMER_MUST_BE_NULL);
		run ("nummeraanduidingOrAdres")
			.withFeature ()
				.with ("identificatiecodeNummeraanduidingBAG", "abc")
				.with ("postcode", null)
				.with ("huisnummer", null)
				.with ("huisletter", "a")
				.with ("huisnummertoevoeging", null)
				.finish ()
			.assertOnlyKey (Message.HUISLETTER_MUST_BE_NULL);
		run ("nummeraanduidingOrAdres")
			.withFeature ()
				.with ("identificatiecodeNummeraanduidingBAG", "abc")
				.with ("postcode", null)
				.with ("huisnummer", null)
				.with ("huisletter", null)
				.with ("huisnummertoevoeging", "abc")
				.finish ()
			.assertOnlyKey (Message.HUISNUMMERTOEVOEGING_MUST_BE_NULL);
		*/
	}
}
