/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import nl.ipo.cds.etl.theme.vrn.domain.ProvinciaalGebiedVerwerving;
import nl.ipo.cds.validation.execute.CompilerException;

/**
 * @author annes
 *
 */
public class ProvinciaalGebiedVerwervingValidator extends AbstractVrnValidator<ProvinciaalGebiedVerwerving> {

	public ProvinciaalGebiedVerwervingValidator(Map<Object, Object> validatorMessages) throws CompilerException {
		super(validatorMessages, ProvinciaalGebiedVerwerving.class);
		// TODO Auto-generated constructor stub
	}

}
