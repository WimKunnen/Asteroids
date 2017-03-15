package asteroids.model;

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
     *          The initial radius of the new bullet.
     *
     * @param   heading
     *          The initial heading of the new bullet.
     */
    public Bullet(double x, double y, double velocityX, double velocityY, double radius, double heading, World world){
        super(x, y, velocityX, velocityY, radius,1, heading, world);
        this.setDensity(7.8 * Math.pow(10, 12));
    }

    /**
     * Default initializer which uses the initializer defined in the Entity super class.
     */
    public Bullet(){
        super();
        this.setDensity(7.8 * Math.pow(10, 12));
    }

}
