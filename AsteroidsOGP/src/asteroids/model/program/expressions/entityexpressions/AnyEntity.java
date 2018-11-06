package asteroids.model.program.expressions.entityexpressions;

import asteroids.model.*;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.EntityType;

@SuppressWarnings("all")

/**
 * @author WimKunnen and Maarten Doclo
 */
public class AnyEntity implements Expression<EntityType> {

    public AnyEntity() {
            }

    @Override
    public EntityType calculate(Program program) throws RuntimeException{

        Ship theExecutor = program.getShip();
        World alderaan = theExecutor.getWorld();

        Entity anyEntity =  alderaan.getAllEntities().stream()
                .findAny().get();
        return new EntityType(anyEntity);
    }
}
