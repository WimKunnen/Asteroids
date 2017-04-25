package asteroids.model.program.expressions;

import asteroids.model.Program;
import asteroids.model.program.types.DoubleType;

/**
 * Created by WimKunnen on 24/04/2017.
 */
public class DoubleLiteral implements Expression<DoubleType> {

    public DoubleLiteral(double value){
        this.type = new DoubleType(value);
    }

    @Override
    public DoubleType calculate(Program program) throws RuntimeException{
        if(Double.isNaN(type.getType()))
            throw new RuntimeException();
        return type;
    }

    private final DoubleType type;
}
