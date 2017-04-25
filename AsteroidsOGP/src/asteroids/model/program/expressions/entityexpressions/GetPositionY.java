package asteroids.model.program.expressions;

import asteroids.model.Entity;
import asteroids.model.Program;
import asteroids.model.program.OneArgumentExecutable;
import asteroids.model.program.types.DoubleType;
import asteroids.model.program.types.EntityType;

/**
 * Created by WimKunnen on 24/04/2017.
 */
public class GetPositionY extends OneArgumentExecutable<Expression<EntityType>>
        implements Expression<DoubleType> {

    public GetPositionY(Expression<EntityType> argument)
            throws IllegalArgumentException {
        super(argument);
    }

    @Override
    public DoubleType calculate(Program program) throws RuntimeException{
        Entity e = this.getFirstArgument().calculate(program).getType();
        if(e == null)
            throw new RuntimeException();
        return new DoubleType(e.getPosition().getY());
    }
}
