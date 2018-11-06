package asteroids.model.program.statements.actions;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.statements.Action;
import asteroids.model.program.types.DoubleType;

import java.util.Map;

/**
 * @author WimKunnen and Maarten Doclo
 */
public class Turn implements Action {

    public Turn(Expression<DoubleType> angle){
        super();
        this.angle = angle;
    }

    Expression angle;
    @Override
    public void execute(Program program) throws RuntimeException{
        if (program == null || program.getShip() == null)
            throw new RuntimeException();

        Ship ship = program.getShip();
        double angle = (double) this.angle.calculate(program).getType();
        if (angle >= 2 * Math.PI || angle < 0)
            throw new RuntimeException();
        ship.turn(angle);
    }

    @Override
    public double getExecutionTime(){
        return 0.2;
    }
}
