package nl.ipo.cds.etl.theme.buisleidingen;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import nl.ipo.cds.etl.test.GeometryConstants;
import nl.ipo.cds.etl.test.ValidationRunner;

import org.deegree.commons.tom.ows.CodeType;
import org.junit.Before;
import org.junit.Test;

public class TransportroutedeelValidatorTest {

	private TransportroutedeelValidator validator;
	private ValidationRunner<Transportroutedeel, Message, Context> runner;
	private GeometryConstants geom;
	
	@Before
	public void createValidator () throws Exception {
		validator = new TransportroutedeelValidator (Collections.emptyMap ());
		runner = new ValidationRunner<> (validator, Transportroutedeel.class);
		geom = new GeometryConstants ("EPSG:28992");
	}
	
	private ValidationRunner<Transportroutedeel, Message, Context>.Runner run (final String validationName) {
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
	
	public @Test void testTransportrouteId () {
		run ("transportrouteId")
			.with (null)
			.assertOnlyKey (Message.TRANSPORTROUTE_ID_NULL);
		
		run ("transportrouteId")
			.with ("")
			.assertOnlyKey (Message.TRANSPORTROUTE_ID_EMPTY);
		
		run ("transportrouteId")
			.with ("0123456789012345678901234567890")
			.assertOnlyKey (Message.TRANSPORTROUTE_ID_TOO_LONG);
		
		run ("transportrouteId")
			.with ("012345678901234567890123456789")
			.assertNoMessages ();
	}
	
	public @Test void testTransportroutedeelId () {
		run ("transportroutedeelId")
			.with (null)
			.assertOnlyKey (Message.TRANSPORTROUTEDEEL_ID_NULL);
		
		run ("transportroutedeelId")
			.with ("")
			.assertOnlyKey (Message.TRANSPORTROUTEDEEL_ID_EMPTY);
		
		run ("transportroutedeelId")
			.with ("Hello, world!")
			.assertNoMessages ();
		
		run ("transportroutedeelId")
			.withFeature ()
				.with ("transportrouteId", "1")
				.with ("transportroutedeelId", "id.0")
				.finish ()
			.withFeature ()
				.with ("transportrouteId", "1")
				.with ("transportroutedeelId", "id.0")
				.finish ()
			.assertOnlyKey (Message.TRANSPORTROUTEDEEL_ID_DUPLICATE, 1);
		
		run ("transportroutedeelId")
			.withFeature ()
				.with ("transportrouteId", "1")
				.with ("transportroutedeelId", "id.0")
				.finish ()
			.withFeature ()
				.with ("transportrouteId", "2")
				.with ("transportroutedeelId", "id.0")
				.finish ()
			.assertNoMessages ();
		
		run ("transportroutedeelId")
			.withFeature ()
				.with ("transportrouteId", "1")
				.with ("transportroutedeelId", "id.0")
				.finish ()
			.withFeature ()
				.with ("transportrouteId", "1")
				.with ("transportroutedeelId", "id.0")
				.finish ()
			.withFeature ()
				.with ("transportrouteId", "1")
				.with ("transportroutedeelId", "id.0")
				.finish ()
			.withFeature ()
				.with ("transportrouteId", "1")
				.with ("transportroutedeelId", "id.1")
				.finish ()
			.withFeature ()
				.with ("transportrouteId", "1")
				.with ("transportroutedeelId", "id.2")
				.finish ()
			.assertOnlyKey (Message.TRANSPORTROUTEDEEL_ID_DUPLICATE, 2);
		
		run ("transportroutedeelId")
			.withFeature ()
				.with ("transportrouteId", "1")
				.with ("transportroutedeelId", "id.0")
				.finish ()
			.withFeature ()
				.with ("transportrouteId", "1")
				.with ("transportroutedeelId", "id.0")
				.finish ()
			.withFeature ()
				.with ("transportrouteId", "1")
				.with ("transportroutedeelId", "id.0")
				.finish ()
			.withFeature ()
				.with ("transportrouteId", "1")
				.with ("transportroutedeelId", "id.1")
				.finish ()
			.withFeature ()
				.with ("transportrouteId", "1")
				.with ("transportroutedeelId", "id.2")
				.finish ()
			.withFeature ()
				.with ("transportrouteId", "1")
				.with ("transportroutedeelId", "id.2")
				.finish ()
			.assertOnlyKey (Message.TRANSPORTROUTEDEEL_ID_DUPLICATE, 3);
		
		run ("transportroutedeelId")
			.withFeature ()
				.with ("transportrouteId", "1")
				.with ("transportroutedeelId", "id.0")
				.finish ()
			.withFeature ()
				.with ("transportrouteId", "1")
				.with ("transportroutedeelId", "id.1")
				.finish ()
			.withFeature ()
				.with ("transportrouteId", "1")
				.with ("transportroutedeelId", "id.2")
				.finish ()
			.assertNoMessages ();
		
		run ("transportroutedeelId")
			.with ("0123456789012345678901234567890")
			.assertOnlyKey (Message.TRANSPORTROUTEDEEL_ID_TOO_LONG);
		
		run ("transportroutedeelId")
			.with ("012345678901234567890123456789")
			.assertNoMessages ();
	}
	
	public @Test void testLaatsteMutatiedatumtijd () {
		run ("laatsteMutatiedatumtijd")
			.with (null)
			.assertOnlyKey (Message.LAATSTE_MUTATIEDATUM_NULL);
		
		run ("laatsteMutatiedatumtijd")
			.with (new Timestamp (new Date ().getTime ()))
			.assertNoMessages ();
		
		run ("laatsteMutatiedatumtijd")
			.with (new Timestamp (new Date ().getTime () - 10000))
			.assertNoMessages ();
		
		run ("laatsteMutatiedatumtijd")
			.with (new Timestamp (new Date ().getTime () + 10000))
			.assertOnlyKey (Message.LAATSTE_MUTATIEDATUM_FUTURE);
	}
	
	public @Test void testTransportrouteNaam () {
		run ("transportrouteNaam")
			.with (null)
			.assertOnlyKey (Message.TRANSPORTROUTE_NAAM_NULL);
		
		run ("transportrouteNaam")
			.with ("")
			.assertOnlyKey (Message.TRANSPORTROUTE_NAAM_EMPTY);
		
		run ("transportrouteNaam")
			.withFeature ()
				.with ("Naam 1")
				.with ("transportrouteId", "route.1")
				.finish ()
			.withFeature ()
				.with ("Naam 2")
				.with ("transportrouteId", "route.1")
				.finish ()
			.assertOnlyKey (Message.TRANSPORTROUTE_NAAM_CHANGED, 1);
		
		run ("transportrouteNaam")
			.withFeature ()
				.with ("Naam 1")
				.with ("transportrouteId", "route.1")
				.finish ()
			.withFeature ()
				.with ("Naam 2")
				.with ("transportrouteId", "route.1")
				.finish ()
			.withFeature ()
				.with ("Naam 2")
				.with ("transportrouteId", "route.1")
				.finish ()
			.assertOnlyKey (Message.TRANSPORTROUTE_NAAM_CHANGED, 2);
		
		run ("transportrouteNaam")
			.withFeature ()
				.with ("Naam 1")
				.with ("transportrouteId", "route.1")
				.finish ()
			.withFeature ()
				.with ("Naam 2")
				.with ("transportrouteId", "route.2")
				.finish ();
	}
	
	public @Test void testOmschrijving () {
		final char[] chars = new char[240];
		Arrays.fill (chars, 'a');
		final String longValue = new String (chars);
		
		run ("omschrijving")
			.with (null)
			.assertOnlyKey (Message.OMSCHRIJVING_NULL);
		
		run ("omschrijving")
			.with ("")
			.assertOnlyKey (Message.OMSCHRIJVING_EMPTY);
		
		run ("omschrijving")
			.with ("Hello, world!")
			.assertNoMessages ();
		
		run ("omschrijving")
			.with (longValue + "a")
			.assertOnlyKey (Message.OMSCHRIJVING_TOO_LONG);
		
		run ("omschrijving")
			.with (longValue)
			.assertNoMessages ();
	}
	
	public @Test void testBuisleidingType () {
		run ("buisleidingType")
			.with (null)
			.assertOnlyKey (Message.BUISLEIDING_TYPE_NULL);
		
		run ("buisleidingType")
			.with ("42")
			.assertOnlyKey (Message.BUISLEIDING_TYPE_INVALID);
		
		run ("buisleidingType")
			.with ("31")
			.assertNoMessages ();
	}
	
	public @Test void testNaamEigenaar () {
		final char[] chars = new char[240];
		Arrays.fill (chars, 'a');
		final String longValue = new String (chars);
		
		run ("naamEigenaar")
			.with (null)
			.assertOnlyKey (Message.NAAM_EIGENAAR_NULL);
		
		run ("naamEigenaar")
			.with ("")
			.assertOnlyKey (Message.NAAM_EIGENAAR_EMPTY);
		
		run ("naamEigenaar")
			.with ("Hello, world!")
			.assertNoMessages ();
		
		run ("naamEigenaar")
			.with (longValue + "a")
			.assertOnlyKey (Message.NAAM_EIGENAAR_TOO_LONG);
		
		run ("naamEigenaar")
			.with (longValue)
			.assertNoMessages ();
	}
	
	public @Test void testUitwendigeDiameter () {
		run ("uitwendigeDiameter")
			.with (null)
			.assertOnlyKey (Message.UITWENDIGE_DIAMETER_NULL);
		
		run ("uitwendigeDiameter")
			.with (0)
			.assertOnlyKey (Message.UITWENDIGE_DIAMETER_INVALID);
		
		run ("uitwendigeDiameter")
			.with (-1)
			.assertOnlyKey (Message.UITWENDIGE_DIAMETER_INVALID);
		
		run ("uitwendigeDiameter")
			.with (1)
			.assertNoMessages ();
	}
	
	public @Test void testWanddikte () {
		run ("wanddikte")
			.with (null)
			.assertOnlyKey (Message.WAND_DIKTE_NULL);
		
		run ("wanddikte")
			.with (0)
			.assertOnlyKey (Message.WAND_DIKTE_INVALID);
		
		run ("wanddikte")
			.with (-1)
			.assertOnlyKey (Message.WAND_DIKTE_INVALID);
		
		run ("wanddikte")
			.with (1)
			.assertNoMessages ();
	}
	
	public @Test void testMaximaleWerkdruk () {
		run ("maximaleWerkdruk")
			.with (null)
			.assertOnlyKey (Message.MAXIMALE_WERKDRUK_NULL);
			
		run ("maximaleWerkdruk")
			.with (0)
			.assertOnlyKey (Message.MAXIMALE_WERKDRUK_INVALID);
		
		run ("maximaleWerkdruk")
			.with (-1)
			.assertOnlyKey (Message.MAXIMALE_WERKDRUK_INVALID);
		
		run ("maximaleWerkdruk")
			.with (1)
			.assertNoMessages ();
	}
	
	public @Test void testGeometrie () throws Exception {
		run ("geometrie")
			.with (null)
			.assertOnlyKey (Message.GEOMETRIE_NULL);
		
		run ("geometrie")
			.with (geom.point (0, 0))
			.assertOnlyKey (Message.GEOMETRIE_NOT_LINESTRING);
		
		run ("geometrie")
			.with (geom.lineString (null))
			.assertOnlyKey (Message.GEOMETRIE_NO_SRS);
		
		run ("geometrie")
			.with (geom.lineString (geom.getSrs ("EPSG:3857")))
			.assertOnlyKey (Message.GEOMETRIE_NOT_RD);
		
		run ("geometrie")
			.with (geom.lineString ())
			.assertNoMessages ();
		
		run ("geometrie")
			.with (geom.lineStringDuplicatePoint ())
			.assertOnlyKey (Message.GEOMETRIE_POINT_DUPLICATION);
		
		run ("geometrie")
			.with (geom.lineStringSelfIntersection ())
			.assertOnlyKey (Message.GEOMETRIE_SELF_INTERSECTION);
	}
	
	public @Test void testLiggingBovenkant () {
		run ("liggingBovenkant")
			.with (null)
			.assertOnlyKey (Message.LIGGING_BOVENKANT_NULL);
		
		run ("liggingBovenkant")
			.with (-1.0)
			.assertNoMessages ();
		
		run ("liggingBovenkant")
			.with (0.0)
			.assertNoMessages ();
		
		run ("liggingBovenkant")
			.with (1.0)
			.assertNoMessages ();
	}
	
	public @Test void testMateriaalsoort () {
		run ("materiaalsoort")
			.with (null)
			.assertOnlyKey (Message.MATERIAAL_SOORT_NULL);
		
		run ("materiaalsoort")
			.with ("")
			.assertOnlyKey (Message.MATERIAAL_SOORT_EMPTY);
		
		run ("materiaalsoort")
			.with ("Hello, World!")
			.assertNoMessages ();
		
		run ("materiaalsoort")
			.with ("01234567890123456789012345678901234567890")
			.assertOnlyKey (Message.MATERIAAL_SOORT_TOO_LONG);
		
		run ("materiaalsoort")
			.with ("0123456789012345678901234567890123456789")
			.assertNoMessages ();
	}
	
	public @Test void testCasNrMaatgevendeStof () {
		run ("casNrMaatgevendeStof")
			.withCodeList ("http://www.risicokaart.nl/codes/stoffen", "valid")
			.with (null)
			.assertOnlyKey (Message.CAS_NR_MAATGEVENDE_STOF_NULL);
		
		run ("casNrMaatgevendeStof")
			.withCodeList ("http://www.risicokaart.nl/codes/stoffen", "valid")
			.with (new CodeType ("", "http://www.risicokaart.nl/codes/stoffen"))
			.assertOnlyKey (Message.CAS_NR_MAATGEVENDE_STOF_EMPTY);
		
		run ("casNrMaatgevendeStof")
			.withCodeList ("http://www.risicokaart.nl/codes/stoffen", "valid")
			.with (new CodeType ("invalid", "http://www.risicokaart.nl/codes/stoffen"))
			.assertOnlyKey (Message.CAS_NR_MAATGEVENDE_STOF_INVALID);
		
		run ("casNrMaatgevendeStof")
			.withCodeList ("http://www.risicokaart.nl/codes/stoffen", "valid")
			.with (new CodeType ("valid", "http://www.risicokaart.nl/codes/invalidStoffen"))
			.assertOnlyKey (Message.CAS_NR_MAATGEVENDE_STOF_INVALID);
		
		run ("casNrMaatgevendeStof")
			.withCodeList ("http://www.risicokaart.nl/codes/stoffen", "valid")
			.with (new CodeType ("valid", "http://www.risicokaart.nl/codes/stoffen"))
			.assertNoMessages ();
	}
	
	public @Test void testTransportroutedeelToestand () {
		run ("transportroutedeelToestand")
			.with (null)
			.assertOnlyKey (Message.STATUS_NULL);
		
		run ("transportroutedeelToestand")
			.with ("")
			.assertOnlyKey (Message.STATUS_INVALID);
		
		run ("transportroutedeelToestand")
			.with ("OngeldigeStatus")
			.assertOnlyKey (Message.STATUS_INVALID);
		
		run ("transportroutedeelToestand")
			.with ("Bestaand")
			.assertNoMessages ();
		
		run ("transportroutedeelToestand")
			.with ("Gepland")
			.assertNoMessages ();
	}
	
	public @Test void testEffectafstandDodelijk () {
		run ("effectafstandDodelijk")
			.with (null)
			.assertNoMessages ();
		
		run ("effectafstandDodelijk")
			.with (-1)
			.assertOnlyKey (Message.EFFECTAFSTAND_DODELIJK_INVALID);
		
		run ("effectafstandDodelijk")
			.with (0)
			.assertNoMessages ();
		
		run ("effectafstandDodelijk")
			.with (1)
			.assertNoMessages ();
	}
	
	public @Test void testMaatgevendScenarioDodelijk () {
		run ("maatgevendScenarioDodelijk")
			.with (null)
			.assertNoMessages ();
		
		run ("maatgevendScenarioDodelijk")
			.with ("0")
			.assertOnlyKey (Message.MAATGEVEND_SCENARIO_DODELIJK_INVALID);
		
		run ("maatgevendScenarioDodelijk")
			.with ("1")
			.assertNoMessages ();
		
		run ("maatgevendScenarioDodelijk")
			.with ("10")
			.assertOnlyKey (Message.MAATGEVEND_SCENARIO_DODELIJK_INVALID);
	}
}
