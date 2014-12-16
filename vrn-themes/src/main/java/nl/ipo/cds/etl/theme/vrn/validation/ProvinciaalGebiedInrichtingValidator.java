/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import nl.ipo.cds.etl.theme.vrn.domain.ProvinciaalGebiedInrichting;
import nl.ipo.cds.validation.execute.CompilerException;

/**
 * @author annes
 *
 */
public class ProvinciaalGebiedInrichtingValidator extends AbstractVrnValidator<ProvinciaalGebiedInrichting> {

	public ProvinciaalGebiedInrichtingValidator(Map<Object, Object> validatorMessages) throws CompilerException {
		super(validatorMessages, ProvinciaalGebiedInrichting.class);
		// TODO Auto-generated constructor stub
	}

}
