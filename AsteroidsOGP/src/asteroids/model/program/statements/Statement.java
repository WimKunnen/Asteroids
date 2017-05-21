package asteroids.model.program.statements;

import asteroids.model.Program;
import asteroids.model.program.Executable;
import asteroids.model.program.types.DoubleType;

/**
 * @author WimKunnen and Maarten Doclo
 */
public interface Statement extends Executable{
     double getExecutionTime();
     void execute(Program program);
}
