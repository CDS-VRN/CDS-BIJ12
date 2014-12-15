/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.LandelijkGebiedInrichting;
import nl.ipo.cds.validation.execute.CompilerException;
import nl.ipo.cds.validation.gml.CodeExpression;

/**
 * @author annes
 *
 */
public class LandelijkGebiedInrichtingValidator extends AbstractVrnValidator<LandelijkGebiedInrichting> {

	private final CodeExpression<Message, Context> statusIngericht = code("statusIngericht");
	
	
	public LandelijkGebiedInrichtingValidator(Map<Object, Object> validatorMessages) throws CompilerException {
		super(validatorMessages, LandelijkGebiedInrichting.class);
		// TODO Auto-generated constructor stub
	}

}
