package asteroids.model.program.expressions;

import asteroids.model.Program;
import asteroids.model.program.Executable;
import asteroids.model.program.FunctionDefinition;
import asteroids.model.program.VariableArgumentExecutable;
import asteroids.model.program.statements.Statement;
import asteroids.model.program.types.BooleanType;
import asteroids.model.program.types.DoubleType;
import asteroids.model.program.types.Type;

import java.util.List;

/**
 * Created by WimKunnen on 25/04/2017.
 */
public class FunctionInvocation extends VariableArgumentExecutable implements Expression{

    public FunctionInvocation(List<Type> arguments, FunctionDefinition function){
        super(arguments);
        setFunction(function);
    }

    private FunctionDefinition function;
    private void setFunction(FunctionDefinition newFunction){
        this.function = newFunction;
    }

    //TODO return statement.
    @Override
    public Type calculate(Program program) throws RuntimeException{
        if(this.function.getArguments().size() > this.getNbArguments())
            throw new RuntimeException();
        for(Statement statement : this.function.getBody()){
            statement.execute(program);
        }
        return new DoubleType(0);
    }

}
