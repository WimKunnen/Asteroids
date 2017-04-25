package asteroids.model.program.statements.actions;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.statements.Action;
import asteroids.model.program.types.DoubleType;

/**
 * Created by WimKunnen on 23/04/2017.
 */
public class Turn implements Action {

    public Turn(Expression<DoubleType> angle){
        super();
    }

    @Override
    public void execute(Program program) throws RuntimeException{
        if (program == null || program.getShip() == null)
            throw new RuntimeException();

        Ship ship = program.getShip();

        Ship other = new Ship();
        for(Ship ship1 : ship.getWorld().getAllShips())
            other = ship1;

        double angle = ship.getPosition().angleBetween(other.getPosition());

        ship.turn(angle);
    }

    public void execute(Program program, Double angle) throws RuntimeException{
        if (program == null || program.getShip() == null)
            throw new RuntimeException();

        Ship ship = program.getShip();

        ship.turn(angle);
    }

    @Override
    public double getExecutionTime(){
        return 0.2;
    }
}
