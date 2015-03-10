/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.etl.postvalidation.IGeometryStore;
import nl.ipo.cds.etl.test.ValidationRunner;
import nl.ipo.cds.etl.theme.vrn.Constants;
import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.AbstractGebied;
import nl.ipo.cds.validation.execute.CompilerException;

import org.deegree.commons.tom.ows.CodeType;
import org.deegree.cs.coordinatesystems.ICRS;
import org.deegree.cs.persistence.CRSManager;
import org.deegree.geometry.Geometry;
import org.deegree.geometry.io.WKTReader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.vividsolutions.jts.io.ParseException;

/**
 * @author reinoldp
 * 
 */
public abstract class AbstractVrnValidatorTest<G extends AbstractGebied, V extends AbstractVrnValidator<G>> {

	protected V validator;
	protected ValidationRunner<G, Message, Context> runner;

	@Before
	public void beforeTest() throws Exception {
		validator = createValidator();
		validator.setGeometryStore(Mockito.mock(IGeometryStore.class));
		validator.setManagerDao(Mockito.mock(ManagerDao.class));
		runner = new ValidationRunner<>(validator, getDomainClass());
	}

	protected abstract Class<G> getDomainClass();

	protected abstract V createValidator() throws CompilerException;

	protected ValidationRunner<G, Message, Context>.Runner run(final String validationName) {
		return runner.validation(validationName);
	}

	@Test
	public final void testGetGeometryValidator() throws ParseException {
		String validationName = "geometry";
		// null geometry
		run(validationName).with(null).assertOnlyKey(Message.ATTRIBUTE_NULL);
		// multisurface
		final Geometry multiSurface = createMultiSurface();
		run(validationName).withFeature("geometrie",multiSurface).assertOnlyKey(Message.GEOMETRY_ONLY_SURFACE);

	}

	private Geometry createMultiSurface() throws ParseException {
		ICRS srs = CRSManager.getCRSRef("EPSG:28992");
		WKTReader reader = new WKTReader(srs);
		Geometry g = reader
				.read("MULTIPOLYGON (((150000.000000 420000.000000,160000.000000 420000.000000,160000.000000 430000.000000,150000.000000 430000.000000,150000.000000 420000.000000)),((120000.000000 420000.000000,130000.000000 420000.000000,130000.000000 430000.000000,120000.000000 430000.000000,120000.000000 420000.000000)))");
		return g;
	}

	/**
	 * Test method for
	 * {@link nl.ipo.cds.etl.theme.vrn.validation.ProvinciaalGebiedBeheerValidator#getDoelBeheerValidator()}.
	 */
	@Test
	public final void testGetDoelRealisatieValidatorNotNull() {
		// doel beheer is optional
		final String validationName = doelValidationName();

		// correct code space and correct code
		run(validationName).withCodeList(Constants.CODESPACE_DOEL_REALISATIE, "NURG")
				.with(new CodeType("NURG", Constants.CODESPACE_DOEL_REALISATIE)).assertNoMessages();

		// illegal code space
		run(validationName).with(new CodeType("NURG", "Wrong code space")).assertOnlyKey(
				Message.ATTRIBUTE_CODE_CODESPACE_INVALID);

		// illegal code
		run(validationName).withCodeList(Constants.CODESPACE_DOEL_REALISATIE, "NURG")
				.with(new CodeType("Illegal code", Constants.CODESPACE_DOEL_REALISATIE))
				.assertOnlyKey(Message.ATTRIBUTE_CODE_INVALID);

		// two valid codes
		run(validationName).withCodeList(Constants.CODESPACE_DOEL_REALISATIE, "NURG", "Maaswerken")
				.with(new CodeType("NURG;Maaswerken", Constants.CODESPACE_DOEL_REALISATIE)).assertNoMessages();

		// one invalid code
		run(validationName).withCodeList(Constants.CODESPACE_DOEL_REALISATIE, "NURG", "Maaswerken")
				.with(new CodeType("NURG;invalid", Constants.CODESPACE_DOEL_REALISATIE))
				.assertOnlyKey(Message.ATTRIBUTE_CODE_INVALID);
	}

	protected abstract String doelValidationName();

}
