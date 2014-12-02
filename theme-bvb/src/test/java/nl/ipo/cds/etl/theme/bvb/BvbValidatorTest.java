package nl.ipo.cds.etl.theme.bvb;

import static nl.ipo.cds.etl.theme.bvb.Message.ATTRIBUTE_NULL;
import static nl.ipo.cds.etl.theme.bvb.Message.GEOMETRY_POINT_REQUIRED;

import java.util.Collections;

import nl.ipo.cds.etl.test.GeometryConstants;
import nl.ipo.cds.etl.test.ValidationRunner;

import org.junit.Before;
import org.junit.Test;

public class BvbValidatorTest {

	private BvbValidator validator;
	private ValidationRunner<Bvb, Message, Context> runner;
	private GeometryConstants geom;

	@Before
	public void createValidator() throws Exception {
		validator = new BvbValidator(Collections.emptyMap());
		runner = new ValidationRunner<>(validator, Bvb.class);
		geom = new GeometryConstants("EPSG:28992");
	}

	private ValidationRunner<Bvb, Message, Context>.Runner run(final String validationName) {
		return runner.validation(validationName);
	}

	@Test	
	public void testGeometry () throws Exception {

		run ("geometry")
			.with (null)
			.assertOnlyKey (ATTRIBUTE_NULL);		

		run ("geometry")
		    .with (geom.lineString (null))
		    .assertOnlyKey (GEOMETRY_POINT_REQUIRED);
	
		run ("geometry")
			.with (geom.point (1,2))
			.assertNoMessages ();
		
	}

}
