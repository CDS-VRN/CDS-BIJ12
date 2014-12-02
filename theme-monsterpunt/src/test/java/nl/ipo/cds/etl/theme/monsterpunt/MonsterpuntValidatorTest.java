package nl.ipo.cds.etl.theme.monsterpunt;

import static nl.ipo.cds.etl.theme.monsterpunt.Message.GEOMETRY_EMPTY_MULTIGEOMETRY;
import static nl.ipo.cds.etl.theme.monsterpunt.Message.GEOMETRY_ONLY_SURFACE_OR_MULTISURFACE;
import static nl.ipo.cds.etl.theme.monsterpunt.Message.GEOMETRY_SRS_NULL;

import java.util.Collections;

import nl.ipo.cds.etl.test.GeometryConstants;
import nl.ipo.cds.etl.test.ValidationRunner;

import org.junit.Before;
import org.junit.Test;

public class MonsterpuntValidatorTest {

	private MonsterpuntValidator validator;
	private ValidationRunner<Monsterpunt, Message, Context> runner;
	private GeometryConstants geom;

	@Before
	public void createValidator() throws Exception {
		validator = new MonsterpuntValidator(Collections.emptyMap());
		runner = new ValidationRunner<>(validator, Monsterpunt.class);
		geom = new GeometryConstants("EPSG:28992");
	}

	private ValidationRunner<Monsterpunt, Message, Context>.Runner run(final String validationName) {
		return runner.validation(validationName);
	}

	@Test
	public void testLocatieValidation () throws Exception {
		assertNullGeometryValidation("locatie");
	}

	private void assertNullGeometryValidation (String attrName) throws Exception {

		run (attrName)
			.with (null)
			.assertNoMessages();

		run (attrName)
	    	.with (geom.point (1,2))
	    	.assertNoMessages();

		run (attrName)
	    	.with (geom.lineString ())
	    	.assertNoMessages();

		run (attrName)
    		.with (geom.multiPoint ())
    		.assertNoMessages();

		run (attrName)
			.with (geom.emptyMultiPolygon())
			.assertOnlyKey (GEOMETRY_EMPTY_MULTIGEOMETRY);

		run (attrName)
			.with (geom.polygon(null))
			.assertOnlyKey (GEOMETRY_SRS_NULL);

		run (attrName)
			.with (geom.polygon())
			.assertNoMessages ();

		run (attrName)
			.with (geom.multiPolygon())
			.assertNoMessages ();
	}


}
