/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Collections;

import nl.ipo.cds.etl.theme.vrn.domain.LandelijkGebiedVerwerving;
import nl.ipo.cds.validation.execute.CompilerException;

/**
 * @author reinoldp
 * 
 */
public class LandelijkGebiedVerwervingValidatorTest extends
		AbstractLandelijkGebiedValidatorTest<LandelijkGebiedVerwerving, LandelijkGebiedVerwervingValidator> {

	protected LandelijkGebiedVerwervingValidator createValidator() throws CompilerException {
		return new LandelijkGebiedVerwervingValidator(Collections.emptyMap());
	}

	protected Class<LandelijkGebiedVerwerving> getDomainClass() {
		return LandelijkGebiedVerwerving.class;
	}

	protected String doelValidationName() {
		return "doelVerwerving";
	}

}
