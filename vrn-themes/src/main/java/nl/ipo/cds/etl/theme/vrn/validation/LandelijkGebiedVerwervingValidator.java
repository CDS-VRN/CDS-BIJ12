/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.LandelijkGebiedVerwerving;
import nl.ipo.cds.validation.Validator;
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

	/**
	 * If doelVerwerving is provided, it should be conform rules. For provinciaal it is optional. Note that
	 * doelVerwerving can contain multiple values, seperated by ';' characters
	 * 
	 * @return
	 */
	public Validator<Message, Context> getDoelVerwervingValidator() {
		// for landelijk thema, doel attribute is required.
		return validate(and(
				validate(not(doelVerwerving.isNull())).message(Message.ATTRIBUTE_NULL, constant(doelVerwerving.name)),
				validateDoelRealisatie(constantDoelVerwerving, doelVerwerving)).shortCircuit());
	}

	/**
	 * All data uploaded by provinces should be within their geometry bounds.
	 */
	public Validator<Message, Context> getGeometryWithinBronhouderGeometryValidator() {
		return getGeometryWithinBronhouderGeometryHelper();
	}
}
