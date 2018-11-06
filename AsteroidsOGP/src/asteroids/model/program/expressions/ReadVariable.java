package asteroids.model.program.expressions;

import asteroids.model.Program;
import asteroids.model.program.types.Type;

@SuppressWarnings("all")

/**
 * @author WimKunnen and Maarten Doclo
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
        if (!program.functionInvocationBusy()){
            return program.getVariableValue(getVariableName());
        }
        else{
            if (program.getCurrentFunctionInvocation().getLocals().containsKey(getVariableName())) {
                return program.getCurrentFunctionInvocation().getLocalVariableValue(getVariableName());
            }
            else{
                return program.getVariableValue(getVariableName());
            }
        }
    }

}
