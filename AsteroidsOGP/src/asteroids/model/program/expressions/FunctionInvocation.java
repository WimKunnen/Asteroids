package asteroids.model.program.expressions;

import asteroids.model.Program;
import asteroids.model.program.FunctionDefinition;
import asteroids.model.program.VariableArgumentExecutable;
import asteroids.model.program.statements.Action;
import asteroids.model.program.statements.Statement;
import asteroids.model.program.types.DoubleType;
import asteroids.model.program.types.Type;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.*;

/**
 * Created by WimKunnen on 25/04/2017.
 */
public class FunctionInvocation extends VariableArgumentExecutable implements Expression{

    public FunctionInvocation(List<Expression> arguments, String functionName){
        super(arguments);
        setArguments(arguments);
        setFunction(function);
        setFunctionName(functionName);
        this.executionStack = new ArrayDeque<>();
    }

    private FunctionInvocation getCopy(){
        return new FunctionInvocation(getArgumentsAsExpressions(),getFunctionName());
    }

    private List<Expression> argumentsAsExpressions;
    public List<Expression> getArgumentsAsExpressions(){
        return this.argumentsAsExpressions;
    }
    public void setArguments(List<Expression> arguments){
        this.argumentsAsExpressions = arguments;
    }

    private Map<String, Type> parameterMap = new HashMap<>();

    public Type getParameterValue(String parameterName){
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

    private Type toReturn = null;

    public Type getToReturn() {
        return toReturn;
    }

    public void setToReturn(Type toReturn) {
        this.toReturn = toReturn;
    }

    private Deque<Statement> executionStack;
    public Deque<Statement> getExecutionStack(){
        return this.executionStack;
    }
    public void setExecutionStack(List<Statement> body) {
        this.executionStack = new ArrayDeque<Statement>(body);
    }

    @Raw
    public void scheduleStatement(Statement statement){
        if(statement != null)
            executionStack.push(statement);
    }

    private Map<String, Type<?>> locals = new HashMap<>();

    public Map<String, Type<?>> getLocals() {
        return locals;
    }


    public Type<?> getLocalVariableValue(String name) throws RuntimeException{
        Type<?> current = locals.get(name);
        if(current == null)
            throw new RuntimeException();

        return current;
    }
    public void setLocalVariableValue(String name, Type<?> value) throws RuntimeException{
        Type<?> current = locals.get(name);
        if(current != null && current.getClass() != value.getClass())
            throw new RuntimeException();

        locals.put(name, value);
    }

    @Override
    public Type calculate(Program program) throws RuntimeException{

        if (program.getAllFunctionInvocationsEver().contains(this)){
            return this.getCopy().calculate(program);
        }


        int i = 1;
        for (Expression expression : getArgumentsAsExpressions()){
            parameterMap.put("$" + Integer.toString(i),getArgumentsAsExpressions().get(i-1).calculate(program));
            i += 1;
        }
        program.setCurrentFunctionInvocation(this);
        FunctionDefinition function = program.getFunctionDefinition(getFunctionName());

        scheduleStatement(function.getBody());

        while (getExecutionStack().size() > 0){
            Statement nextStatement = getExecutionStack().pop();
            if (nextStatement instanceof Action){
                throw new RuntimeException();
            }
            nextStatement.execute(program);
        }

        program.stopCurrentFunctionInvocation();

        if (toReturn == null){
            throw new RuntimeException();
        }
        return toReturn;
    }

}

