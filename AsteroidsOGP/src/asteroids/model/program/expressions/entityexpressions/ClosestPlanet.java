package asteroids.model.program.expressions.entityexpressions;

import asteroids.model.*;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.EntityType;

@SuppressWarnings("all")

/**
 * @author WimKunnen and Maarten Doclo
 */
public class ClosestPlanet implements Expression<EntityType>{

    public ClosestPlanet(){
    }

    @Override
    public EntityType calculate(Program program) throws RuntimeException{

        Ship theExecutor = program.getShip();
        World world = theExecutor.getWorld();

        MinorPlanet closestPlanet = (MinorPlanet) world.getAllEntities().stream()
                .filter((Entity q) -> q instanceof MinorPlanet)
                .min((Entity a, Entity b)
                        -> (int)Math.signum(a.getDistanceBetween(theExecutor) - b.getDistanceBetween(theExecutor)))
                .orElse(null);

        return new EntityType(closestPlanet);
    }
}
