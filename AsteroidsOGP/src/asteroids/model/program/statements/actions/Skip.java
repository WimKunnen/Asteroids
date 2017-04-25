package asteroids.model.program.statements.actions;

import asteroids.model.Program;
import asteroids.model.program.statements.Action;

/**
 * Created by WimKunnen on 23/04/2017.
 */
public class Skip implements Action{
    @Override
    public void execute(Program program) {

    }

    @Override
    public double getExecutionTime(){
        return 0.2;
    }
}
