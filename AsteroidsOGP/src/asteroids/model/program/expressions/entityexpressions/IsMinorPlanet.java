package asteroids.model.program.expressions;

import asteroids.model.Entity;
import asteroids.model.MinorPlanet;
import asteroids.model.Program;
import asteroids.model.program.OneArgumentExecutable;
import asteroids.model.program.types.BooleanType;
import asteroids.model.program.types.EntityType;

/**
 * Created by WimKunnen on 24/04/2017.
 */
public class IsMinorPlanet extends OneArgumentExecutable<Expression<EntityType>>
        implements Expression<BooleanType> {

    public IsMinorPlanet(Expression<EntityType> argument)
            throws IllegalArgumentException {
        super(argument);
    }

    @Override
    public BooleanType calculate(Program program) throws RuntimeException{
        Entity e = this.getFirstArgument().calculate(program).getType();
        return new BooleanType(e instanceof MinorPlanet);
    }
}
