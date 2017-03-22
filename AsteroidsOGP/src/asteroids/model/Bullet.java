package asteroids.model;

import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;

/**
 * @author Wim Kunnen and Maarten Doclo.
 */

public class Bullet extends Entity {

    /**
     * Initializes a new Bullet by using the initializer defined in the Entity super class.
     * @param   x
     *          The initial position of the new bullet along the x-axis.
     *
     * @param   y
     *          The initial position of the new bullet along the y-axis.
     *
     * @param   velocityX
     *          The initial velocity of the new bullet along the x-axis.
     *
     * @param   velocityY
     *          The initial velocity of the new bullet along the y-axis.
     *
     * @invar   A bullet cannot be held by a world and a ship at the same time.
     *          | getWorld() == null || getSource() == null
     *
     */
    public Bullet(double x, double y, double velocityX, double velocityY, double radius) throws IllegalArgumentException{
        super(x, y, velocityX, velocityY, radius);
        this.setDensity(7.8 * Math.pow(10, 12));
    }

    /**
     * Default initializer which uses the initializer defined in the Entity super class.
     */
    public Bullet(){
        super();
        this.setDensity(7.8 * Math.pow(10, 12));
        this.setMinimumRadius();
        this.setRadius(this.getMinimumRadius());
    }

    private int maxNbBounces = 3;
    public int getMaxNbBounces(){
        return this.maxNbBounces;
    }
    private void setMaxNbBounces(int newMaxNb){
        this.maxNbBounces = newMaxNb;
    }
    private int nbOfBounces = 0;
    public int getNbOfBounces(){
        return this.nbOfBounces;
    }
    protected void riseNbOfBounces() {
        this.nbOfBounces += 1;
    }

    public Ship source;
    public Ship getSource(){
        return this.source;
    }
    protected void setSource(Ship sourceShip){
        if (sourceShip.getWorld() == this.getWorld())
            this.source = sourceShip;
    }


}
