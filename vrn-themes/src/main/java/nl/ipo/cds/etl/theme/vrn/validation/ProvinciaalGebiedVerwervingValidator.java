/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.ProvinciaalGebiedVerwerving;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.execute.CompilerException;

/**
 * @author annes
 * 
 */
public class ProvinciaalGebiedVerwervingValidator extends
		AbstractGebiedVerwervingValidator<ProvinciaalGebiedVerwerving> {

	/**
	 * @param validatorMessages
	 * @throws CompilerException
	 */
	public ProvinciaalGebiedVerwervingValidator(Map<Object, Object> validatorMessages) throws CompilerException {
		super(validatorMessages, ProvinciaalGebiedVerwerving.class);
		compile();
	}

	/**
	 * If doelVerwerving is provided, it should be conform rules. For provinciaal it is optional. Note that
	 * doelVerwerving can contain multiple values, separated by ';' characters
	 * 
	 * @return
	 */
	public Validator<Message, Context> getDoelVerwervingValidator() {

		return validate(ifExp(
		// can be null or blank
		or(doelVerwerving.isNull(), isBlank(doelVerwerving.code())), constant(true), validateDoelRealisatie(constantDoelVerwerving, doelVerwerving)));
	}
}
