<<<<<<< Updated upstream
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
        return program.getVariableValue(getVariableName());
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
//TODO check Globals definition.
public class ReadVariable
        implements Expression<DoubleType> {

    public ReadVariable(String argument)
            throws IllegalArgumentException {
        this.variable = argument;
    }

    private String variable;

    @Override
    public DoubleType calculate(Program program) {
        Type value = program.getGlobals().get(this.variable);
        return (DoubleType) value;
    }
}
>>>>>>> Stashed changes
