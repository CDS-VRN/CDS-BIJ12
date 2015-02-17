/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.ProvinciaalGebiedInrichting;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.execute.CompilerException;

/**
 * @author annes
 * 
 */
public class ProvinciaalGebiedInrichtingValidator extends
		AbstractGebiedInrichtingValidator<ProvinciaalGebiedInrichting> {

	/**
	 * @param validatorMessages
	 * @throws CompilerException
	 */
	public ProvinciaalGebiedInrichtingValidator(Map<Object, Object> validatorMessages) throws CompilerException {
		super(validatorMessages, ProvinciaalGebiedInrichting.class);
		compile();
	}

	/**
	 * If doelInrichting is provided, it should be conform rules. For provinciaal it is optional. Note that doelbeheer
	 * can contain multiple values, separated by ';' characters
	 * 
	 * @return
	 */
	public Validator<Message, Context> getDoelInrichtingValidator() {

		return validate(ifExp(
		// can be null or blank
		or(doelInrichting.isNull(), isBlank(doelInrichting.code())), constant(true), validateDoelRealisatie(constantDoelInrichting, doelInrichting)));
	}

}
