package asteroids.model.program.statements;

import asteroids.model.Program;
import asteroids.model.program.Executable;
import asteroids.model.program.types.DoubleType;

/**
 * Created by WimKunnen on 23/04/2017.
 */
public interface Statement extends Executable{
     double getExecutionTime();
     void execute(Program program);
}
