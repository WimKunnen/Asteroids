package asteroids.model.program.expressions;

import asteroids.model.Program;
import asteroids.model.program.types.EntityType;

/**
 * Created by WimKunnen on 24/04/2017.
 */
public class EntityNullLiteral implements Expression<EntityType> {

    @Override
    public EntityType calculate(Program program) {
        return EntityType.NULL_REFERENCE;
    }
}
