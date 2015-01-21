package nl.ipo.cds.etl.theme.vrn.validation;

import nl.ipo.cds.etl.theme.vrn.domain.AbstractGebied;
import nl.ipo.cds.validation.AttributeExpression;
import nl.ipo.cds.validation.ValidationMessage;
import nl.ipo.cds.validation.ValidatorContext;

/**
 * This expression is an attribute expression calling getAbstractGebied on the AbstractGebied object (which should return itself).
 * This is by far the easiest way of implementing, as the ExpressionExecutor always demands to provide a MethodHandle
 * to a method to invoke (not possible to simply return the object without a method involved).
 */
public class AbstractGebiedExpression<K extends Enum<K> & ValidationMessage<K, C>, C extends ValidatorContext<K, C>, T extends AbstractGebied>
extends AttributeExpression<K, C, T> {

    public AbstractGebiedExpression(String name, Class<T> type) {
        super(name, type);
    }

}
