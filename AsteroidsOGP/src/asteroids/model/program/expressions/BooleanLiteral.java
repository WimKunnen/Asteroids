package asteroids.model.program.expressions;

import asteroids.model.Program;
import asteroids.model.program.types.BooleanType;

/**
 * Created by WimKunnen on 23/04/2017.
 */
public class BooleanLiteral implements Expression<BooleanType> {

    public static final BooleanLiteral TRUE_LITERAL = new BooleanLiteral(true);
    public static final BooleanLiteral FALSE_LITERAL = new BooleanLiteral(false);

    public BooleanLiteral(boolean value){
        this.type = new BooleanType(value);
    }

    private final BooleanType type;

    @Override
    public BooleanType calculate(Program program) {
        return this.type;
    }

}
