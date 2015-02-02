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
	 * For landelijk, the doelbeheer attribute is mandatory.
	 * @return
	 */
	@Override
	public Validator<Message, Context> getDoelBeheerValidator() {
		return validate(and(
				validate(not(doelBeheer.isNull())).message(Message.ATTRIBUTE_NULL, constant(doelBeheer.name)),
				validate(doelBeheer.hasCodeSpace(doelBeheerCodeSpace)).message(
						Message.ATTRIBUTE_CODE_CODESPACE_INVALID, doelBeheer.codeSpace(), constant(doelBeheer.name),
						doelBeheerCodeSpace),
				validate(not(isBlank(doelBeheer.code()))).message(Message.ATTRIBUTE_EMPTY, constant(doelBeheer.name)),
				validate(doelBeheer.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, doelBeheer.code(),
						constant(doelBeheer.name), doelBeheerCodeSpace)).shortCircuit());
	}

	/**
	 * All data uploaded by provinces should be within their geometry bounds.
	 */
	public Validator<Message, Context> getGeometryWithinBronhouderGeometryValidator() {
		return getGeometryWithinBronhouderGeometryHelper();
	}
}
