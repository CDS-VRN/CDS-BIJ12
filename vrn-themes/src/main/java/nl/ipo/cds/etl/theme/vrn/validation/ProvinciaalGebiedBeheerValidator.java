/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import nl.ipo.cds.etl.theme.vrn.domain.ProvinciaalGebiedBeheer;
import nl.ipo.cds.validation.execute.CompilerException;

/**
 * @author annes
 *
 */
public class ProvinciaalGebiedBeheerValidator extends AbstractVrnValidator<ProvinciaalGebiedBeheer> {

	public ProvinciaalGebiedBeheerValidator(Map<Object, Object> validatorMessages) throws CompilerException {
		super(validatorMessages, ProvinciaalGebiedBeheer.class);
		// TODO Auto-generated constructor stub
	}

}
