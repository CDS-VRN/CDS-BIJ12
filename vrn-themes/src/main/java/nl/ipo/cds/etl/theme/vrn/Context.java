package nl.ipo.cds.etl.theme.vrn;

import nl.ipo.cds.validation.DefaultValidatorContext;
import nl.ipo.cds.validation.ValidationReporter;
import nl.ipo.cds.validation.gml.codelists.CodeListFactory;

public class Context extends DefaultValidatorContext<Message, Context> {
	
	private final String uuid;
	
	public String getUuid() {
		return uuid;
	}

	public Context(final CodeListFactory codeListFactory,
			final ValidationReporter<Message, Context> reporter, String uuid) {
		super(codeListFactory, reporter);
		
		this.uuid=uuid;
	}

}
