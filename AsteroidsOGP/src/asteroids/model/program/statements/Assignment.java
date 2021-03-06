package asteroids.model.program.statements;

import asteroids.model.Program;
import asteroids.model.program.OneArgumentExecutable;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.DoubleType;
import asteroids.model.program.types.Type;

/**
 * @author WimKunnen and Maarten Doclo
 */
public class Assignment extends OneArgumentExecutable<Expression<? extends Type<?>>>
        implements Statement {

    public Assignment(String variableName, Expression<? extends Type<?>> rhs) throws IllegalArgumentException {
        super(rhs);
        this.variableName = variableName;
    }

    @Override
    public void execute(Program program) throws RuntimeException {
        if (program == null)
            throw new RuntimeException();

        if (!program.functionInvocationBusy()){
            if (program.getFunctionMap().containsKey(variableName)){
                throw new RuntimeException();
            }
            program.setVariableValue(variableName, getFirstArgument().calculate(program));
        }
        else{
            program.getCurrentFunctionInvocation().setLocalVariableValue(variableName, getFirstArgument().calculate(program));
        }
    }

    @Override
    public double getExecutionTime(){
        return 0;
    }

    private String variableName;
}
