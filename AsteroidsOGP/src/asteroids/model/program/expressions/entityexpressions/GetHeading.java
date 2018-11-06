package asteroids.model.program.expressions.entityexpressions;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.DoubleType;

@SuppressWarnings("all")

/**
 * @author WimKunnen and Maarten Doclo
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
