/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import nl.ipo.cds.etl.theme.vrn.Constants;
import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.ProvinciaalGebiedBeheer;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.callbacks.UnaryCallback;
import nl.ipo.cds.validation.constants.Constant;
import nl.ipo.cds.validation.execute.CompilerException;
import nl.ipo.cds.validation.gml.codelists.CodeList;
import nl.ipo.cds.validation.gml.codelists.CodeListException;

/**
 * @author annes
 * 
 */
public class ProvinciaalGebiedBeheerValidator extends AbstractGebiedBeheerValidator<ProvinciaalGebiedBeheer> {

	/**
	 * @param validatorMessages
	 * @throws CompilerException
	 */
	public ProvinciaalGebiedBeheerValidator(Map<Object, Object> validatorMessages) throws CompilerException {
		super(validatorMessages, ProvinciaalGebiedBeheer.class);
		compile();
	}

	/**
	 * If doelbeheer is provided, it should be conform rules. For provinciaal it is optional. Note that doelbeheer can
	 * contain multiple values, seperated by ';' characters
	 * 
	 * @return
	 */
	public Validator<Message, Context> getDoelBeheerValidator() {

		// we need a dedicated callback to validate whether the code is valid for codetype DoelRealisatie; this cannot
		// be done by ordinary CodeType validation because we have multiple codes in a single string.
		final UnaryCallback<Message, Context, Boolean, String> validateDoelRealisatieCode = new UnaryCallback<Message, Context, Boolean, String>() {
			@Override
			public Boolean call(final String input, final Context context) throws Exception {
				try {

					final CodeList codeList = context.getCodeListFactory().getCodeList(
							Constants.CODESPACE_DOEL_REALISATIE);
					if (codeList == null) {
						return false;
					}

					return codeList.hasCode(input);
				} catch (CodeListException e) {
					return false;
				}
			}
		};

		final Constant<Message, Context, String> constantDoelBeheer = constant("doelBeheer");
		return validate(ifExp(
				// can be null
				doelBeheer.isNull(),
				constant(true),
				and(
				// when not null, needs to have the correct codespace
						validate(doelBeheer.hasCodeSpace(doelBeheerCodeSpace)).message(
								Message.ATTRIBUTE_CODE_CODESPACE_INVALID, doelBeheer.codeSpace(),
								constant(doelBeheer.name), doelBeheerCodeSpace),
						// split the code into values seperated by ';'
						validate(split(stringAttr("doelBeheerValue"),
								constant(";"),
								// each value must not be null and have a valid code
								validate(forEach(
										"i",
										attr("values", String[].class),
										validate(and(
												validate(not(isBlank(stringAttr("i")))).message(
														Message.ATTRIBUTE_EMPTY, constantDoelBeheer),
												validate(
														callback(Boolean.class, stringAttr("i"),
																validateDoelRealisatieCode)).message(
														Message.ATTRIBUTE_CODE_INVALID, stringAttr("i"),
														constantDoelBeheer, beheerpakketCodeSpace)).shortCircuit()))))))
						.shortCircuit()));
		// TODO message
	}
}
