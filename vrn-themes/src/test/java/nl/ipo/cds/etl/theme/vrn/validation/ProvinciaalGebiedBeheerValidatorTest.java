/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Collections;

import nl.ipo.cds.etl.theme.vrn.domain.ProvinciaalGebiedBeheer;
import nl.ipo.cds.validation.execute.CompilerException;

/**
 * @author reinoldp
 * 
 */
public class ProvinciaalGebiedBeheerValidatorTest extends
		AbstractProvinciaalGebiedValidatorTest<ProvinciaalGebiedBeheer, ProvinciaalGebiedBeheerValidator> {

	protected ProvinciaalGebiedBeheerValidator createValidator() throws CompilerException {
		return new ProvinciaalGebiedBeheerValidator(Collections.emptyMap());
	}

	protected Class<ProvinciaalGebiedBeheer> getDomainClass() {
		return ProvinciaalGebiedBeheer.class;
	}

	protected String doelValidationName() {
		return "doelBeheer";
	}

}
