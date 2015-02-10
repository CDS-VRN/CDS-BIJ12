/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import static nl.ipo.cds.etl.theme.vrn.Constants.*;
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
public abstract class AbstractGebiedVerwervingValidator<T extends AbstractGebiedVerwerving> extends AbstractVrnValidator<T> {

	
	private final CodeExpression<Message, Context> statusVerwerving = code("statusVerwerving");
	private final Constant<Message, Context, String> statusVerwervingCodeSpace = constant(CODESPACE_STATUS_VERWERVING);
	
	protected final CodeExpression<Message, Context> doelVerwerving = code("doelVerwerving");
	protected final Constant<Message, Context, String> constantDoelVerwerving = constant("doelVerwerving");
	
	private final Constant<Message, Context, String> typeEigenaarCodeSpace = constant(CODESPACE_TYPE_BEHEERDER);
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
		return validate(and(
				validate(not(statusVerwerving.isNull())).message(Message.ATTRIBUTE_NULL, constant(statusVerwerving.name)),
				validate(not(isBlank(statusVerwerving.code()))).message(Message.ATTRIBUTE_EMPTY, constant(statusVerwerving.name)),
				validate(statusVerwerving.hasCodeSpace(statusVerwervingCodeSpace)).message(Message.ATTRIBUTE_CODE_CODESPACE_INVALID,
						statusVerwerving.codeSpace(), constant(statusVerwerving.name), statusVerwervingCodeSpace),
				validate(statusVerwerving.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, statusVerwerving.code(), constant(statusVerwerving.name),
						statusVerwervingCodeSpace)).shortCircuit());
	}

	public abstract Validator<Message, Context> getDoelVerwervingValidator();

	public Validator<Message, Context> getTypeBeheerderValidator() {
		return validate(and(
				validate(not(isBlank(typeEigenaar.code()))).message(Message.ATTRIBUTE_EMPTY, constant(typeEigenaar.name)),
				validate(typeEigenaar.hasCodeSpace(typeEigenaarCodeSpace)).message(Message.ATTRIBUTE_CODE_CODESPACE_INVALID, typeEigenaar.codeSpace(),
						constant(typeEigenaar.name), typeEigenaarCodeSpace),
				validate(typeEigenaar.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, typeEigenaar.code(), constant(typeEigenaar.name),
						typeEigenaarCodeSpace)).shortCircuit());
	}
	
}
