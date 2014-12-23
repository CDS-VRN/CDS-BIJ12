/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.AbstractGebiedVerwerving;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.constants.Constant;
import nl.ipo.cds.validation.execute.CompilerException;
import nl.ipo.cds.validation.gml.CodeExpression;

/**
 * @author annes
 *
 */
public class AbstractGebiedVerwervingValidator<T extends AbstractGebiedVerwerving> extends AbstractVrnValidator<T> {

	
	private final CodeExpression<Message, Context> statusVerwerving = code("StatusVerwerving");
	private final Constant<Message, Context, String> statusVerwervingCodeSpace = constant("http://codeList/");
	
	/**
	 * @param validatorMessages
	 * @param clazz
	 * @throws CompilerException
	 */
	public AbstractGebiedVerwervingValidator(Map<Object, Object> validatorMessages, Class<T> clazz) throws CompilerException {
		super(validatorMessages, clazz);
		compile();
	}

	/*
	 * codeLijst validaties
	 */
	public Validator<Message, Context> getStatusIngerichtValidator() {
		return validate(ifExp(statusVerwerving.isNull(), constant(true), and(
		validate(statusVerwerving.hasCodeSpace(statusVerwervingCodeSpace)).message(Message.ATTRIBUTE_CODE_CODESPACE_INVALID,
		statusVerwerving.codeSpace(), constant(statusVerwerving.name), statusVerwervingCodeSpace),
		validate(not(isBlank(statusVerwerving.code()))).message(Message.ATTRIBUTE_EMPTY, constant(statusVerwerving.name)),
		validate(statusVerwerving.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, statusVerwerving.code(), constant(statusVerwerving.name),
		statusVerwervingCodeSpace)).shortCircuit()));
	}

}
