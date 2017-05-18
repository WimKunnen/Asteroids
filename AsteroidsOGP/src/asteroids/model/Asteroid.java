package asteroids.model;


/**
 * A class of asteroids for the Asteroid project.
 *
 * @invar An asteroid located in a world will always fit in the boundaries of that world.
 *        | if (getWorld() != null)
 *        |     this.fitsInBoundaries(getWorld())
 * @invar An asteroid located in a world never overlaps with other entities in that world.
 *        | for (Entity entity : this.getWorld().getAllEntities()){
 *        |     !(this.overlap(entity))
 *
 * @invar   The velocity of an asteroid is always smaller than or equal to the speed of light.
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
 * @version 1.0
 */
public class Asteroid extends MinorPlanet {

    /**
     * Initializes a new asteroid by using the initializer defined in the Entity super class.
     * @param   x
     *          The initial position of the new asteroid along the x-axis.
     *
     * @param   y
     *          The initial position of the new asteroid along the y-axis.
     *
     * @param   velocityX
     *          The initial velocity of the new asteroid along the x-axis.
     *
     * @param   velocityY
     *          The initial velocity of the new asteroid along the y-axis.
     *
     * @param   radius
     *          The initial radius of the new asteroid.
     */
    public Asteroid(double x, double y, double velocityX, double velocityY, double radius)
            throws IllegalArgumentException{
        super(x, y, velocityX, velocityY, radius);
        this.setDensity(2.65E12);
    }

    /**
     * Default initializer which uses the initializer defined in the MinorPlanet super class.
     */
    public Asteroid(){
        super();
        this.setDensity(2.65E12);
        this.setRadius(this.getMinimumRadius());
    }

}
