package asteroids.model.program.expressions;

import asteroids.model.Program;
import asteroids.model.program.TwoArgumentExecutable;
import asteroids.model.program.types.BooleanType;
import asteroids.model.program.types.Type;

/**
 * Created by WimKunnen on 24/04/2017.
 */
public class Inequality extends TwoArgumentExecutable<Expression<? extends Type<?>>, Expression<? extends Type<?>>>
        implements Expression<BooleanType> {

    public Inequality(Expression<? extends Type<?>> left, Expression<? extends Type<?>> right)
            throws IllegalArgumentException {
        super(left, right);
    }

    @Override
    public BooleanType calculate(Program program) {
        Object left = this.getFirstArgument().calculate(program).getType();
        Object right = this.getSecondArgument().calculate(program).getType();

        if(left == null)
            return new BooleanType(right != null);

        return new BooleanType(!left.equals(right));
    }
}
