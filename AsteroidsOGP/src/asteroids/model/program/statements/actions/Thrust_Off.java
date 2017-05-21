package asteroids.model.program.statements.actions;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.program.statements.Action;

/**
 * @author WimKunnen and Maarten Doclo
 */
public class Thrust_Off implements Action {

    public Thrust_Off(){
        super();
    }

    @Override
    public void execute(Program program) throws RuntimeException{
        if (program == null || program.getShip() == null)
            throw new RuntimeException();

        Ship ship = program.getShip();

        ship.thrustOff();
    }

    @Override
    public double getExecutionTime(){
        return 0.2;
    }
}
