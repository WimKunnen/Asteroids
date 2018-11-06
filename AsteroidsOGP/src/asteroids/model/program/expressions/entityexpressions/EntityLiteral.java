package asteroids.model.program.expressions.entityexpressions;

import asteroids.model.Entity;
import asteroids.model.Program;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.EntityType;

@SuppressWarnings("all")

/**
 * @author WimKunnen and Maarten Doclo
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
