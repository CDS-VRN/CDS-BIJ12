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

	/**
	 * @param validatorMessages
	 * @param clazz
	 * @throws CompilerException
	 */
	public AbstractGebiedBeheerValidator(Map<Object, Object> validatorMessages, Class<T> clazz) throws CompilerException {
		super(validatorMessages, clazz);
		compile();
	}

	private final Constant<Message, Context, String> statusBeheerCodeSpace = constant("http://codeList/");
	private final Constant<Message, Context, String> beheerPakketCodeSpace = constant("http://codeList/");
	
	private final CodeExpression<Message, Context> statusBeheer = code("statusBeheer");
	private final CodeExpression<Message, Context> beheerPakket = code("beheerPakket");
	
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
		return validate(ifExp(beheerPakket.isNull(), constant(true), and(
		validate(beheerPakket.hasCodeSpace(beheerPakketCodeSpace)).message(Message.ATTRIBUTE_CODE_CODESPACE_INVALID, beheerPakket.codeSpace(),
		constant(beheerPakket.name), beheerPakketCodeSpace),
		validate(not(isBlank(beheerPakket.code()))).message(Message.ATTRIBUTE_EMPTY, constant(beheerPakket.name)),
		validate(beheerPakket.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, beheerPakket.code(), constant(beheerPakket.name),
		beheerPakketCodeSpace)).shortCircuit()));
	}
	
}
