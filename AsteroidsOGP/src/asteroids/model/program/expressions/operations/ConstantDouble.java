package asteroids.model.program.expressions.operations;

import asteroids.model.Program;
import asteroids.model.program.OneArgumentExecutable;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.DoubleType;

/**
 * @author WimKunnen and Maarten Doclo
 */
public class ConstantDouble extends OneArgumentExecutable<Expression<DoubleType>>
        implements Expression<DoubleType> {

    public ConstantDouble(Expression<DoubleType> argument)
            throws IllegalArgumentException {
        super(argument);
    }

    @Override
    public DoubleType calculate(Program program) {
        double value = this.getFirstArgument().calculate(program).getType();
        return new DoubleType(value);
    }
}
