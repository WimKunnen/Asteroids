package asteroids.model.program.statements;

import asteroids.model.Program;

/**
 * @author WimKunnen and Maarten Doclo
 */
public class Break implements Statement{

	public Break() throws IllegalArgumentException {
            super();
    }

    @Override
    public void execute(Program program) throws RuntimeException {
        if (program == null)
            throw new RuntimeException();

        if (program.getCurrentWhile() == null){
            throw new RuntimeException();
        }

        while (program.getExecutionStack().getFirst() != program.getCurrentWhile()){
            program.getExecutionStack().removeFirst();
        }
        program.getExecutionStack().removeFirst();

    }

    @Override
    public double getExecutionTime(){
        return 0;
    }
}
