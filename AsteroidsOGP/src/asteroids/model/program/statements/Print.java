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
        Object needsToBePrinted = getFirstArgument().calculate(program).getType();
        if(needsToBePrinted != null) {
            String toPrint = needsToBePrinted.toString();
            program.getPrinted().add(needsToBePrinted);
            System.out.println(toPrint);
        }else{
            program.getPrinted().add(null);
            System.out.println("null");
        }
    }
    @Override
    public double getExecutionTime(){
            return 0;
        }
}
