/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Collections;

import nl.ipo.cds.etl.theme.vrn.domain.LandelijkGebiedBeheer;
import nl.ipo.cds.validation.execute.CompilerException;

/**
 * @author reinoldp
 * 
 */
public class LandelijkGebiedBeheerValidatorTest extends
		AbstractLandelijkGebiedValidatorTest<LandelijkGebiedBeheer, LandelijkGebiedBeheerValidator> {

	protected LandelijkGebiedBeheerValidator createValidator() throws CompilerException {
		return new LandelijkGebiedBeheerValidator(Collections.emptyMap());
	}

	protected Class<LandelijkGebiedBeheer> getDomainClass() {
		return LandelijkGebiedBeheer.class;
	}

	protected String doelValidationName() {
		return "doelBeheer";
	}

}
