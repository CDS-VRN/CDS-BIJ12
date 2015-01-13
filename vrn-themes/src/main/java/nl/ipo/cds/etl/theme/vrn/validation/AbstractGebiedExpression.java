package nl.ipo.cds.etl.theme.vrn.validation;

import nl.ipo.cds.etl.theme.vrn.domain.AbstractGebied;
import nl.ipo.cds.validation.AttributeExpression;
import nl.ipo.cds.validation.ValidationMessage;
import nl.ipo.cds.validation.ValidatorContext;

public class AbstractGebiedExpression<K extends Enum<K> & ValidationMessage<K, C>, C extends ValidatorContext<K, C>, T extends AbstractGebied>
extends AttributeExpression<K, C, T> {

	public AbstractGebiedExpression(String name, Class<T> type) {
		super(name, type);
		// TODO Auto-generated constructor stub
	}

}
