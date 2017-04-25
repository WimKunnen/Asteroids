package asteroids.model.program.expressions.entityexpressions;

import asteroids.model.Program;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.EntityType;

/**
 * Created by WimKunnen on 24/04/2017.
 */
public class EntitySelfLiteral implements Expression<EntityType> {

    @Override
    public EntityType calculate(Program program) throws RuntimeException{
        if(program == null || program.getShip() == null)
            throw new RuntimeException();
        return new EntityType(program.getShip());
    }
}
