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

	
	private final CodeExpression<Message, Context> statusVerwerving = code("statusVerwerving");
	private final Constant<Message, Context, String> statusVerwervingCodeSpace = constant("statusVerwerving");
	
	private final CodeExpression<Message, Context> doelVerwerving = code("doelVerwerving");
	private final Constant<Message, Context, String> doelVerwervingCodeSpace = constant("doelRealisatie");
	
	private final Constant<Message, Context, String> typeEigenaarCodeSpace = constant("typeBeheerderEnEigenaar");
	private final CodeExpression<Message, Context> typeEigenaar = code("typeEigenaar");
	
	/**
	 * @param validatorMessages
	 * @param clazz
	 * @throws CompilerException
	 */
	public AbstractGebiedVerwervingValidator(Map<Object, Object> validatorMessages, Class<T> clazz) throws CompilerException {
		super(validatorMessages, clazz);
	}

	/*
	 * codeLijst validaties
	 */
	public Validator<Message, Context> getStatusIngerichtValidator() {
		return validate(ifExp(statusVerwerving.isNull(), constant(false), and(
		validate(statusVerwerving.hasCodeSpace(statusVerwervingCodeSpace)).message(Message.ATTRIBUTE_CODE_CODESPACE_INVALID,
		statusVerwerving.codeSpace(), constant(statusVerwerving.name), statusVerwervingCodeSpace),
		validate(not(isBlank(statusVerwerving.code()))).message(Message.ATTRIBUTE_EMPTY, constant(statusVerwerving.name)),
		validate(statusVerwerving.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, statusVerwerving.code(), constant(statusVerwerving.name),
		statusVerwervingCodeSpace)).shortCircuit()));
	}

	public Validator<Message, Context> getDoelInrichtingValidator() {
		return validate(ifExp(doelVerwerving.isNull(), constant(false), and(
		validate(doelVerwerving.hasCodeSpace(doelVerwervingCodeSpace)).message(Message.ATTRIBUTE_CODE_CODESPACE_INVALID, doelVerwerving.codeSpace(),
		constant(doelVerwerving.name), doelVerwervingCodeSpace),
		validate(not(isBlank(doelVerwerving.code()))).message(Message.ATTRIBUTE_EMPTY, constant(doelVerwerving.name)),
		validate(doelVerwerving.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, doelVerwerving.code(), constant(doelVerwerving.name),
		doelVerwervingCodeSpace)).shortCircuit()));
	}

	public Validator<Message, Context> getTypeBeheerderValidator() {
		return validate(ifExp(typeEigenaar.isNull(), constant(false), and(
		validate(typeEigenaar.hasCodeSpace(typeEigenaarCodeSpace)).message(Message.ATTRIBUTE_CODE_CODESPACE_INVALID, typeEigenaar.codeSpace(),
		constant(typeEigenaar.name), typeEigenaarCodeSpace),
		validate(not(isBlank(typeEigenaar.code()))).message(Message.ATTRIBUTE_EMPTY, constant(typeEigenaar.name)),
		validate(typeEigenaar.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, typeEigenaar.code(), constant(typeEigenaar.name),
		typeEigenaarCodeSpace)).shortCircuit()));
	}
	
}
