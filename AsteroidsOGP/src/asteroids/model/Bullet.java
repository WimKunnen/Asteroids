package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

@SuppressWarnings("all")

/**
 A class of bullets for the Asteroid project.
 *
 * @invar A bullet located in a world will always fit in the boundaries of that world.
 *        | if (getWorld() != null)
 *        |     this.fitsInBoundaries(getWorld())
 * @invar A bullet located in a world never overlaps with other entities in that world.
 *        | for (Entity entity : this.getWorld().getAllEntities()){
 *        |     !(this.overlap(entity))
 *
 * @invar   The velocity of a bullet is always smaller than or equal to the speed of light.
 * 		    | velocity.vectorLength() <= speedOfLight
 *
 * @invar   The radius will always be greater or equal to th minimum radius.
 *          | isValidRadius()
 *
 * @invar   The maximum velocity of the ship shall always be smaller or equal to the speed of light.
 *          | getMaximumVelocity() <= speedOfLight
 *
 * @author WimKunnen and Maarten Doclo
 *
 * @version 2.0
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
     * @post    The new x coordinate is equal to x.
     *          | new.getPosition().getX() == x
     *
     * @post    The new y coordinate is equal to y.
     *          | new.getPosition().getY() == y
     *
     * @post    The new velocity along the x axis is equal to velocityX.
     *          | new.getVelocity().getX() == velocityX
     *
     * @post    The new velocity along the y axis is equal to velocityY.
     *          | new.getVelocity().getY() == velocityY
     *
     * @post    The new radius is equal to radius.
     *          | new.getRadius() == radius
     *
     * @throws  IllegalArgumentException
     *          The given radius is not a valid radius for any entity.
     *          | (!isValidRadius(radius))
     *
     * @throws   IllegalArgumentException
     *           Throws an exception if either x or y is equal to NaN.
     *           | (Double.isNaN(x) ||  Double.isNaN(y))
     *
     * @throws   IllegalArgumentException
     *           Throws an exception if either x + the radius or y+ the radius is out of the entities world.
     *           | x + this.getRadius() > this.getWorld().getWidth() || y + this.getRadius() > this.getWorld().getHeight())
     */
    public Bullet(double x, double y, double velocityX, double velocityY, double radius)
            throws IllegalArgumentException{
        super(x, y, velocityX, velocityY, radius);
        this.setDensity(7.8E12);
    }

    /**
     * Default initializer which uses the initializer defined in the Entity super class.
     *
     * @post    The new x coordinate is equal to 0.
     *          | new.getPosition.getX() == 0
     *
     * @post    The new y coordinate is equal to 0.
     *          | new.getPosition.getY() == 0
     *
     * @post    The new velocity along the x axis is equal to 0.
     *          | new.getVelocity.getX() == 0
     *
     * @post    The new velocity along the y axis is equal to 0.
     *          | new.getVelocity.getY() == 0
     *
     * @post    The new radius is equal to the minimum radius.
     *          | new.getRadius() == this.minimumRadius
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
     * @post
     * | new.getMaxNbBounces() == newMaxNb
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
     * @post
     *      | new.getSource() == sourceShip
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

    /**
     * Terminates the bullet.
     * If the bullet is located in a world, it is removed from that world.
     * If the bullet has a source ship, the source no longer has this bullet as a bullet
     *
     * @post    The entity is no longer part of its (non null) world.
     *          | !getWorld().getEntities().contains(this)
     *
     * @post    The source ship no longer contains the bullet
     *          | !getSource().getBullets.contains(this)
     *
     * @see implementation
     */
    @Override
    public void terminate(){
        isTerminated = true;
        if (getWorld() != null)
            getWorld().removeEntity(this);
        if (getSource() != null){
            getSource().removeBullet(this);
        }
    }

    /**
     * Returns true if and only if the bullet is located in the ship.
     * @see impementation
     */
    public boolean liesWithinShip(Ship ship){
        double distanceBetweenCentres = getDistanceBetween(ship) + getRadius() + ship.getRadius();
        return getRadius() < ship.getRadius() - distanceBetweenCentres;
    }
}
