/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import static nl.ipo.cds.etl.theme.vrn.Constants.*;

import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.AbstractGebiedInrichting;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.constants.Constant;
import nl.ipo.cds.validation.execute.CompilerException;
import nl.ipo.cds.validation.gml.CodeExpression;

/**
 * @author annes
 * 
 */
public abstract class AbstractGebiedInrichtingValidator<T extends AbstractGebiedInrichting> extends AbstractVrnValidator<T> {

	private final CodeExpression<Message, Context> statusIngericht = code("statusInrichting");
	private final Constant<Message, Context, String> statusInrichtingCodeSpace = constant(CODESPACE_STATUS_INRICHTING);

	protected final CodeExpression<Message, Context> doelInrichting = code("doelInrichting");
	protected final Constant<Message, Context, String> doelInrichtingCodeSpace = constant(CODESPACE_DOEL_REALISATIE);

	private final CodeExpression<Message, Context> typeBeheerder = code("typeBeheerder");
	private final Constant<Message, Context, String> typeBeheerderCodeSpace = constant(CODESPACE_TYPE_BEHEERDER);

	/**
	 * @param validatorMessages
	 * @param clazz
	 * @throws CompilerException
	 */
	public AbstractGebiedInrichtingValidator(Map<Object, Object> validatorMessages, Class<T> clazz)
			throws CompilerException {
		super(validatorMessages, clazz);
	}

	/*
	 * codeLijst validaties
	 */
	public Validator<Message, Context> getStatusIngerichtValidator() {
		return validate(
				and(
						validate(not(statusIngericht.isNull())).message(Message.ATTRIBUTE_NULL, constant(statusIngericht.name)),
						validate(not(isBlank(statusIngericht.code()))).message(Message.ATTRIBUTE_EMPTY,
								constant(statusIngericht.name)),
						validate(statusIngericht.hasCodeSpace(statusInrichtingCodeSpace)).message(
								Message.ATTRIBUTE_CODE_CODESPACE_INVALID, statusIngericht.codeSpace(),
								constant(statusIngericht.name), statusInrichtingCodeSpace),
						validate(statusIngericht.isValid()).message(Message.ATTRIBUTE_CODE_INVALID,
								statusIngericht.code(), constant(statusIngericht.name), statusInrichtingCodeSpace))
						.shortCircuit());
	}


	public abstract Validator<Message, Context> getDoelInrichtingValidator();

	public Validator<Message, Context> getTypeBeheerderValidator() {
		return validate(and(
				validate(not(typeBeheerder.isNull())).message(Message.ATTRIBUTE_NULL, constant(typeBeheerder.name)),
				validate(not(isBlank(typeBeheerder.code()))).message(Message.ATTRIBUTE_EMPTY,
						constant(typeBeheerder.name)),
				validate(typeBeheerder.hasCodeSpace(typeBeheerderCodeSpace)).message(
						Message.ATTRIBUTE_CODE_CODESPACE_INVALID, typeBeheerder.codeSpace(),
						constant(typeBeheerder.name), typeBeheerderCodeSpace),

				validate(typeBeheerder.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, typeBeheerder.code(),
						constant(typeBeheerder.name), typeBeheerderCodeSpace)).shortCircuit());
	}

}
