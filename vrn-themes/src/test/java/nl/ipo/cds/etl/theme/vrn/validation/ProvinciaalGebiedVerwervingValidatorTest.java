/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Collections;

import nl.ipo.cds.etl.theme.vrn.domain.ProvinciaalGebiedVerwerving;
import nl.ipo.cds.validation.execute.CompilerException;

/**
 * @author reinoldp
 * 
 */
public class ProvinciaalGebiedVerwervingValidatorTest extends
		AbstractProvinciaalGebiedValidatorTest<ProvinciaalGebiedVerwerving, ProvinciaalGebiedVerwervingValidator> {

	protected ProvinciaalGebiedVerwervingValidator createValidator() throws CompilerException {
		return new ProvinciaalGebiedVerwervingValidator(Collections.emptyMap());
	}

	protected Class<ProvinciaalGebiedVerwerving> getDomainClass() {
		return ProvinciaalGebiedVerwerving.class;
	}

	protected String doelValidationName() {
		return "doelVerwerving";
	}

}
