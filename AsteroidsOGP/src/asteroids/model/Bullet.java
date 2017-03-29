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
        this.setMassOfEntity(this.getDensity());
    }

    /**
     * Default initializer which uses the initializer defined in the Entity super class.
     */
    public Bullet(){
        super();
        this.setDensity(7.8 * Math.pow(10, 12));
        this.setMassOfEntity(this.getDensity());
        this.setMinimumRadius();
        this.setRadius(this.getMinimumRadius());
    }

    /**
     * Variable registering the maximum number of bounces for this bullet.
     */
    private int maxNbBounces = 3;
    /**
     * @see implementation
     */
    public int getMaxNbBounces(){
        return this.maxNbBounces;
    }
    /**
     * @see implementation
     */
    private void setMaxNbBounces(int newMaxNb){
        this.maxNbBounces = newMaxNb;
    }

    /**
     * Variable registering the amount of bounces a bullet has done so far.
     */
    private int nbOfBounces = 0;
    /**
     * @see implementation
     */
    public int getNbOfBounces(){
        return this.nbOfBounces;
    }
    /**
     * @see implementation
     */
    protected void riseNbOfBounces() {
        this.nbOfBounces += 1;
    }

    public Ship source;
    /**
     * @see implementation
     */
    public Ship getSource(){
        return this.source;
    }
    /**
     * @see implementation
     */
    protected void setSource(Ship sourceShip){
        this.source = sourceShip;
    }

//    protected boolean canBeFired(){
//        if (!(this.fitsInBoundaries(this.getWorld())))
//            return false;
//
//    }
    /**
     * Boolean registering if the bullet has yet been outside of the ship after reloading.
     */
    private boolean beenOutOfShip = true;
    /**
     * @see implementation
     */
    protected boolean hasBeenOutOfShip(){
        return this.beenOutOfShip;
    }
    /**
     * @see implementation
     */
    protected void switchBeenOutOfShip(Boolean bool){
        this.beenOutOfShip = bool;
    }

}
