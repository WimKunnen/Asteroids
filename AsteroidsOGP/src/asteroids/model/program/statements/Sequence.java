package asteroids.model.program.statements;

import asteroids.model.Program;
import asteroids.model.program.VariableArgumentExecutable;

import java.util.List;

/**
 * @author WimKunnen and Maarten Doclo
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

        if (program.getCurrentFunctionInvocation() == null) {
            for (int i = getNbArguments() - 1; i >= 0; i--) {
                program.scheduleStatement(getArgumentAt(i));
            }
        }
        else {
            for (int i = getNbArguments() - 1; i >= 0; i--) {
                program.getCurrentFunctionInvocation().scheduleStatement(getArgumentAt(i));
            }
        }
    }

    @Override
    public double getExecutionTime(){
        return 0;
    }
}
