/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.LandelijkGebiedBeheer;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.execute.CompilerException;

/**
 * @author annes
 * 
 */
public class LandelijkGebiedBeheerValidator extends AbstractGebiedBeheerValidator<LandelijkGebiedBeheer> {

	/**
	 * @param validatorMessages
	 * @throws CompilerException
	 */
	public LandelijkGebiedBeheerValidator(Map<Object, Object> validatorMessages) throws CompilerException {
		super(validatorMessages, LandelijkGebiedBeheer.class);
		compile();
	}

	/**
	 * If doelbeheer is provided, it should be conform rules. For provinciaal it is optional. Note that doelbeheer can
	 * contain multiple values, seperated by ';' characters
	 * 
	 * @return
	 */
	public Validator<Message, Context> getDoelBeheerValidator() {
		// for landelijk thema, doel attribute is required.
		return validate(and(
				validate(not(doelBeheer.isNull())).message(Message.ATTRIBUTE_NULL, constant(doelBeheer.name)),
				validateDoelRealisatie(constantDoelBeheer, doelBeheer)).shortCircuit());
	}

	/**
	 * All data uploaded by provinces should be within their geometry bounds.
	 */
	public Validator<Message, Context> getGeometryWithinBronhouderGeometryValidator() {
		return getGeometryWithinBronhouderGeometryHelper();
	}
}
