package asteroids.model;

import asteroids.model.program.statements.Statement;
import asteroids.model.program.types.Type;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.*;

/**
 * Created by WimKunnen on 18/04/2017.
 */
public class Program {

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
    private Deque<Statement> executionStack = new ArrayDeque<Statement>();

    private double executionTime;

    private boolean onHold = false;

    private void hold(){
        this.onHold = true;
    }

    private void continueProgram(){
        this.onHold = false;
    }

    private List<Type> printed = new ArrayList<>();

    public List<Type> getPrinted(){
        return this.printed;
    }

    public List<Type> execute(double timeDifference){

        for(Statement statement : getExecutionStack()){
            statement.execute(this);
        }
        return this.printed;
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
