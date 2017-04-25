package asteroids.model.program.expressions.entityexpressions;

import asteroids.model.Entity;
import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.EntityType;

/**
 * Created by WimKunnen on 25/04/2017.
 */
public class ClosestShip implements Expression<EntityType>{

    public ClosestShip(){


    }

    @Override
    public EntityType calculate(Program program) throws RuntimeException{

        Ship self = program.getShip();

        Ship closestSoFar = new Ship();
        double closestDistanceSoFar = 100000;

        for(Ship other : self.getWorld().getAllShips()){
            double distance = self.getDistanceBetween(other);
            if (distance < closestDistanceSoFar){
                closestDistanceSoFar = distance;
                closestSoFar = other;
            }
        }

        return new EntityType(closestSoFar);
    }

}
