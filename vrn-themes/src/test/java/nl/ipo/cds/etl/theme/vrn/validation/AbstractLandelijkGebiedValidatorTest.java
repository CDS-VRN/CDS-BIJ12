/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.AbstractGebied;

import org.junit.Test;

/**
 * @author reinoldp
 * 
 */
public abstract class AbstractLandelijkGebiedValidatorTest<G extends AbstractGebied, V extends AbstractVrnValidator<G>>
		extends AbstractVrnValidatorTest<G, V> {

	/**
	 * Test method for
	 * {@link nl.ipo.cds.etl.theme.vrn.validation.ProvinciaalGebiedBeheerValidator#getDoelBeheerValidator()}.
	 */
	@Test
	public final void testGetDoelValidatorNull() {
		// doel beheer is required for Landelijk thema
		final String validationName = doelValidationName();
		run(validationName).with(null).assertOnlyKey(Message.ATTRIBUTE_NULL);

	}
}
