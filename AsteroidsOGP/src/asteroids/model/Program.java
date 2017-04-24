package asteroids.model;

/**
 * Created by WimKunnen on 18/04/2017.
 */
public class Program {

    private double executionTime;

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
