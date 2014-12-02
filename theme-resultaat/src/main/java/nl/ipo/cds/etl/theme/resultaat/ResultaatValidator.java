package nl.ipo.cds.etl.theme.resultaat;

import java.util.Map;

import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.AbstractValidator;
import nl.ipo.cds.validation.ValidationReporter;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.execute.CompilerException;
import nl.ipo.cds.validation.gml.codelists.CodeListFactory;

public class ResultaatValidator extends AbstractValidator<Resultaat, Message, Context> {

	public ResultaatValidator(final Map<Object, Object> validatorMessages) throws CompilerException {
		super(Context.class, Resultaat.class, validatorMessages);
		compile();
	}

	@Override
	public Context beforeJob(final EtlJob job, final CodeListFactory codeListFactory,
			final ValidationReporter<Message, Context> reporter) {
		return new Context(codeListFactory, reporter);
	}

	public Validator<Message, Context> getDummy1Validator () {
		return validate (constant (true));
	}

	public Validator<Message, Context> getDummy2Validator () {
		return validate (constant (true));
	}

}
