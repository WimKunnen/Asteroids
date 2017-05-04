package asteroids.model;

import asteroids.model.program.FunctionDefinition;
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

    public Program(List<FunctionDefinition> functions, List<Statement> body) {
        setFunctions(functions);
        setExecutionStack(body);
    }

    private List<FunctionDefinition> functions;

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
            executionStack.addLast(statement);
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

    private double timeLeft;

    private double getTimeLeft(){
        return this.timeLeft;
    }

    public void setTimeLeft(double timeLeft) {
        this.timeLeft = timeLeft;
    }

    private boolean onHold = false;

    private void hold(){
        this.onHold = true;
    }

    private void continueProgram(){
        this.onHold = false;
    }

    public boolean isExecuted = false;

    private List<Type> printed = new ArrayList<>();

    public List<Type> getPrinted(){
        return this.printed;
    }

    public List<Type> execute(double timeDifference){

        double time = timeDifference + getTimeLeft();

        for(Statement statement : getExecutionStack()){
            if (time >= 0.2) {
                continueProgram();
                statement.execute(this);
                time -= statement.getExecutionTime();
                getExecutionStack().removeFirst();
                if (getExecutionStack().size() == 0){
                    isExecuted = true;
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

    private Map<String, Type<?>> globals;



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
}
