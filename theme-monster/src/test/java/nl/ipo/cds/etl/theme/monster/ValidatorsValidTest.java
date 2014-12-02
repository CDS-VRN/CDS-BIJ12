package nl.ipo.cds.etl.theme.monster;

import java.util.Collections;

import nl.ipo.cds.etl.theme.monster.MonsterValidator;

import org.junit.Test;

public class ValidatorsValidTest {

	@Test
	public void testValidatorValid () throws Exception {
		new MonsterValidator (Collections.emptyMap ());
	}

}
