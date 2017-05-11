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
        setFunction(function);
        setParameterMap(arguments);
        setFunctionName(functionName);
        this.executionStack = new ArrayDeque<>();
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

    private boolean executed = false;
    private boolean getExecuted(){
        return executed;
    }
    private void setExecuted(boolean bool){
        executed = bool;
    }

    @Override
    public Type calculate(Program program) throws RuntimeException{
        program.setCurrentFunctionInvocation(this);
        FunctionDefinition function = program.getFunctionDefinition(getFunctionName());

        scheduleStatement(function.getBody()); //TODO

        while (getExecutionStack().size() > 0){
            Statement checkStatement = getExecutionStack().getFirst();
            if (program.getTotalTime() >= checkStatement.getExecutionTime()){
                Statement nextStatement = getExecutionStack().pop();
                if (nextStatement instanceof Action){
                    throw new RuntimeException();
                }
                program.continueProgram();
                nextStatement.execute(program);
                program.setTotalTime(program.getTotalTime() - nextStatement.getExecutionTime());
//                if (getExecutionStack().size() == 0){
//                    executed = true;
//                }
            }
            else{
                program.hold();
                break;
            }
        }

        if (getExecuted()) {
            program.setCurrentFunctionInvocation(null);
            program.emptyLocals();
        }
        return toReturn;
    }

}
