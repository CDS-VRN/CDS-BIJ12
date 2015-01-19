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
import static nl.ipo.cds.etl.theme.vrn.Constants.*;

/**
 * @author annes
 * 
 */
public abstract class AbstractGebiedBeheerValidator<T extends AbstractGebiedBeheer> extends AbstractVrnValidator<T> {

	private final Constant<Message, Context, String> statusBeheerCodeSpace = constant(CODESPACE_STATUS_BEHEER);
	private final Constant<Message, Context, String> beheerpakketCodeSpace = constant(CODESPACE_BEHEER_PAKKET);
	protected final Constant<Message, Context, String> doelBeheerCodeSpace = constant(CODESPACE_DOEL_REALISATIE);
	private final Constant<Message, Context, String> typeBeheerderCodeSpace = constant(CODESPACE_TYPE_BEHEERDER);

	private final CodeExpression<Message, Context> statusBeheer = code("statusBeheer");
	private final CodeExpression<Message, Context> beheerpakket = code("beheerpakket");
	protected final CodeExpression<Message, Context> doelBeheer = code("doelBeheer");
	private final CodeExpression<Message, Context> typeBeheerder = code("typeBeheerder");

	/**
	 * @param validatorMessages
	 * @param clazz
	 * @throws CompilerException
	 */
	public AbstractGebiedBeheerValidator(Map<Object, Object> validatorMessages, Class<T> clazz)
			throws CompilerException {
		super(validatorMessages, clazz);
	}

	/*
	 * codeLijst validaties
	 */
	public Validator<Message, Context> getStatusBeheerValidator() {
		return validate(and(
				validate(not(statusBeheer.isNull())).message(Message.ATTRIBUTE_NULL, constant(statusBeheer.name)),
				validate(not(isBlank(statusBeheer.code()))).message(Message.ATTRIBUTE_EMPTY,
						constant(statusBeheer.name)),
				validate(statusBeheer.hasCodeSpace(statusBeheerCodeSpace)).message(
						Message.ATTRIBUTE_CODE_CODESPACE_INVALID, statusBeheer.codeSpace(),
						constant(statusBeheer.name), statusBeheerCodeSpace),

				validate(statusBeheer.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, statusBeheer.code(),
						constant(statusBeheer.name), statusBeheerCodeSpace)).shortCircuit());
	}

	public Validator<Message, Context> getBeheerPakketValidator() {
		return validate(and(
				validate(not(beheerpakket.isNull())).message(Message.ATTRIBUTE_NULL, constant(beheerpakket.name)),
				validate(not(isBlank(beheerpakket.code()))).message(Message.ATTRIBUTE_EMPTY,
						constant(beheerpakket.name)),
				validate(beheerpakket.hasCodeSpace(beheerpakketCodeSpace)).message(
						Message.ATTRIBUTE_CODE_CODESPACE_INVALID, beheerpakket.codeSpace(),
						constant(beheerpakket.name), beheerpakketCodeSpace),

				validate(beheerpakket.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, beheerpakket.code(),
						constant(beheerpakket.name), beheerpakketCodeSpace)).shortCircuit());
	}


	public abstract Validator<Message, Context> getDoelbeheerValidator();

	public Validator<Message, Context> getTypeBeheerderValidator() {
		return validate(and(
				validate(not(typeBeheerder.isNull())).message(Message.ATTRIBUTE_NULL, constant(beheerpakket.name)),
				validate(not(isBlank(typeBeheerder.code()))).message(Message.ATTRIBUTE_EMPTY,
						constant(typeBeheerder.name)),
				validate(typeBeheerder.hasCodeSpace(typeBeheerderCodeSpace)).message(
						Message.ATTRIBUTE_CODE_CODESPACE_INVALID, typeBeheerder.codeSpace(),
						constant(typeBeheerder.name), typeBeheerderCodeSpace),

				validate(typeBeheerder.isValid()).message(Message.ATTRIBUTE_CODE_INVALID, typeBeheerder.code(),
						constant(typeBeheerder.name), typeBeheerderCodeSpace)).shortCircuit());
	}
}
