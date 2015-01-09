/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.AbstractGebiedBeheer;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.constants.Constant;
import nl.ipo.cds.validation.execute.CompilerException;
import nl.ipo.cds.validation.gml.CodeExpression;

/**
 * @author annes
 *
 */
public class AbstractGebiedBeheerValidator<T extends AbstractGebiedBeheer> extends AbstractVrnValidator<T> {

	private final Constant<Message, Context, String> statusBeheerCodeSpace = constant("statusBeheer");
	private final Constant<Message, Context, String> beheerpakketCodeSpace = constant("beheerpakket");
	private final Constant<Message, Context, String> doelBeheerCodeSpace = constant("doelRealisatie");
	
	private final CodeExpression<Message, Context> statusBeheer = code("statusBeheer");
	private final CodeExpression<Message, Context> beheerpakket = code("beheerpakket");
	private final CodeExpression<Message, Context> doelBeheer = code("doelBeheer");
	
	private final Constant<Message, Context, String> typeBeheerderCodeSpace = constant("typeBeheerder");
	private final CodeExpression<Message, Context> typeBeheerder = code("typeBeheerder");
	
	/**
	 * @param validatorMessages
	 * @param clazz
	 * @throws CompilerException
	 */
	public AbstractGebiedBeheerValidator(Map<Object, Object> validatorMessages, Class<T> clazz) throws CompilerException {
		super(validatorMessages, clazz);
	}

	
	
	/*
	 * codeLijst validaties
	 */
	public Validator<Message, Context> getStatusBeheerValidator() {
		return validate(ifExp(statusBeheer.isNull(), constant(true), and(
		validate(statusBeheer.hasCodeSpace(statusBeheerCodeSpace)).message(Message.ATTRIBUTE_CODE_CODESPACE_INVALID, statusBeheer.codeSpace(),
		constant(statusBeheer.name), statusBeheerCodeSpace),
		validate(not(isBlank(statusBeheer.code()))).message(Message.ATTRIBUTE_EMPTY, constant(statusBeheer.name)),
		validate(statusBeheer.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, statusBeheer.code(), constant(statusBeheer.name),
		statusBeheerCodeSpace)).shortCircuit()));
	}
	
	public Validator<Message, Context> getBeheerPakketValidator() {
		return validate(ifExp(beheerpakket.isNull(), constant(true), and(
		validate(beheerpakket.hasCodeSpace(beheerpakketCodeSpace)).message(Message.ATTRIBUTE_CODE_CODESPACE_INVALID, beheerpakket.codeSpace(),
		constant(beheerpakket.name), beheerpakketCodeSpace),
		validate(not(isBlank(beheerpakket.code()))).message(Message.ATTRIBUTE_EMPTY, constant(beheerpakket.name)),
		validate(beheerpakket.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, beheerpakket.code(), constant(beheerpakket.name),
		beheerpakketCodeSpace)).shortCircuit()));
	}
	
	public Validator<Message, Context> getDoelBeheerValidator() {
		return validate(ifExp(doelBeheer.isNull(), constant(false), and(
		validate(doelBeheer.hasCodeSpace(doelBeheerCodeSpace)).message(Message.ATTRIBUTE_CODE_CODESPACE_INVALID, doelBeheer.codeSpace(),
		constant(doelBeheer.name), doelBeheerCodeSpace),
		validate(not(isBlank(doelBeheer.code()))).message(Message.ATTRIBUTE_EMPTY, constant(doelBeheer.name)),
		validate(doelBeheer.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, doelBeheer.code(), constant(doelBeheer.name),
		doelBeheerCodeSpace)).shortCircuit()));
	}

	public Validator<Message, Context> getTypeBeheerderValidator() {
		return validate(ifExp(typeBeheerder.isNull(), constant(false), and(
		validate(typeBeheerder.hasCodeSpace(typeBeheerderCodeSpace)).message(Message.ATTRIBUTE_CODE_CODESPACE_INVALID, typeBeheerder.codeSpace(),
		constant(typeBeheerder.name), typeBeheerderCodeSpace),
		validate(not(isBlank(typeBeheerder.code()))).message(Message.ATTRIBUTE_EMPTY, constant(typeBeheerder.name)),
		validate(typeBeheerder.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, typeBeheerder.code(), constant(typeBeheerder.name),
		typeBeheerderCodeSpace)).shortCircuit()));
	}
}