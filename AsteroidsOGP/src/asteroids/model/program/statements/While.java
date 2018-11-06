package asteroids.model.program.statements;

import asteroids.model.Program;
import asteroids.model.program.TwoArgumentExecutable;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.BooleanType;

/**
 * @author WimKunnen and Maarten Doclo
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

        if (program.getCurrentWhile() != this){
            program.setCurrentWhile(this);
        }

        if(getFirstArgument().calculate(program).getType()) {
            if (!program.functionInvocationBusy()) {
                program.scheduleStatement(this);
                program.scheduleStatement(getSecondArgument());
            }
            else {
                program.getCurrentFunctionInvocation().scheduleStatement(this);
                program.getCurrentFunctionInvocation().scheduleStatement(getSecondArgument());
            }
        }
        else {
            program.stopCurrentWhile();
        }
    }
    @Override
    public double getExecutionTime(){
        return 0;
    }
}
