<<<<<<< Updated upstream
package asteroids.model.program.expressions;

import asteroids.model.Program;
import asteroids.model.program.types.Type;

/**
 * Created by Maarten Doclo on 6/05/2017.
 */
public class ReadParameter implements Expression{

    public ReadParameter(String parameterName){
        setParameterName(parameterName);
    }
    private String parameterName;

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    @Override
    public Type calculate(Program program){
        return (Type)program.getCurrentFunctionInvocation().getParameterValue(getParameterName());
    }
}
=======
package asteroids.model.program.expressions;

import asteroids.model.Program;
import asteroids.model.program.types.DoubleType;
import asteroids.model.program.types.Type;

/**
 * Created by WimKunnen on 05/05/2017.
 */

//TODO check Globals definition
public class ReadParameter
        implements Expression<DoubleType> {

    public ReadParameter(String argument)
            throws IllegalArgumentException {
        this.parameter = argument;
    }

    private String parameter;

    @Override
    public DoubleType calculate(Program program) {
        Type value = program.getGlobals().get(this.parameter);
        return (DoubleType) value;
    }
}
>>>>>>> Stashed changes
