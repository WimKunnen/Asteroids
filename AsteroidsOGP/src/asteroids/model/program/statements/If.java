package asteroids.model.program.statements;

import asteroids.model.Program;
import asteroids.model.program.ThreeArgumentExecutable;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.BooleanType;

/**
 * @author WimKunnen and Maarten Doclo
 */
public class If extends ThreeArgumentExecutable<Expression<BooleanType>, Statement, Statement>
        implements Statement {

    public If(Expression<BooleanType> condition, Statement then, Statement otherwise) throws IllegalArgumentException {
        super(condition, then, otherwise);
    }

    @Override
    public void execute(Program program) throws RuntimeException {
        if (program == null)
            throw new RuntimeException();
        if (getFirstArgument().calculate(program) == null)
            throw new RuntimeException();
        if (!program.functionInvocationBusy()) {
            if (getFirstArgument().calculate(program).getType())
                program.scheduleStatement(getSecondArgument());
            else
                program.scheduleStatement(getThirdArgument());
        }
        else{
            if (getFirstArgument().calculate(program).getType())
                program.getCurrentFunctionInvocation().scheduleStatement(getSecondArgument());
            else
                program.getCurrentFunctionInvocation().scheduleStatement(getThirdArgument());
        }
    }
    @Override
    public double getExecutionTime(){
        return 0;
    }
}
