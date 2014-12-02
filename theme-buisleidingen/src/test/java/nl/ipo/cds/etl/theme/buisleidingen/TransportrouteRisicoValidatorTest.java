package nl.ipo.cds.etl.theme.buisleidingen;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import nl.ipo.cds.etl.test.GeometryConstants;
import nl.ipo.cds.etl.test.ValidationRunner;

import org.junit.Before;
import org.junit.Test;

public class TransportrouteRisicoValidatorTest {

	private final static Set<String> transportrouteIds = new HashSet<> ();
	static {
		transportrouteIds.add ("transportroute.0");
		transportrouteIds.add ("transportroute.1");
		transportrouteIds.add ("transportroute.2");
		transportrouteIds.add ("transportroute.3");
	}
	
	private TransportrouteRisicoValidator validator;
	private ValidationRunner<TransportrouteRisico, Message, Context> runner;
	private GeometryConstants geom;
	
	@Before
	public void createValidator () throws Exception {
		validator = new TransportrouteRisicoValidator (Collections.emptyMap (), new Transportroutes () {
			@Override
			public Set<String> getTransportrouteIds () {
				return transportrouteIds;
			}
		});
		runner = new ValidationRunner<> (validator, TransportrouteRisico.class);
		geom = new GeometryConstants ("EPSG:28992");
	}
	
	private ValidationRunner<TransportrouteRisico, Message, Context>.Runner run (final String validationName) {
		return runner.validation (validationName);
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
	
	public @Test void testLaatsteMutatiedatumtijd () {
		run ("laatsteMutatiedatumtijd")
			.with (null)
			.assertOnlyKey (Message.RISICOCONTOUR_LAATSTE_MUTATIEDATUM_NULL);
		
		run ("laatsteMutatiedatumtijd")
			.with (new Timestamp (new Date ().getTime ()))
			.assertNoMessages ();
		
		run ("laatsteMutatiedatumtijd")
			.with (new Timestamp (new Date ().getTime () - 10000))
			.assertNoMessages ();
		
		run ("laatsteMutatiedatumtijd")
			.with (new Timestamp (new Date ().getTime () + 10000))
			.assertOnlyKey (Message.RISICOCONTOUR_LAATSTE_MUTATIEDATUM_FUTURE);
	}
	
	public @Test void testRisicocontour () throws Exception {
		run ("risicocontour")
			.with (null)
			.assertOnlyKey (Message.RISICOCONTOUR_NULL);

		run ("risicocontour")
			.with (geom.multiPolygon ())
			.assertNoMessages ();
		
		run ("risicocontour")
			.with (geom.point (0, 0))
			.assertOnlyKey (Message.RISICOCONTOUR_NOT_MULTIPOLYGON);
		
		run ("risicocontour")
			.with (geom.multiPolygon (null))
			.assertOnlyKey (Message.RISICOCONTOUR_SRS_NULL);
		
		run ("risicocontour")
			.with (geom.multiPolygon (geom.getSrs ("EPSG:3857")))
			.assertOnlyKey (Message.RISICOCONTOUR_SRS_NOT_RD);
	}
	
	public @Test void testTransportrouteIdExists () {
		run ("transportrouteIdExists")
			.withFeature ("transportrouteId", "transportroute.4")
			.assertOnlyKey (Message.RISICOCONTOUR_TRANSPORTROUTE_NOT_FOUND);
		
		run ("transportrouteIdExists")
			.withFeature ("transportrouteId", "transportroute.4")
			.withFeature ("transportrouteId", "transportroute.5")
			.assertOnlyKey (Message.RISICOCONTOUR_TRANSPORTROUTE_NOT_FOUND, 2);
		
		run ("transportrouteIdExists")
			.withFeature ("transportrouteId", "transportroute.0")
			.assertNoMessages ();
	}
}
