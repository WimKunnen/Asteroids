package asteroids.model.program.expressions.operations;

import asteroids.model.Program;
import asteroids.model.program.TwoArgumentExecutable;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.BooleanType;
import asteroids.model.program.types.Type;

/**
 * @author WimKunnen and Maarten Doclo
 */
public class Equality extends TwoArgumentExecutable<Expression<? extends Type<?>>, Expression<? extends Type<?>>>
        implements Expression<BooleanType> {

    public Equality(Expression<? extends Type<?>> left, Expression<? extends Type<?>> right)
            throws IllegalArgumentException {
        super(left, right);
    }

    @Override
    public BooleanType calculate(Program program) {
        Object left = this.getFirstArgument().calculate(program).getType();
        Object right = this.getSecondArgument().calculate(program).getType();

        if(left == null)
            return new BooleanType(right == null);

        return new BooleanType(left.equals(right));
    }
}
