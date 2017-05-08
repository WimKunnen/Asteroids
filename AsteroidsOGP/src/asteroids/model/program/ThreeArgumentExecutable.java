package asteroids.model.program;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * Created by WimKunnen on 24/04/2017.
 */
public abstract class ThreeArgumentExecutable<F extends Executable,S extends Executable,T extends Executable> extends Executable.ArgumentExecutable {

    public ThreeArgumentExecutable(F firstArgument, S secondArgument, T thirdArgument) throws IllegalArgumentException{
        if(!canHaveAsSubExecutable(firstArgument) || ! canHaveAsSubExecutable(secondArgument) || !canHaveAsSubExecutable(thirdArgument))
            throw new IllegalArgumentException();
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
        this.thirdArgument = thirdArgument;
    }

    @Basic @Raw
    public F getFirstArgument() {
        return firstArgument;
    }
    private F firstArgument;

    @Basic @Raw
    public S getSecondArgument() {
        return secondArgument;
    }
    private S secondArgument;

    @Basic @Raw
    public T getThirdArgument() {
        return thirdArgument;
    }
    private T thirdArgument;

    @Override @Raw
    public Executable[] getSubExecutables(){
        return new Executable[]{getFirstArgument(),getSecondArgument(),getThirdArgument()};
    }

}
