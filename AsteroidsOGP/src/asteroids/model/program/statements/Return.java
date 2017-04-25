package asteroids.model.program.statements;

import asteroids.model.Program;
import asteroids.model.program.expressions.Expression;

/**
 * Created by WimKunnen on 24/04/2017.
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

    }
}