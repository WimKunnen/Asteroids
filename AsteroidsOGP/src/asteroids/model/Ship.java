package asteroids.model;
import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;


/**
 * A class of spaceships for the game Asteroids.
 * The class involves a position, velocity, orientation, maximum velocity and a radius.
 * 
 * @invar   The heading of a ship will always be a number between zero and 2 * PI.
 * 		    | isValidAngle()
 *
 * @invar   The velocity of a ship is always smaller than or equal to the speed of light.
 * 		    | velocity.vectorLength <= speedOfLight
 *
 * @invar   The radius will always be greater or equal to th minimum radius.
 *          | isValidRadius()
 *
 * @invar   The maximum velocity of the ship shall always be smaller or equal to the speed of light.
 *          | getMaximumVelocity() <= speedOfLight
 *
 *
 *
 * @author  Wim Kunnen and Maarten Doclo
 *
 * @version 1.0
 *
 * About the authors and the software:
 *  Wim Kunnen:     Studies: Ingenieurswetenschappen: Elektrotechniek - Computer Wetenschappen.
 *  Maarten Doclo:  Studies: Ingenieurswetenschappen: Computer Wetenschappen - Elektrotechniek.
 *
 *  This Java class was created for the Asteroids Part 1 assignment for the course Objectoriented Programming
 *  given by Prof. dr. ir. E. Steegmans.
 *
 *  The code for this assignment can also be found at our public Github Repository:
 *  https://github.com/WimKunnen/AsteroidsPart1
 */

public class Ship extends Entity{

    /**
     * Initializes a new Ship by using the initializer defined in the Entity super class.
     * @param   x
     *          The initial position of the new ship along the x-axis.
     *
     * @param   y
     *          The initial position of the new ship along the y-axis.
     *
     * @param   velocityX
     *          The initial velocity of the new ship along the x-axis.
     *
     * @param   velocityY
     *          The initial velocity of the new ship along the y-axis.
     *
     * @param   radius
     *          The initial radius of the new ship.
     *
     * @param   heading
     *          The initial heading of the new ship.
     */
    public Ship(double x, double y, double velocityX, double velocityY, double radius, double heading){
        super(x, y, velocityX, velocityY, radius, heading);
    }

    /**
     * Default initializer which uses the initializer defined in the Entity super class.
     */
    public Ship(){
        super();
    }

    /**
     * The current velocity is increased by the added velocity.
     *
     * @param   addedVelocitySize
     *          The size of the velocity vector by which the current velocity is increased.
     *
     * @post    The velocity has increased by the given velocity
     *          | new.velocity == this.velocity.sum(new Vector(addedVelocitySize * cos(this.getHeading()),
     *          |                    addedVelocitySize * sin(this.getHeading()))
     *          If the given velocity is smaller than zero, the velocity is unchanged.
     */
    public void thrust(double addedVelocitySize){
        if (addedVelocitySize < 0) {
            addedVelocitySize = 0;
        }
        Vector addedVelocity = new Vector(addedVelocitySize * Math.cos(this.getHeading()),
                addedVelocitySize * Math.sin(this.getHeading()));
        Vector newVelocity = this.velocity.sum(addedVelocity);
        this.setVelocity(newVelocity);

        }



    /**
     * Increases the heading of the ship by a given angle.
     *
     * @param   angle
     *          The angle by which the heading is increased.
     *
     * @post    The heading is increased by the given angle modulo 2 * π.
     *          | new.getHeading() == (this.getHeading + angle) % (2 * Math.PI)
     */
    public void turn(double angle) {
        double newAngle = Math.abs((this.getHeading() + angle) % (2 * Math.PI));
        assert isValidAngle(newAngle);
        this.setHeading(newAngle);
    }

}