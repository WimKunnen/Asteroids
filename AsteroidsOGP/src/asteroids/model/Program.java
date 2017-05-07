package asteroids.model;

import asteroids.model.program.FunctionDefinition;
import asteroids.model.program.expressions.FunctionInvocation;
import asteroids.model.program.statements.Statement;
import asteroids.model.program.types.Type;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.*;

/**
 A class of programs for the Asteroid project.
 *
 * @author WimKunnen and Maarten Doclo
 *
 * @version 1.0
 */
public class Program {

    public Program(List<FunctionDefinition> functions, Statement body) {
        setFunctions(functions);
        this.executionStack = new ArrayDeque<>();
        scheduleStatement(body);
        setFunctionMap();
    }

    private List<FunctionDefinition> functions;

    private Map<String,FunctionDefinition> functionMap = new HashMap<>();

    public Map<String, FunctionDefinition> getFunctionMap() {
        return functionMap;
    }

    private void setFunctionMap(){
        for (FunctionDefinition functionDefinition : functions){
            getFunctionMap().put(functionDefinition.getName(),functionDefinition);
        }
    }
    public FunctionDefinition getFunctionDefinition(String functionName){
        return getFunctionMap().get(functionName);
    }

    public void setFunctions(List<FunctionDefinition> functions) {
        this.functions = functions;
    }

    public List<FunctionDefinition> getFunctions() {
        return functions;
    }

    private Ship ship;

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    @Raw
    public void scheduleStatement(Statement statement){
        if(statement != null)
            executionStack.push(statement);
    }

    @Raw
    public void unScheduleStatement(Statement statement){
        if(statement != null)
            executionStack.remove(statement);
    }
    public Deque<Statement> getExecutionStack(){
        return this.executionStack;
    }

    private Deque<Statement> executionStack;

    public void setExecutionStack(List<Statement> body) {
        this.executionStack = new ArrayDeque<Statement>(body);
    }

    private double timeLeft = 0;

    public double getTimeLeft(){
        return this.timeLeft;
    }

    public void setTimeLeft(double timeLeft) {
        this.timeLeft = timeLeft;
    }

    private boolean onHold = false;

    public void hold(){
        this.onHold = true;
    }

    public void continueProgram(){
        this.onHold = false;
    }

    public boolean executed = false;

    public boolean isExecuted() {
        return executed;
    }

    private List<Type> printed = new ArrayList<>();

    public List<Type> getPrinted(){
        return this.printed;
    }

    private double totalTime;
    public double getTotalTime() {
        return totalTime;
    }
    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public List<Type> execute(double timeDifference){

        double time = timeDifference + getTimeLeft();
        setTotalTime(time);

        for(Statement statement : getExecutionStack()){//TODO execution stack is being modified while being looped over
            if (time >= statement.getExecutionTime()) {
                continueProgram();
                statement.execute(this);
                time -= statement.getExecutionTime();
                getExecutionStack().removeFirst();
                if (getExecutionStack().size() == 0){
                    executed = true;
                }
            }

            else{
                hold();
                setTimeLeft(time);
                break;
            }
        }
        return this.getPrinted();
    }

    private Map<String, Type<?>> globals = new HashMap<>();

    public Map<String, Type<?>> getGlobals() {
        return globals;
    }

    public Type<?> getVariableValue(String name) throws RuntimeException{
        Type<?> current = globals.get(name);
        if(current == null)
            throw new RuntimeException();

        return current;
    }
    public void setVariableValue(String name, Type<?> value) throws RuntimeException{
        Type<?> current = globals.get(name);
        if(current != null && current.getClass() != value.getClass())
            throw new RuntimeException();

        globals.put(name, value);
    }

    private FunctionInvocation currentFunctionInvocation;

    public FunctionInvocation getCurrentFunctionInvocation() {
        return currentFunctionInvocation;
    }
    public void setCurrentFunctionInvocation(FunctionInvocation invocation){
        this.currentFunctionInvocation = invocation;
    }
}
