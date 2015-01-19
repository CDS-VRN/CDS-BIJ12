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
public class ProvinciaalGebiedInrichtingValidator extends AbstractGebiedInrichtingValidator<ProvinciaalGebiedInrichting> {

	/**
	 * @param validatorMessages
	 * @throws CompilerException
	 */
	public ProvinciaalGebiedInrichtingValidator(Map<Object, Object> validatorMessages) throws CompilerException {
		super(validatorMessages, ProvinciaalGebiedInrichting.class);
		compile();
	}

	/**
	 * Doelinrichting is mandatory for "provinciaal".
	 * @return
	 */
	@Override
	public Validator<Message, Context> getDoelInrichtingValidator() {
		return validate(
				and(
						validate(not(doelInrichting.isNull())).message(Message.ATTRIBUTE_NULL,
								constant(doelInrichting.name)),
						validate(not(isBlank(doelInrichting.code()))).message(Message.ATTRIBUTE_EMPTY,
								constant(doelInrichting.name)),
						validate(doelInrichting.hasCodeSpace(doelInrichtingCodeSpace)).message(
								Message.ATTRIBUTE_CODE_CODESPACE_INVALID, doelInrichting.codeSpace(),
								constant(doelInrichting.name), doelInrichtingCodeSpace),
						validate(doelInrichting.isValid()).message(Message.ATTRIBUTE_CODE_INVALID,
								doelInrichting.code(), constant(doelInrichting.name), doelInrichtingCodeSpace))
						.shortCircuit());
	}
}
