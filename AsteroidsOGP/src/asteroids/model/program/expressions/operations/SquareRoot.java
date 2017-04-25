package asteroids.model.program.expressions.operations;

import asteroids.model.Program;
import asteroids.model.program.OneArgumentExecutable;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.DoubleType;

/**
 * Created by WimKunnen on 24/04/2017.
 */
public class SquareRoot extends OneArgumentExecutable<Expression<DoubleType>>
        implements Expression<DoubleType> {

    public SquareRoot(Expression<DoubleType> argument)
            throws IllegalArgumentException {
        super(argument);
    }

    @Override
    public DoubleType calculate(Program program) throws RuntimeException{
        double left = this.getFirstArgument().calculate(program).getType();

        if(left < 0)
            throw new RuntimeException();

        return new DoubleType(Math.sqrt(left));
    }
}
