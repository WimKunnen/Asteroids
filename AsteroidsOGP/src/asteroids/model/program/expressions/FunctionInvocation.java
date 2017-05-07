package asteroids.model.program.expressions;

import asteroids.model.Program;
<<<<<<< Updated upstream
import asteroids.model.program.FunctionDefinition;
=======
>>>>>>> Stashed changes
import asteroids.model.program.VariableArgumentExecutable;
import asteroids.model.program.types.DoubleType;
import asteroids.model.program.types.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WimKunnen on 25/04/2017.
 */
public class FunctionInvocation extends VariableArgumentExecutable implements Expression{

<<<<<<< Updated upstream
    public FunctionInvocation(List<Expression> arguments, String functionName){
=======
    public FunctionInvocation(List<Expression> arguments, String function){
>>>>>>> Stashed changes
        super(arguments);
        setFunction(function);
        setParameterMap(arguments);
        setFunctionName(functionName);
    }

    private Map<String, Expression> parameterMap = new HashMap<>();


    public void setParameterMap(List<Expression> parameters) {
        int i = 1;
        for (Expression expression : parameters){
            parameterMap.put("$" + Integer.toString(i),parameters.get(i-1));
        }
    }

    public Expression getParameterValue(String parameterName){
        return parameterMap.get(parameterName);
    }

    private String function;
    private void setFunction(String newFunction){
        this.function = newFunction;
    }

<<<<<<< Updated upstream
    private String functionName;
    private void setFunctionName(String functionName){
        this.functionName = functionName;
    }
    private String getFunctionName() {
        return functionName;
    }

    //TODO return statement.
    @Override
    public Type calculate(Program program) throws RuntimeException{
        program.setCurrentFunctionInvocation(this);
        FunctionDefinition function = program.getFunctionDefinition(getFunctionName());

//        if(this.function.getArguments().size() > this.getNbArguments())
//            throw new RuntimeException();
//        for(Statement statement : this.function.getBody()){
//            statement.execute(program);
//        }

        function.getBody().execute(program);
=======
    //TODO Fix this method!
    @Override
    public Type calculate(Program program) throws RuntimeException{
//        if(this.function.getArguments().size() > this.getNbArguments()) //TODO moet het bekje hier omgekeerd?
//            throw new RuntimeException();
//        this.function.getBody().execute(program);
>>>>>>> Stashed changes
        return new DoubleType(0);
    }

}
