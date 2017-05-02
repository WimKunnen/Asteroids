package asteroids.model.program.expressions.entityexpressions;

import asteroids.model.*;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.EntityType;

/**
 * Created by WimKunnen on 02/05/2017.
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
