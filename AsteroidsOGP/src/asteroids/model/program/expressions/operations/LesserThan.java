package asteroids.model.program.expressions;

import asteroids.model.Program;
import asteroids.model.program.TwoArgumentExecutable;
import asteroids.model.program.types.BooleanType;
import asteroids.model.program.types.DoubleType;

/**
 * Created by WimKunnen on 24/04/2017.
 */
public class LesserThan extends TwoArgumentExecutable<Expression<DoubleType>, Expression<DoubleType>>
        implements Expression<BooleanType> {

    public LesserThan(Expression<DoubleType> left, Expression<DoubleType> right)
            throws IllegalArgumentException {
        super(left, right);
    }

    @Override
    public BooleanType calculate(Program program) {
        double left = this.getFirstArgument().calculate(program).getType();
        double right = this.getSecondArgument().calculate(program).getType();

        return new BooleanType(left < right);
    }
}
