package nl.ipo.cds.etl.theme.vrn;

import javax.sql.DataSource;

import nl.ipo.cds.validation.DefaultValidatorContext;
import nl.ipo.cds.validation.ValidationReporter;
import nl.ipo.cds.validation.gml.codelists.CodeListFactory;

public class Context extends DefaultValidatorContext<Message, Context> {
	
	private final DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public Context(final CodeListFactory codeListFactory,
			final ValidationReporter<Message, Context> reporter, DataSource dataSource) {
		super(codeListFactory, reporter);
		
		this.dataSource=dataSource;
	}

}
