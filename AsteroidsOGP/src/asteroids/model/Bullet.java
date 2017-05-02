package asteroids.model;

import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;

import java.util.Iterator;

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
    public Bullet(double x, double y, double velocityX, double velocityY, double radius)
            throws IllegalArgumentException{
        super(x, y, velocityX, velocityY, radius);
        this.setDensity(7.8E12);
    }

    /**
     * Default initializer which uses the initializer defined in the Entity super class.
     */
    public Bullet(){
        super();
        this.setDensity(7.8E12);
        this.setRadius(this.getMinimumRadius());
    }

    /**
     * Variable registering the minimum radius of bullets.
     */
    private static double minimumRadius = 1;

    /**
     * Return the minimum radius of all bullets.
     * @see implementation
     */
    @Basic
    @Override
    public double getMinimumRadius(){
        return minimumRadius;
    }

    /**
     * Variable registering the maximum number of bounces for this bullet.
     */
    private int maxNbBounces = 3;

    /**
     * Return the maximum number of bounces of this bullet.
     * @see implementation
     */
    @Basic
    public int getMaxNbBounces(){
        return this.maxNbBounces;
    }

    /**
     * Set the maximum of bounces to the given integer.
     *
     * @param newMaxNb
     *        The new maximum number of bounces of this bullet.
     *
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
     * Return the number of bounces of this bullet.
     * @see implementation
     */
    @Basic
    public int getNbOfBounces(){
        return this.nbOfBounces;
    }

    /**
     * Rise the number of bounces with one.
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
     * Return the source ship of this bullet.
     * @see implementation
     */
    @Basic
    public Ship getSource(){
        return this.source;
    }
    /**
     * Change the source of this bullet to a given ship.
     *
     * @param sourceShip
     *        The ship that needs to be the source of this bullet.
     *
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
     * Return whether the bullet has yet been outside of the ship after reloading.
     * @see implementation
     */
    @Basic
    public boolean hasBeenOutOfShip(){
        return this.beenOutOfShip;
    }
    /**
     * Change the status of beenOutOfShip to the given boolean.
     *
     * @param bool
     *        The boolean to which beenOutOfShip needs to change.
     *
     * @see implementation
     */
    public void switchBeenOutOfShip(Boolean bool){
        this.beenOutOfShip = bool;
    }

    @Override
    public void terminate(){
        isTerminated = true;
        if (getWorld() != null)
            getWorld().removeEntity(this);
        if (getSource() != null){
            getSource().removeBullet(this);
        }
    }

    public boolean liesWithinShip(Ship ship){
        double distanceBetweenCentres = getDistanceBetween(ship) + getRadius() + ship.getRadius();
        return getRadius() < ship.getRadius() - distanceBetweenCentres;
    }
}
