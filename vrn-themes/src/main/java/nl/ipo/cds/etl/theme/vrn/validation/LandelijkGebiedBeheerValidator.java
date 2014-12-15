/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.LandelijkGebiedBeheer;
import nl.ipo.cds.validation.execute.CompilerException;
import nl.ipo.cds.validation.gml.CodeExpression;

/**
 * @author annes
 *
 */
public class LandelijkGebiedBeheerValidator extends AbstractVrnValidator<LandelijkGebiedBeheer> {

	private final CodeExpression<Message, Context> statusBeheer = code("statusBeheer");
	private final CodeExpression<Message, Context> beheerPakket = code("beheerPakket");
	
	public LandelijkGebiedBeheerValidator(Map<Object, Object> validatorMessages) throws CompilerException {
		super(validatorMessages, LandelijkGebiedBeheer.class);
		// TODO Auto-generated constructor stub
	}

}
