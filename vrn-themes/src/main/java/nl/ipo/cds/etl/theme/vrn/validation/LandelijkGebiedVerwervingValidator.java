/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import nl.ipo.cds.etl.theme.vrn.domain.LandelijkGebiedVerwerving;
import nl.ipo.cds.validation.execute.CompilerException;

/**
 * @author annes
 *
 */
public class LandelijkGebiedVerwervingValidator extends AbstractGebiedVerwervingValidator<LandelijkGebiedVerwerving> {

	

	/**
	 * @param validatorMessages
	 * @throws CompilerException
	 */
	public LandelijkGebiedVerwervingValidator(Map<Object, Object> validatorMessages) throws CompilerException {
		super(validatorMessages, LandelijkGebiedVerwerving.class);
		compile();
	}

	
}
