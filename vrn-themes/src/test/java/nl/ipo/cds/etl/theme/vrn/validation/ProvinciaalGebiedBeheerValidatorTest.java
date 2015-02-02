/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Collections;

import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.etl.postvalidation.BulkValidator;
import nl.ipo.cds.etl.postvalidation.IGeometryStore;
import nl.ipo.cds.etl.test.ValidationRunner;
import nl.ipo.cds.etl.theme.vrn.Constants;
import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.ProvinciaalGebiedBeheer;

import org.deegree.commons.tom.ows.CodeType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author reinoldp
 * 
 */
public class ProvinciaalGebiedBeheerValidatorTest {

	private ProvinciaalGebiedBeheerValidator validator;
	private ValidationRunner<ProvinciaalGebiedBeheer, Message, Context> runner;

	@Before
	public void createValidator() throws Exception {
		validator = new ProvinciaalGebiedBeheerValidator(Collections.emptyMap());
		validator.setGeometryStore(Mockito.mock(IGeometryStore.class));
		validator.setManagerDao(Mockito.mock(ManagerDao.class));
		validator.setBulkValidator(Mockito.mock(BulkValidator.class));
		runner = new ValidationRunner<>(validator, ProvinciaalGebiedBeheer.class);
	}

	private ValidationRunner<ProvinciaalGebiedBeheer, Message, Context>.Runner run(final String validationName) {
		return runner.validation(validationName);
	}

	/**
	 * Test method for
	 * {@link nl.ipo.cds.etl.theme.vrn.validation.ProvinciaalGebiedBeheerValidator#getDoelBeheerValidator()}.
	 */
	@Test
	public final void testGetDoelbeheerValidator() {
		// doel beheer is optional
		run("doelBeheer").with(null).assertNoMessages();

		// correct code space and correct code
		run("doelBeheer").withCodeList(Constants.CODESPACE_DOEL_REALISATIE, "NURG")
				.with(new CodeType("NURG", Constants.CODESPACE_DOEL_REALISATIE)).assertNoMessages();

		// illegal code space
		run("doelBeheer").with(new CodeType("NURG", "Wrong code space")).assertOnlyKey(
				Message.ATTRIBUTE_CODE_CODESPACE_INVALID);

		// illegal code
		run("doelBeheer").withCodeList(Constants.CODESPACE_DOEL_REALISATIE, "NURG")
				.with(new CodeType("Illegal code", Constants.CODESPACE_DOEL_REALISATIE))
				.assertOnlyKey(Message.ATTRIBUTE_CODE_INVALID);

		// two valid codes
		run("doelBeheer").withCodeList(Constants.CODESPACE_DOEL_REALISATIE, "NURG", "Maaswerken")
				.with(new CodeType("NURG;Maaswerken", Constants.CODESPACE_DOEL_REALISATIE)).assertNoMessages();

		// one invalid code
		run("doelBeheer").withCodeList(Constants.CODESPACE_DOEL_REALISATIE, "NURG", "Maaswerken")
				.with(new CodeType("NURG;invalid", Constants.CODESPACE_DOEL_REALISATIE))
				.assertOnlyKey(Message.ATTRIBUTE_CODE_INVALID);
	}

}
