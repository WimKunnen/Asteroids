package asteroids.model.program.expressions;

import asteroids.model.Program;
import asteroids.model.program.FunctionDefinition;
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

    public FunctionInvocation(List<Expression> arguments, String functionName){
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

    private FunctionDefinition function;
    private void setFunction(FunctionDefinition newFunction){
        this.function = newFunction;
    }

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
        return new DoubleType(0);
    }

}
