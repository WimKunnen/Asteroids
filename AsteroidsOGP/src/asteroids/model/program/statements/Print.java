package asteroids.model.program.statements;

import asteroids.model.Program;
import asteroids.model.program.OneArgumentExecutable;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.Type;

/**
 * Created by WimKunnen on 23/04/2017.
 */
public class Print extends OneArgumentExecutable<Expression<? extends Type<?>>>
        implements Statement {

    public Print(Expression<? extends Type<?>> e) throws IllegalArgumentException {
        super(e);
    }

    @Override
    public void execute(Program program) throws RuntimeException {
        if (program == null)
            throw new RuntimeException();
        Type toPrint = (Type) getFirstArgument().calculate(program).getType();
        String message = toPrint.toString();
        program.getPrinted().add(toPrint);
        System.out.println(message);
    }
    @Override
    public double getExecutionTime(){
            return 0;
        }
}
