package nl.ipo.cds.etl.theme.bvb;

import java.util.Collections;

import nl.ipo.cds.etl.theme.bvb.BvbValidator;

import org.junit.Test;

public class ValidatorsValidTest {

	@Test
	public void testValidatorValid () throws Exception {
		new BvbValidator (Collections.emptyMap ());
	}

}
