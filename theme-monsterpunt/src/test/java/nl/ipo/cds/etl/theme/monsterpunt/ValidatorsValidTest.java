package nl.ipo.cds.etl.theme.monsterpunt;

import java.util.Collections;

import nl.ipo.cds.etl.theme.monsterpunt.MonsterpuntValidator;

import org.junit.Test;

public class ValidatorsValidTest {

	@Test
	public void testValidatorValid () throws Exception {
		new MonsterpuntValidator (Collections.emptyMap ());
	}

}
