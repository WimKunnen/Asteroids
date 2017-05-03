package asteroids.model.program.expressions.entityexpressions;

import asteroids.model.Entity;
import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.EntityType;

/**
 * Created by WimKunnen on 25/04/2017.
 */
public class ClosestShip implements Expression<EntityType>{

    public ClosestShip() {
    }

    @Override
    public EntityType calculate(Program program) throws RuntimeException{

        Ship theExecutor = program.getShip();
        World alderaan = theExecutor.getWorld();

        Ship closestShip = (Ship) alderaan.getAllEntities().stream()
                .filter((Entity q) -> q instanceof Ship && q != theExecutor)
                .min((Entity a, Entity b)
                        -> (int)Math.signum(a.getDistanceBetween(theExecutor) - b.getDistanceBetween(theExecutor)))
                .orElse(null);

        return new EntityType(closestShip);
    }
}
