package asteroids.model.program.expressions.operations;

import asteroids.model.Program;
import asteroids.model.program.TwoArgumentExecutable;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.DoubleType;

/**
 * @author WimKunnen and Maarten Doclo
 */
public class Subtraction extends TwoArgumentExecutable<Expression<DoubleType>,Expression<DoubleType>>
        implements Expression<DoubleType> {

    public Subtraction(Expression<DoubleType> left, Expression<DoubleType> right)
            throws IllegalArgumentException {
        super(left, right);
    }

    @Override
    public DoubleType calculate(Program program) {
        double left = this.getFirstArgument().calculate(program).getType();
        double right = this.getSecondArgument().calculate(program).getType();

        return new DoubleType(left - right);
    }
}
