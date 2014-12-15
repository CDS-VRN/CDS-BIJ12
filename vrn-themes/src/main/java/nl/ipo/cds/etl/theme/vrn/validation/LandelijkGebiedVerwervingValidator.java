/**
 * 
 */
package nl.ipo.cds.etl.theme.vrn.validation;

import java.util.Map;

import nl.ipo.cds.etl.theme.vrn.Context;
import nl.ipo.cds.etl.theme.vrn.Message;
import nl.ipo.cds.etl.theme.vrn.domain.LandelijkGebiedVerwerving;
import nl.ipo.cds.validation.execute.CompilerException;
import nl.ipo.cds.validation.gml.CodeExpression;

/**
 * @author annes
 *
 */
public class LandelijkGebiedVerwervingValidator extends AbstractVrnValidator<LandelijkGebiedVerwerving> {

	
	private final CodeExpression<Message, Context> statusVerwerving = code("StatusVerwerving");
	
	public LandelijkGebiedVerwervingValidator(Map<Object, Object> validatorMessages) throws CompilerException {
		super(validatorMessages, LandelijkGebiedVerwerving.class);
		// TODO Auto-generated constructor stub
	}

}
