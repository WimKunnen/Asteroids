package asteroids.model;

import asteroids.model.program.FunctionDefinition;
import asteroids.model.program.expressions.FunctionInvocation;
import asteroids.model.program.statements.Statement;
import asteroids.model.program.statements.While;
import asteroids.model.program.types.Type;
import asteroids.part3.programs.internal.generated.AsteroidsProgramParser;
import asteroids.util.ModelException;
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
            this.functionMap.put(functionDefinition.getName(),functionDefinition);
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




    private List<Object> printed = new ArrayList<>();

    public List<Object> getPrinted(){
        return this.printed;
    }

    private double totalTime;
    public double getTotalTime() {
        return totalTime;
    }
    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public List<Object> execute(double timeDifference){

        double time = timeDifference + getTimeLeft();
        setTotalTime(time);

        while (getExecutionStack().size() > 0){

            Statement checkStatement = getExecutionStack().getFirst();
            if (getTotalTime() >= checkStatement.getExecutionTime()){
                Statement nextStatement = getExecutionStack().pop();
                continueProgram();
                nextStatement.execute(this);
                setTotalTime( getTotalTime() - nextStatement.getExecutionTime());
                if (getExecutionStack().size() == 0){
                    executed = true;
                }
            }
            else{
                hold();
                setTimeLeft(getTotalTime());
                break;
            }
        }

        if (executed){
            return this.getPrinted();}
        return null;
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



    public FunctionInvocation getCurrentFunctionInvocation() {
        if (currentFunctionInvocations.isEmpty()){
            return null;
        }
        return currentFunctionInvocations.getFirst();
    }
    public void setCurrentFunctionInvocation(FunctionInvocation invocation){
        this.currentFunctionInvocations.addFirst(invocation);
        System.out.println(currentFunctionInvocations);
    }
    public void stopCurrentFunctionInvocation(){
        currentFunctionInvocations.removeFirst();
        System.out.println("removing : " +currentFunctionInvocations);
    }

    private Deque<FunctionInvocation> currentFunctionInvocations = new ArrayDeque<>();



    public While getCurrentWhile() {
        if (currentWhiles.isEmpty()){
            return null;
        }
        return currentWhiles.getFirst();
    }
    public void setCurrentWhile(While whileStatement){
        this.currentWhiles.addFirst(whileStatement);
    }
    public void stopCurrentWhile(){
        currentWhiles.removeFirst();
    }

    private Deque<While> currentWhiles = new ArrayDeque<>();

}
