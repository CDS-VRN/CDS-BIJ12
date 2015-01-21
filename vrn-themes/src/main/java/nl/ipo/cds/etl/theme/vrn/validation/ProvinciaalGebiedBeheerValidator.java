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
	 * Iff doelbeheer is provided, it should be conform rules. For provinciaal it is optional.
	 * @return
	 */
	@Override
	public Validator<Message, Context> getDoelbeheerValidator() {
		return validate(ifExp(doelBeheer.isNull(),
				constant(true),
				and(
						validate(not(isBlank(doelBeheer.code()))).message(Message.ATTRIBUTE_EMPTY, constant(doelBeheer.name)),
						validate(doelBeheer.hasCodeSpace(doelBeheerCodeSpace)).message(
								Message.ATTRIBUTE_CODE_CODESPACE_INVALID, doelBeheer.codeSpace(), constant(doelBeheer.name),
								doelBeheerCodeSpace),
						validate(not(isBlank(doelBeheer.code()))).message(Message.ATTRIBUTE_EMPTY, constant(doelBeheer.name)),
						validate(doelBeheer.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, doelBeheer.code(),
								constant(doelBeheer.name), doelBeheerCodeSpace)).shortCircuit()));
	}
}
