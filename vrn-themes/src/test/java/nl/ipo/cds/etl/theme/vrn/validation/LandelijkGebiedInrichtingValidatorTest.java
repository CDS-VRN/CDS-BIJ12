/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Collections;

import nl.ipo.cds.etl.theme.vrn.domain.LandelijkGebiedInrichting;
import nl.ipo.cds.validation.execute.CompilerException;

/**
 * @author reinoldp
 * 
 */
public class LandelijkGebiedInrichtingValidatorTest extends
		AbstractLandelijkGebiedValidatorTest<LandelijkGebiedInrichting, LandelijkGebiedInrichtingValidator> {

	protected LandelijkGebiedInrichtingValidator createValidator() throws CompilerException {
		return new LandelijkGebiedInrichtingValidator(Collections.emptyMap());
	}

	protected Class<LandelijkGebiedInrichting> getDomainClass() {
		return LandelijkGebiedInrichting.class;
	}

	protected String doelValidationName() {
		return "doelInrichting";
	}

}
