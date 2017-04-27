package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * Created by WimKunnen on 18/04/2017.
 *
r * A class of minor planets.
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
     */
    public MinorPlanet(){
        super();
        this.setRadius(this.getMinimumRadius());
    }

    /**
     * Variable registering the minimum radius of bullets.
     */
    private final static double minimumRadius = 5;

    /**
     * Return the minimum radius of all bullets.
     * @see implementation
     */
    @Basic
    @Override
    public double getMinimumRadius(){
        return minimumRadius;
    }

}
