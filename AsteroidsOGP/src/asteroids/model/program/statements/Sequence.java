package asteroids.model.program.statements;

import asteroids.model.Program;
import asteroids.model.program.VariableArgumentExecutable;

import java.util.List;

/**
 * Created by WimKunnen on 23/04/2017.
 */
public class Sequence extends VariableArgumentExecutable<Statement>
        implements Statement {

    public Sequence(List<Statement> statements) throws IllegalArgumentException {
        super(statements);
    }

    @Override
    public void execute(Program program) throws RuntimeException {
        if (program == null)
            throw new RuntimeException();

        for (int i = getNbArguments()-1; i >= 0; i--) {
            program.scheduleStatement(getArgumentAt(i));
        }
    }

    @Override
    public double getExecutionTime(){
        return 0;
    }
}
