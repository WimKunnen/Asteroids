package asteroids.model.program.expressions;

import asteroids.model.Entity;
import asteroids.model.Program;
import asteroids.model.program.types.EntityType;

/**
 * Created by WimKunnen on 24/04/2017.
 */
public class EntityLiteral implements Expression<EntityType> {

    public EntityLiteral(Entity value){
        this.type = new EntityType(value);
    }

    @Override
    public EntityType calculate(Program program) throws RuntimeException{
        return type;
    }

    private final EntityType type;
}
