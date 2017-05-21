package asteroids.model.program.expressions.operations;

import asteroids.model.Program;
import asteroids.model.program.OneArgumentExecutable;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.BooleanType;

/**
 * @author WimKunnen and Maarten Doclo
 */
public class BooleanNegation extends OneArgumentExecutable<Expression<BooleanType>>
        implements Expression<BooleanType> {

    public BooleanNegation(Expression<BooleanType> argument)
            throws IllegalArgumentException {
        super(argument);
    }

    @Override
    public BooleanType calculate(Program program) {
        boolean value = this.getFirstArgument().calculate(program).getType();
        return new BooleanType(!value);
    }
}
