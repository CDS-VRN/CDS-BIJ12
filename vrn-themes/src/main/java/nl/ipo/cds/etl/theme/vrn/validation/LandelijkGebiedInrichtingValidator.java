/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.LandelijkGebiedInrichting;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.execute.CompilerException;

/**
 * @author annes
 *
 */
public class LandelijkGebiedInrichtingValidator extends AbstractGebiedInrichtingValidator<LandelijkGebiedInrichting> {

	

	/**
	 * @param validatorMessages
	 * @throws CompilerException
	 */
	public LandelijkGebiedInrichtingValidator(Map<Object, Object> validatorMessages) throws CompilerException {
		super(validatorMessages, LandelijkGebiedInrichting.class);
		compile();
	}

	/**
	 * If doelInrichting is provided, it should be conform rules. For landelijk it is required. Note that doelInrichting
	 * can
	 * contain multiple values, separated by ';' characters
	 * 
	 * @return
	 */
	public Validator<Message, Context> getDoelInrichtingValidator() {
		// for landelijk thema, doel attribute is required.
		return validate(and(
				validate(not(doelInrichting.isNull())).message(Message.ATTRIBUTE_NULL, constant(doelInrichting.name)),
				validateDoelRealisatie(constantDoelInrichting, doelInrichting)).shortCircuit());
	}

	/**
	 * All data uploaded by provinces should be within their geometry bounds.
	 */
	public Validator<Message, Context> getGeometryWithinBronhouderGeometryValidator() {
		return getGeometryWithinBronhouderGeometryHelper();
	}
}
