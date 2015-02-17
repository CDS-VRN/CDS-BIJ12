/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.ProvinciaalGebiedBeheer;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.execute.CompilerException;

/**
 * @author annes
 * 
 */
public class ProvinciaalGebiedBeheerValidator extends AbstractGebiedBeheerValidator<ProvinciaalGebiedBeheer> {

	/**
	 * @param validatorMessages
	 * @throws CompilerException
	 */
	public ProvinciaalGebiedBeheerValidator(Map<Object, Object> validatorMessages) throws CompilerException {
		super(validatorMessages, ProvinciaalGebiedBeheer.class);
		compile();
	}

	/**
	 * If doelbeheer is provided, it should be conform rules. For provinciaal it is optional. Note that doelbeheer can
	 * contain multiple values, separated by ';' characters
	 * 
	 * @return
	 */
	public Validator<Message, Context> getDoelBeheerValidator() {

		return validate(ifExp(
		// can be null or blank
		or(doelBeheer.isNull(), isBlank(doelBeheer.code())), constant(true), validateDoelRealisatie(constantDoelBeheer, doelBeheer)));
	}

}
