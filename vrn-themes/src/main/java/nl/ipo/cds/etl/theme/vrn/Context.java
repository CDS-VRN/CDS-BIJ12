package nl.ipo.cds.etl.theme.vrn;

import nl.ipo.cds.validation.DefaultValidatorContext;
import nl.ipo.cds.validation.ValidationReporter;
import nl.ipo.cds.validation.gml.codelists.CodeListFactory;

public class Context extends DefaultValidatorContext<Message, Context> {
	
	private final String jdbcConnectionString;
	
	public String getJdbcConnectionString() {
		return jdbcConnectionString;
	}

	public Context(final CodeListFactory codeListFactory,
			final ValidationReporter<Message, Context> reporter, String jdbcConnectionString) {
		super(codeListFactory, reporter);
		
		this.jdbcConnectionString=jdbcConnectionString;
	}

}
