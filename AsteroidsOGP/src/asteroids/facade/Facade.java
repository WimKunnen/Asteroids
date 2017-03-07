package asteroids.facade;

import asteroids.model.Ship;
import asteroids.model.Vector;
import asteroids.part1.facade.IFacade;
import asteroids.util.ModelException;

/**
 * @author WimKunnen
 */


public class Facade implements IFacade {

    /**
     * Default initializer for the Ship class.
     */
    public Facade() {
    }

    /**
     * Returns a new Ship at the origin point (0,0) with a velocity of 0, a heading of 0 and a radius equal to the minimum radius.
     */
    public Ship createShip() throws ModelException {
        return new Ship();
    }

    /**
     * Returns a ne Ship at (x,y) with a velocity vector of (xVelocity, yVelocity), a heading of orientation and a radius equal to radius.
     *
     * @param x           The position of the newly created Ship along the X-axis.
     * @param y           The position of the newly created Ship along the Y-axis.
     * @param xVelocity   The velocity of the newly created Ship along the X-axis.
     * @param yVelocity   The velocity of the newly created Ship along the Y-axis.
     * @param radius      The radius of the newly created Ship.
     * @param orientation The heading of the newly created Ship.
     */
    public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius, double orientation) throws ModelException {
       try{
           return new Ship(x, y, xVelocity, yVelocity, radius, orientation);
       }catch(IllegalArgumentException e) {
           throw new ModelException(e);
       }
    }

    /**
     * Returns an array of doubles of the ship's current position.
     */
    public double[] getShipPosition(Ship ship) throws ModelException {
        return new double[]{ship.getPosition().getX(), ship.getPosition().getY()};
    }

    /**
     * Returns an array of doubles of the ship's current velocity.
     */
    public double[] getShipVelocity(Ship ship) throws ModelException {
        return new double[]{ship.getVelocity().getX(), ship.getVelocity().getY()};
    }

    /**
     * Returns the ship's radius.
     */
    public double getShipRadius(Ship ship) throws ModelException {
        return ship.getRadius();
    }

    /**
     * Returns the ship's current heading.
     */
    public double getShipOrientation(Ship ship) throws ModelException {
        return ship.getHeading();
    }

    /**
     * Changes the ships position by the current velocity * dt.
     *
     * @param ship The ship that will be moved.
     * @param dt   The difference in time between the new calculation and the previous.
     * @throws ModelException The time difference is invalid.
     *                        | dt < 0
     */
    public void move(Ship ship, double dt) throws ModelException {
        try {
            ship.move(dt);
        } catch (IllegalArgumentException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Changes the velocity of the ship by the given amount.
     *
     * @param ship   The ship who's speed will be changed.
     * @param amount The given factor by which the speed will be increased.
     */
    public void thrust(Ship ship, double amount) throws ModelException {ship.thrust(amount);}

    /**
     * Changes the heading of the ship by the given angle.
     *
     * @param ship  The ship of which the heading will be changed.
     * @param angle The angle by which the ship's heading will be changed.
     */
    public void turn(Ship ship, double angle) throws ModelException {ship.turn(angle);}

    /**
     * Returns the distance between the two given ships.
     *
     * @throws ModelException The second ship is not a valid ship
     *                        | ship2 == null
     */
    public double getDistanceBetween(Ship ship1, Ship ship2) throws ModelException {
        try {
            return ship1.getDistanceBetween(ship2);
        } catch (IllegalArgumentException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Returns true if and only if the two ships overlap.
     *
     * @throws ModelException The ships do not overlap.
     *                        | ship1.getDistanceBetween(ship2) >= ship1.getRadius() + ship2.getRadius()
     */
    public boolean overlap(Ship ship1, Ship ship2) throws ModelException {
        try {
            return ship1.overlap(ship2);
        } catch (NullPointerException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Returns the time it will take before two ships collide.
     *
     * @throws ModelException The ships will not collide.
     *                        | ship1.willCollide(ship2) == false
     */
    public double getTimeToCollision(Ship ship1, Ship ship2) throws ModelException {
        try {
            return ship1.getTimeToCollision(ship2);

        } catch (NullPointerException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Returns the position of the collision point as an array of doubles.
     *
     * @throws ModelException The ships will not collide.
     *                        | ship1.willCollide(ship2) == false
     */
    public double[] getCollisionPosition(Ship ship1, Ship ship2) throws ModelException {
        try {
            Vector collisionPosition = ship1.getCollisionPosition(ship2);
            return collisionPosition == null ? null : new double[]{collisionPosition.getX(), collisionPosition.getY()};
        } catch (IllegalArgumentException e) {
            throw new ModelException(e);
        }
    }
}
