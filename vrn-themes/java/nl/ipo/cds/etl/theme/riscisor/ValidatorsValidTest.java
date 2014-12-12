package nl.ipo.cds.etl.theme.riscisor;

import java.util.Collections;

import org.junit.Test;

public class ValidatorsValidTest {

	@Test
	public void testKwetsbaarObjectValidatorValid () throws Exception {
		new KwetsbaarObjectValidator (Collections.emptyMap ());
	}

}
