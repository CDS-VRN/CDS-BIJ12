/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

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
public class AbstractGebiedInrichtingValidator<T extends AbstractGebiedInrichting> extends AbstractVrnValidator<T> {

	private final CodeExpression<Message, Context> statusIngericht = code("statusIngericht");
	private final Constant<Message, Context, String> statusIngerichtCodeSpace = constant("http://codeList/");
	
	/**
	 * @param validatorMessages
	 * @param clazz
	 * @throws CompilerException
	 */
	public AbstractGebiedInrichtingValidator(Map<Object, Object> validatorMessages, Class<T> clazz) throws CompilerException {
		super(validatorMessages, clazz);
		compile();
	}

	/*
	 * codeLijst validaties
	 */
	public Validator<Message, Context> getStatusIngerichtValidator() {
		return validate(ifExp(statusIngericht.isNull(), constant(true), and(
		validate(statusIngericht.hasCodeSpace(statusIngerichtCodeSpace)).message(Message.ATTRIBUTE_CODE_CODESPACE_INVALID,
		statusIngericht.codeSpace(), constant(statusIngericht.name), statusIngerichtCodeSpace),
		validate(not(isBlank(statusIngericht.code()))).message(Message.ATTRIBUTE_EMPTY, constant(statusIngericht.name)),
		validate(statusIngericht.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, statusIngericht.code(), constant(statusIngericht.name),
		statusIngerichtCodeSpace)).shortCircuit()));
	}
	
}
