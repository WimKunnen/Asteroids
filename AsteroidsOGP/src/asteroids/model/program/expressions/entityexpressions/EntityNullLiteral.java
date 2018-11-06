package asteroids.model.program.expressions.entityexpressions;

import asteroids.model.Program;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.EntityType;

@SuppressWarnings("all")

/**
 * @author WimKunnen and Maarten Doclo
 */
public class EntityNullLiteral implements Expression<EntityType> {

    @Override
    public EntityType calculate(Program program) {
        return EntityType.NULL_REFERENCE;
    }
}
