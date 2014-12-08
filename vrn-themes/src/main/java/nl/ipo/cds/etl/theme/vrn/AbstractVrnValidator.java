package nl.ipo.cds.etl.theme.vrn;

import java.util.Map;

import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.AbstractValidator;
import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.AbstractGebied;
import nl.ipo.cds.validation.ValidationReporter;
import nl.ipo.cds.validation.gml.codelists.CodeListFactory;

public class AbstractVrnValidator  extends AbstractValidator<AbstractGebied, Message, Context> {

	public AbstractVrnValidator(Class<Context> contextClass, Class<AbstractGebied> featureClass, Map<Object, Object> validatorMessages) {
		super(contextClass, featureClass, validatorMessages);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Context beforeJob(final EtlJob job, final CodeListFactory codeListFactory,
			final ValidationReporter<Message, Context> reporter) {
		return new Context(codeListFactory, reporter);
	}

	
}
