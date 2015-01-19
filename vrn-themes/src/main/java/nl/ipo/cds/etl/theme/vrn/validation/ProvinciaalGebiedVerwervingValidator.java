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
public class ProvinciaalGebiedVerwervingValidator extends AbstractGebiedVerwervingValidator<ProvinciaalGebiedVerwerving> {

	/**
	 * @param validatorMessages
	 * @throws CompilerException
	 */
	public ProvinciaalGebiedVerwervingValidator(Map<Object, Object> validatorMessages) throws CompilerException {
		super(validatorMessages, ProvinciaalGebiedVerwerving.class);
		compile();
	}

	/**
	 * doelVerwerving is mandatory for "provinciaal".
	 * @return
	 */
	@Override
	public Validator<Message, Context> getDoelVerwervingValidator() {
		return validate(and(
				validate(not(doelVerwerving.isNull())).message(Message.ATTRIBUTE_NULL, constant(doelVerwerving.name)),
				validate(not(isBlank(doelVerwerving.code()))).message(Message.ATTRIBUTE_EMPTY, constant(doelVerwerving.name)),
				validate(doelVerwerving.hasCodeSpace(doelVerwervingCodeSpace)).message(Message.ATTRIBUTE_CODE_CODESPACE_INVALID, doelVerwerving.codeSpace(),
						constant(doelVerwerving.name), doelVerwervingCodeSpace),
				validate(doelVerwerving.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, doelVerwerving.code(), constant(doelVerwerving.name),
						doelVerwervingCodeSpace)).shortCircuit());
	}
}
