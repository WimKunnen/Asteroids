package asteroids.model.program.expressions.entityexpressions;

import asteroids.model.Entity;
import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.program.OneArgumentExecutable;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.DoubleType;
import asteroids.model.program.types.EntityType;

/**
 * Created by WimKunnen on 24/04/2017.
 */
public class GetHeading
        implements Expression<DoubleType> {

public GetHeading(){
        }

@Override
public DoubleType calculate(Program program) throws RuntimeException{
        Ship theExecutor = program.getShip();
        if(theExecutor == null)
            throw new RuntimeException();
        return new DoubleType(((Ship) theExecutor).getHeading());
        }
}
