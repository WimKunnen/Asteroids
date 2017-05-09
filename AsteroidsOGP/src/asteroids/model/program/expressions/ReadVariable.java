package asteroids.model.program.expressions;

import asteroids.model.Program;
import asteroids.model.program.OneArgumentExecutable;
import asteroids.model.program.types.Type;

/**
 * Created by Maarten Doclo on 6/05/2017.
 */
public class ReadVariable implements Expression<Type<?>>{

    public ReadVariable(String variableName){
        setVariableName(variableName);
    }
    private String variableName;

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public Type calculate(Program program){
        if (program.getCurrentFunctionInvocation() == null){
            return program.getVariableValue(getVariableName());
        }
        else{
            return program.getLocalVariableValue(getVariableName());
        }
    }

}
