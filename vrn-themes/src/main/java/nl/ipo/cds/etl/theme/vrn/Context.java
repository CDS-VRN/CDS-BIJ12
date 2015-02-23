package nl.ipo.cds.etl.theme.vrn;

import javax.sql.DataSource;

import nl.ipo.cds.validation.DefaultValidatorContext;
import nl.ipo.cds.validation.ValidationReporter;
import nl.ipo.cds.validation.gml.codelists.CodeListFactory;
import org.deegree.geometry.Geometry;

public class Context extends DefaultValidatorContext<Message, Context> {
	
	private final DataSource dataSource;
	private final Geometry bronhouderGeometry;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * Return bronhouder geometry if any.
	 * @return The geometry IFF any. Otherwise null.
	 */
	public Geometry getBronhouderGeometry() {
		return bronhouderGeometry;
	}

	public Context(final CodeListFactory codeListFactory,
			final ValidationReporter<Message, Context> reporter, DataSource dataSource, Geometry bronhouderGeometry) {
		super(codeListFactory, reporter);
		
		this.dataSource=dataSource;
		this.bronhouderGeometry = bronhouderGeometry;
	}

}
