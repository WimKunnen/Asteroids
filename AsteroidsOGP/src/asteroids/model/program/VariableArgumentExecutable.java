package asteroids.model.program;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.*;

/**
 * Created by WimKunnen on 24/04/2017.
 */

public abstract class VariableArgumentExecutable<T extends Executable> extends ArgumentExecutable {

    public VariableArgumentExecutable(List<T> arguments) throws IllegalArgumentException{
        if(arguments == null)
            throw new IllegalArgumentException();
        for(T argument : arguments){
            if(!canHaveAsSubExecutable(argument))
                throw new IllegalArgumentException();
        }
        this.arguments = new ArrayList<T>(arguments);
    }

    @Basic @Raw
    public T getArgumentAt(int i) throws IndexOutOfBoundsException{
        return arguments.get(i);
    }
    @Basic @Raw
    public int getNbArguments(){
        return arguments.size();
    }
    private final List<T> arguments;

    @Override @Raw
    public Executable[] getSubExecutables(){
        return arguments.<Executable>toArray(new Executable[0]);
    }

}
