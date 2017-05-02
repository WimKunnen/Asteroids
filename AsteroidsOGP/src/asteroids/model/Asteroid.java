package asteroids.model;

import java.util.Iterator;

/**
 * Created by WimKunnen on 18/04/2017.
 *
 * A class of asteroids.
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
