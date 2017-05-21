package asteroids.model.program.expressions.entityexpressions;

import asteroids.model.Program;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.EntityType;

@SuppressWarnings("all")

/**
 * @author WimKunnen and Maarten Doclo
 */
public class EntitySelfLiteral implements Expression<EntityType> {

    @Override
    public EntityType calculate(Program program) throws RuntimeException{
        if(program == null || program.getShip() == null)
            throw new RuntimeException();
        return new EntityType(program.getShip());
    }
}
