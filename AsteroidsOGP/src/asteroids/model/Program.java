package asteroids.model;

import asteroids.model.program.ProgramStatement;

/**
 * Created by WimKunnen on 18/04/2017.
 */
public class Program {

    private double executionTime;

    private double getExecutionTime(ProgramStatement statement){
        switch (statement){

            case TURN:
                return 0.2;

            case FIRE:
                return 0.2;

            case SKIP:
                return 0.2;

            case THRUSTON:
                return 0.2;

            case THRUSTOFF:
                return 0.2;

            default:
                return 0;
        }
    }

    private boolean onHold = false;

    private void hold(){
        this.onHold = true;
    }

    private void continueProgram(){
        this.onHold = false;
    }


    public void execute(){

    }
}
