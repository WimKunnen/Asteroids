package asteroids.model.program.statements;

import asteroids.model.Program;

/**
 * Created by WimKunnen on 24/04/2017.
 */
public class Break implements Statement{

	public Break() throws IllegalArgumentException {
            super();
        }

    @Override
    public void execute(Program program) throws RuntimeException {
        if (program == null)
            throw new RuntimeException();

        //program.getExecutionStack().removeLastOccurrence(Statement = While);
    }

    @Override
    public double getExecutionTime(){
        return 0;
    }
}
