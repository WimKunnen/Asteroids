package asteroids.model.program.expressions.entityexpressions;

import asteroids.model.*;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.EntityType;

/**
 * Created by WimKunnen on 02/05/2017.
 */
public class ClosestPlanetoid implements Expression<EntityType>{

    public ClosestPlanetoid() {
    }

    @Override
    public EntityType calculate(Program program) throws RuntimeException{

        Ship theExecutor = program.getShip();
        World alderaan = theExecutor.getWorld();

        Planetoid closestPlanetoid = (Planetoid) alderaan.getAllEntities().stream()
                .filter((Entity q) -> q instanceof Planetoid)
                .min((Entity a, Entity b)
                        -> (int)Math.signum(a.getDistanceBetween(theExecutor) - b.getDistanceBetween(theExecutor))).get();

        return new EntityType(closestPlanetoid);
    }
}
