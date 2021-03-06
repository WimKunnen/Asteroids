package asteroids.model.program.statements;

import asteroids.model.Program;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.Type;

/**
 * @author WimKunnen and Maarten Doclo
 */
public class Return implements Statement{

    public Return(Expression value){
        this.setValue(value);
    }

    private Expression value;

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }

    @Override
    public double getExecutionTime(){
        return 0;
    }

    @Override
    public void execute(Program program){
        if (program.getCurrentFunctionInvocation() == null){
            throw new RuntimeException();
        }
        if (program.getCurrentFunctionInvocation().getToReturn() == null){
            program.getCurrentFunctionInvocation().setToReturn(getValue().calculate(program));
        }
    }
}
