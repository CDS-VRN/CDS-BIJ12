/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Collections;

import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.etl.AbstractValidator;
import nl.ipo.cds.etl.postvalidation.BulkValidator;
import nl.ipo.cds.etl.postvalidation.IGeometryStore;
import nl.ipo.cds.etl.test.ValidationRunner;
import nl.ipo.cds.etl.theme.vrn.Constants;
import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.AbstractGebied;
import nl.ipo.cds.etl.theme.vrn.domain.ProvinciaalGebiedBeheer;
import nl.ipo.cds.validation.execute.CompilerException;

import org.deegree.commons.tom.ows.CodeType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
		validator.setBulkValidator(Mockito.mock(BulkValidator.class));
		runner = new ValidationRunner<>(validator, getDomainClass());
	}

	protected abstract Class<G> getDomainClass();

	protected abstract V createValidator() throws CompilerException;

	protected ValidationRunner<G, Message, Context>.Runner run(final String validationName) {
		return runner.validation(validationName);
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
