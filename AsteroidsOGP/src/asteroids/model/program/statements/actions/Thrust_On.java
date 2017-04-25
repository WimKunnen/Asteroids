package asteroids.model.program.statements.actions;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.program.statements.Action;

/**
 * Created by WimKunnen on 24/04/2017.
 */
public class Thrust_On implements Action {

    public Thrust_On(){
        super();
    }

    @Override
    public void execute(Program program) throws RuntimeException{
        if (program == null || program.getShip() == null)
            throw new RuntimeException();

        Ship ship = program.getShip();

        ship.thrustOn();
    }

    @Override
    public double getExecutionTime(){
        return 0.2;
    }
}
