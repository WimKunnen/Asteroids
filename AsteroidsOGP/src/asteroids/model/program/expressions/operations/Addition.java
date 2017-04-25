package asteroids.model.program.expressions.operations;

import asteroids.model.Program;
import asteroids.model.program.TwoArgumentExecutable;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.DoubleType;

/**
 * Created by WimKunnen on 24/04/2017.
 */
public class Addition extends TwoArgumentExecutable<Expression<DoubleType>, Expression<DoubleType>>
        implements Expression<DoubleType> {

    public Addition(Expression<DoubleType> left, Expression<DoubleType> right)
            throws IllegalArgumentException {
        super(left, right);
    }

    @Override
    public DoubleType calculate(Program program) {
        double left = this.getFirstArgument().calculate(program).getType();
        double right = this.getSecondArgument().calculate(program).getType();

        return new DoubleType(left + right);
    }
}
