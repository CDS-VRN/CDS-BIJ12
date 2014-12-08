package nl.ipo.vrn.etl.theme.verwerving;

import java.util.Collections;

import org.junit.Test;

public class ValidatorsValidTest {

	@Test
	public void testValidatorValid () throws Exception {
		new VerwervingValidator (Collections.emptyMap ());
	}

}
