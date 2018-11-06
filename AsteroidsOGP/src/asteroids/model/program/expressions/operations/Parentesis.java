package asteroids.model.program.expressions.operations;

import asteroids.model.Program;
import asteroids.model.program.OneArgumentExecutable;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.DoubleType;
import asteroids.model.program.types.Type;

/**
 * @author WimKunnen and Maarten Doclo
 */
public class Parentesis extends OneArgumentExecutable<Expression<DoubleType>>
        implements Expression {

    public Parentesis(Expression argument)
            throws IllegalArgumentException {
        super(argument);
    }

    @Override
    public Type calculate(Program program) {
        double value = this.getFirstArgument().calculate(program).getType();
        return new DoubleType(value);
    }

}
