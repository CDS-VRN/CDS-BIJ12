package nl.ipo.cds.etl.theme.buisleidingen;

import java.util.HashMap;
import java.util.Set;

import org.junit.Test;

/**
 * Attempt to instantiate and compile both validators. This tests whether all
 * attributes can be found on the context or feature type beans.
 */
public class ValidatorsValidTest {

	@Test
	public void testTransportroutedeelValidatorvalid () throws Exception {
		new TransportroutedeelValidator (new HashMap<Object, Object> ());
	}
	
	@Test
	public void testTransportrouteRisicoValidatorvalid () throws Exception {
		new TransportrouteRisicoValidator (new HashMap<Object, Object> (), new Transportroutes () {			
			@Override
			public Set<String> getTransportrouteIds() {
				return null;
			}
		});
	}
}
