package nl.ipo.cds.etl.theme.vrn;

import javax.sql.DataSource;

import com.vividsolutions.jts.index.SpatialIndex;
import nl.ipo.cds.validation.DefaultValidatorContext;
import nl.ipo.cds.validation.ValidationReporter;
import nl.ipo.cds.validation.gml.codelists.CodeListFactory;
import org.deegree.geometry.Geometry;

public class Context extends DefaultValidatorContext<Message, Context> {
	
	private final Geometry bronhouderGeometry;

	private final SpatialIndex spatialIndex;

	public SpatialIndex getSpatialIndex() {
		return spatialIndex;
	}


	/**
	 * Return bronhouder geometry if any.
	 * @return The geometry IFF any. Otherwise null.
	 */
	public Geometry getBronhouderGeometry() {
		return bronhouderGeometry;
	}

	public Context(final CodeListFactory codeListFactory,
			final ValidationReporter<Message, Context> reporter, SpatialIndex spatialIndex, Geometry bronhouderGeometry) {
		super(codeListFactory, reporter);
		
		this.bronhouderGeometry = bronhouderGeometry;
		this.spatialIndex = spatialIndex;
	}

}
