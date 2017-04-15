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
     * @param   radius
     *          The radius of this entity.
     *
     * @invar   A bullet cannot be held by a world and a ship at the same time.
     *          | getSource() == null || getWorld() == null || !getSource().getBullets.contains(this)
     *
     */
    public Bullet(double x, double y, double velocityX, double velocityY, double radius) throws IllegalArgumentException{
        super(x, y, velocityX, velocityY, radius);
        this.setDensity(7.8E12);
    }

    /**
     * Default initializer which uses the initializer defined in the Entity super class.
     */
    public Bullet(){
        super();
        this.setDensity(7.8 * Math.pow(10, 12));
        this.setRadius(this.getMinimumRadius());
    }

    private static double minimumRadius = 1;

    /**
     * @see implementation
     */
    @Basic
    public double getMinimumRadius(){
        return minimumRadius;
    }

    /**
     * Variable registering the maximum number of bounces for this bullet.
     */
    private int maxNbBounces = 3;

    /**
     * @see implementation
     */
    @Basic
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
    @Basic
    public int getNbOfBounces(){
        return this.nbOfBounces;
    }

    /**
     * @see implementation
     */
    public void riseNbOfBounces() {
        this.nbOfBounces += 1;
    }

    /**
     * Variable registering the bullets source ship.
     */
    public Ship source;
    /**
     * @see implementation
     */
    @Basic
    public Ship getSource(){
        return this.source;
    }
    /**
     * @see implementation
     */
    public void setSource(Ship sourceShip){
        this.source = sourceShip;
    }

    /**
     * Boolean registering if the bullet has yet been outside of the ship after reloading.
     */
    private boolean beenOutOfShip = true;
    /**
     * @see implementation
     */
    @Basic
    public boolean hasBeenOutOfShip(){
        return this.beenOutOfShip;
    }
    /**
     * @see implementation
     */
    public void switchBeenOutOfShip(Boolean bool){
        this.beenOutOfShip = bool;
    }

}
