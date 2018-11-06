package asteroids.model.program.expressions;

import asteroids.model.Program;
import asteroids.model.program.types.DoubleType;
import asteroids.model.program.types.Type;

/**
 * @author WimKunnen and Maarten Doclo
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
        return program.getCurrentFunctionInvocation().getParameterValue(getParameterName());
    }
}
