/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Collections;

import nl.ipo.cds.etl.theme.vrn.domain.ProvinciaalGebiedInrichting;
import nl.ipo.cds.validation.execute.CompilerException;

/**
 * @author reinoldp
 * 
 */
public class ProvinciaalGebiedInrichtingValidatorTest extends
		AbstractProvinciaalGebiedValidatorTest<ProvinciaalGebiedInrichting, ProvinciaalGebiedInrichtingValidator> {

	protected ProvinciaalGebiedInrichtingValidator createValidator() throws CompilerException {
		return new ProvinciaalGebiedInrichtingValidator(Collections.emptyMap());
	}

	protected Class<ProvinciaalGebiedInrichting> getDomainClass() {
		return ProvinciaalGebiedInrichting.class;
	}

	protected String doelValidationName() {
		return "doelInrichting";
	}

}
