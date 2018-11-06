package asteroids.model.program.expressions;

import asteroids.model.Program;
import asteroids.model.program.Executable;
import asteroids.model.program.types.Type;

/**
 * Created by WimKunnen on 18/04/2017.
 */
public interface Expression <T extends Type<?>> extends Executable {

    T calculate(Program program) throws RuntimeException;

}
