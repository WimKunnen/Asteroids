package asteroids.model.program;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * Created by WimKunnen on 24/04/2017.
 */
public abstract class OneArgumentExecutable<F extends Executable> extends Executable.ArgumentExecutable {
    public OneArgumentExecutable(F first) throws IllegalArgumentException{
        if(!canHaveAsSubExecutable(first))
            throw new IllegalArgumentException();
        this.firstArgument = first;
    }

    @Basic @Raw
    public F getFirstArgument() {
        return firstArgument;
    }
    private F firstArgument;

    @Override @Raw
    public Executable[] getSubExecutables(){
        return new Executable[]{getFirstArgument()};
    }
}
