package asteroids.model.program.expressions;

import asteroids.model.Program;
import asteroids.model.program.types.DoubleType;

/**
 * @author WimKunnen and Maarten Doclo
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
