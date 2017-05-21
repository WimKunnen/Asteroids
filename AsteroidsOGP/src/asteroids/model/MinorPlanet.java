package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * A class of minor planets for the Asteroid project.
 *
 * @invar A minor planet located in a world will always fit in the boundaries of that world.
 *        | if (getWorld() != null)
 *        |     this.fitsInBoundaries(getWorld())
 * @invar A minor planet located in a world never overlaps with other entities in that world.
 *        | for (Entity entity : this.getWorld().getAllEntities()){
 *        |     !(this.overlap(entity))
 *
 * @invar   The velocity of a minor planet is always smaller than or equal to the speed of light.
 * 		    | velocity.vectorLength() <= speedOfLight
 *
 * @invar   The radius will always be greater or equal to th minimum radius.
 *          | isValidRadius()
 *
 * @invar   The maximum velocity of the minor planet shall always be smaller or equal to the speed of light.
 *          | getMaximumVelocity() <= speedOfLight
 *
 * @author WimKunnen and Maarten Doclo
 *
 * @version 1.0
 */
public abstract class MinorPlanet extends Entity {
    /**
     * Initializes a new minor planet by using the initializer defined in the Entity super class.
     * @param   x
     *          The initial position of the new minor planet along the x-axis.
     *
     * @param   y
     *          The initial position of the new minor planet along the y-axis.
     *
     * @param   velocityX
     *          The initial velocity of the new minor planet along the x-axis.
     *
     * @param   velocityY
     *          The initial velocity of the new minor planet along the y-axis.
     *
     * @param   radius
     *          The initial radius of the new minor planet.
     *
     * @invar   The radius of a minor planet shall never be less than 5km.
     *          | this.getRadius() >= 5
     */
    public MinorPlanet(double x, double y, double velocityX, double velocityY, double radius){
        super(x, y, velocityX, velocityY, radius);
    }

    /**
     * Default initializer which uses the initializer defined in the Entity super class.
     *
     * @effect
     *        | this(0,0,0,0,this.minimumRadius)
     */
    public MinorPlanet(){
        super();
        this.setRadius(this.getMinimumRadius());
    }

    /**
     * Variable registering the minimum radius of minor planets.
     */
    private final static double minimumRadius = 5;

    /**
     * Return the minimum radius of all minor planets.
     * @see implementation
     */
    @Basic
    @Override
    public double getMinimumRadius(){
        return minimumRadius;
    }

}
