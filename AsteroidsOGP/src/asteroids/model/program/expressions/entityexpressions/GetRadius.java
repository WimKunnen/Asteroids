package asteroids.model.program.expressions.entityexpressions;

import asteroids.model.Entity;
import asteroids.model.Program;
import asteroids.model.program.OneArgumentExecutable;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.DoubleType;
import asteroids.model.program.types.EntityType;

/**
 * Created by WimKunnen on 24/04/2017.
 */
public class GetRadius extends OneArgumentExecutable<Expression<EntityType>>
        implements Expression<DoubleType> {

    public GetRadius(Expression<EntityType> argument)
            throws IllegalArgumentException {
        super(argument);
    }

    @Override
    public DoubleType calculate(Program program) throws RuntimeException{
        Entity e = this.getFirstArgument().calculate(program).getType();
        if(e == null)
            throw new RuntimeException();
        return new DoubleType(e.getRadius());
    }
}
