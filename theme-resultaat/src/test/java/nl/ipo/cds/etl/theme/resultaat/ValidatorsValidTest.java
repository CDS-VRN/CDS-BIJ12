package nl.ipo.cds.etl.theme.resultaat;

import java.util.Collections;

import nl.ipo.cds.etl.theme.resultaat.ResultaatValidator;

import org.junit.Test;

public class ValidatorsValidTest {

	@Test
	public void testValidatorValid () throws Exception {
		new ResultaatValidator (Collections.emptyMap ());
	}

}
