package asteroids.model.program.statements;

import asteroids.model.Program;
import asteroids.model.program.TwoArgumentExecutable;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.BooleanType;

/**
 * Created by WimKunnen on 23/04/2017.
 */
public class While extends TwoArgumentExecutable<Expression<BooleanType>, Statement>
        implements Statement {

    public While(Expression<BooleanType> condition, Statement body) throws IllegalArgumentException {
        super(condition, body);
    }

    @Override
    public void execute(Program program) throws RuntimeException {
        if (program == null)
            throw new RuntimeException();

        if(getFirstArgument().calculate(program).getType()) {
            program.scheduleStatement(this);
            program.scheduleStatement(getSecondArgument());
        }
    }
    @Override
    public double getExecutionTime(){
        return 0;
    }
}
