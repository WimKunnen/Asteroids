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

public class Ship {

    /**
     * Initializes a new Ship.
     *
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
     *          The given radius is not a valid radius for any ship.
     *          | (!isValidRadius(radius))
     *
     * @throws   IllegalArgumentException
     *           Throws an exception if either x or y is equal to NaN.
     *           | (Double.isNaN(x) ||  Double.isNaN(y))
     */
    public Ship(double x, double y, double velocityX, double velocityY, double radius, double heading)
            throws IllegalArgumentException{
                if(Double.isNaN(x) ||  Double.isNaN(y))
                    throw new IllegalArgumentException();
                else
                    setPosition(new Vector(x, y));

                if(isValidRadius(radius)){
                    this.radius = radius;
                }else{
                    throw new IllegalArgumentException();
                }
                this.setMaximumVelocity(speedOfLight);
                this.setVelocity(new Vector(velocityX,velocityY));
                this.setHeading(heading);
    }

    /**
     * Default initializer for the Ship class.
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
    public Ship(){

        this.setPosition(new Vector());
        this.radius = minimumRadius;
        this.setMaximumVelocity(this.speedOfLight);
        this.setVelocity(new Vector());
        this.setHeading(0);

    }

    //Position:
    private Vector position;

    /**
     * Changes the current position vector to a new position vector.
     *
     * @param   newPosition
     *          The new position vector.
     *
     * @post    The position is set to the new position.
     *          |new.getPosition == newPosition
     */

    @Model
    private void setPosition(Vector newPosition){
        this.position = newPosition;
    }

    /**
     * Returns the position vector of the ship.
     */
    @Basic
    public Vector getPosition(){
        return position;
    }

    //Move
    /**
     * Changes the position of the ship by the velocity * time difference.
     *
     * @param   timeDifference
     *          The difference in time between two frames.
     *
     * @throws  IllegalArgumentException
     *          The given time difference is smaller than zero.
     *          | !isValidTimeDifference
     */
    public void move(double timeDifference) throws IllegalArgumentException{
        if(isValidTimeDifference(timeDifference)){
            setPosition(this.getPosition().sum(getVelocity().resizeVector(timeDifference)));
        }else{
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return True if and only if the given time difference is nonnegative.
     *         | result == timeDifference >= 0
     *
     * @param   timeDifference
     *          The difference in time between two moments used in the thrust() method.
     */
    private boolean isValidTimeDifference(double timeDifference){
        return timeDifference >= 0;
    }

    //Velocity:
    /**
     * Constant registering the speed of light which equals 300 000 km/s.
     */
    private final double speedOfLight = 300000;

    private final double speedOfLightSquared = speedOfLight*speedOfLight;

    /**
     * Variable registering the maximum velocity of this ship.
     */
    private double maximumVelocity;

    private double maximumVelocitySquared;
    
    /**
     * Returns the maximum velocity of this ship
     * 
     */
    @Basic @Immutable
    public double getMaximumVelocity(){
    	return this.maximumVelocity;
    }

    /**
     * Returns the square of the maximum velocity of this ship
     *
     */
    @Basic @Immutable
    private double getMaximumVelocitySquared(){
        return this.maximumVelocitySquared;
    }

    /**
     * Sets the maximum velocity at the given velocity.
     *
     * @param   velocity
     *          The new maximum velocity
     *
     * @post    If the given velocity is smaller or equal to speedOfLight and nonegative, the new maximum velocity is set at the given velocity.
     *          |if(velocity <= speedOfLight && 0 <= velocity) then
     *          |   this.maximumVelocity = velocity
     *          |   this.maximumVelocitySquared = velocity * velocity
     *          If the given velocity is greater than speedOfLight, the new maximum velocity is set at the speedOfLight.
     *          |if(velocity > speedOfLight) then
     *          |   this.maximumVelocity = speedOfLight
     *          |   this.maximumVelocitySquared = speedOfLightSquared
     *          Else, the new maximum velocity is set at 0.
     *          |Else
     *          |   this.maximumVelocity = 0
     *          |   this.maximumVelocitySquared = 0
     *          Thus the square of the maximum velocity changes accordingly.
     */

    public void setMaximumVelocity(double velocity){
        if(velocity <= speedOfLight && 0 <= velocity) {
            this.maximumVelocity = velocity;
            this.maximumVelocitySquared = velocity * velocity;
        }else if(velocity > speedOfLight){
            this.maximumVelocity = speedOfLight;
            this.maximumVelocitySquared = speedOfLightSquared;
        }else{
            this.maximumVelocity = 0;
            this.maximumVelocitySquared = 0;
        }
    }

    /**
     * Returns the velocity vector.
     */
    private Vector velocity;

    /**
     * The new velocity is set at the given velocity.
     *
     * @param   velocity
     *          The new velocity vector.
     *
     * @post    If the square of the new total velocity does not exceed the square of the maximum velocity,
     *          the new velocity is equal the the given velocity.
     *          | new.getVelocityX() == velocity
     *          If the square of the new total velocity does exceed the square of the maximum velocity,
     *          the new total velocity is set at the maximum velocity, but the new direction of the velocity remains unaltered.
     *
     */

    @Model
    private void setVelocity(Vector velocity){
        this.velocity = velocity.vectorLengthSquared() > this.getMaximumVelocitySquared()
                ? velocity.normalize().resizeVector(maximumVelocity) :  velocity;
    }

    /**
     * Returns the velocity vector.
     */
    @Basic
    public Vector getVelocity(){
        return this.velocity;
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

    // Heading

    /**
     * Variable registering the orientation of this ship.
     */
    private double heading;

    /**
     * Returns the heading of the ship.
     */
    @Basic
    public double getHeading(){
        return this.heading;
    }

    /**
     * Set the heading at the given angle.
     *
     * @param   angle
     *          The angle at which the new heading will be set.
     *
     * @pre     The angle must be a valid angle.
     *          | isValidAngle(angle)
     */

    @Model
    private void setHeading(double angle) {
        assert isValidAngle(angle);
        this.heading = angle;
    }

    /**
     * Checks if the angle is valid.
     *
     * @param   angle
     *          The angle between the ship's direction and the x-axis.
     *
     * @return  True if and only if the angle is between 0 and 2 * π.
     *          | result == ((this.getHeading() + angle < 2 * Math.PI) && (0 <= this.getHeading() + angle))
     */
    public boolean isValidAngle(double angle){
        return ((angle < 2 * Math.PI) && (0 <= angle));
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

    //Radius
    /**
     * Variable registering the minimum radius of all ships.
     */
    private static double minimumRadius = 10;

    /**
     * Variable registering the radius of this ship.
     */
    private final double radius;

    /**
     * Returns the radius of the ship.
     */
    @Basic
    @Immutable
    public double getRadius(){
        return this.radius;
    }

    /**
     * Returns true if and only if the given radius is larger than the minimum radius and not NaN.
     *
     * @param   radius
     *          The radius which validity will be checked.
     */
    public boolean isValidRadius(double radius){
        return (radius >= minimumRadius && ! Double.isNaN(radius));
    }

    // Collision detection
    /**
     * Returns the distance between two ships.
     * | return sqrt(xDifference * xDifference + yDifference * yDifference) - radiusDifference
     * The distance between a ship and itself is zero.
     * @param   other
     *          The second ship.
     *
     * @throws IllegalArgumentException
     *          The other ship does not exist.
     *          | other == null
     */

    public double getDistanceBetween(Ship other) throws IllegalArgumentException{
        if(other != null){
            if (this == other) {
                return 0;
            }
            else {
                double xDifference = (this.getPosition().getX() - other.getPosition().getX());
                double yDifference = (this.getPosition().getY() - other.getPosition().getY());
                double radiusSum = this.getRadius() + other.getRadius();
                return Math.sqrt(xDifference * xDifference + yDifference * yDifference) - radiusSum;
            }
        }else{
            throw new IllegalArgumentException("Not an existing ship!");
        }
    }

    /**
     * Returns true if and only if the distance between the two ships is nonnegative or the two ships are the same.
     * | return getDistanceBetween < 0 || this == other
     *
     * @param   other
     *          The second ship.
     *
     *  @throws IllegalArgumentException
     *          The other ship does not exist.
     *          | other == null
     */
    public boolean overlap(Ship other) throws IllegalArgumentException{
        if (other != null) {
            return this.getDistanceBetween(other) < 0 || this == other;
        }else{
            throw new IllegalArgumentException("Not an existing ship!");
        }
    }

    /**
     * Returns true if and only if the ships will collide at some point in time.
     *
     * @param   other
     *          The other ship
     *
     * @throws  IllegalArgumentException
     *          The other ship does not exist.
     *          | other == null
     */
    public boolean willCollide(Ship other) throws NullPointerException{

            Vector deltaV = this.deltaV(other);
            Vector deltaR = this.deltaR(other);
            double sigma = this.sigma(other);
            double d = d(deltaV, deltaR, sigma);

            return  !(deltaV.scalarProduct(deltaR) >= 0 || d <= 0 || this.overlap(other));
    }



    /**
     * If two ships will collide, the time it takes them to collide is returned.
     * | if (willCollide) then
     * |    return timeToCollision
     *
     * @param other
     *        | The other ship
     *
     * @throws NullPointerException
     *        The other ship does not exist
     *        | other == null
     */

    public double getTimeToCollision(Ship other) throws NullPointerException{
            Vector deltaV = this.deltaV(other);
            Vector deltaR = this.deltaR(other);
            double sigma = this.sigma(other);
            double d = this.d(deltaV, deltaR, sigma);
            double timeToCollision = -(deltaV.scalarProduct(deltaR) + Math.sqrt(d)) / deltaV.scalarProduct(deltaV);

            if (this.willCollide(other)) {
                return timeToCollision;
            } else {
                return Double.POSITIVE_INFINITY;
            }
    }

    /**
     * If the ships will collide, the position of the hull where the ships hit is returned.
     * | if (willCollide()) then
     * |    return hullPosition
     * Else, null is returned.
     * |else
     * |    return null
     *
     * @param    other
     *          |the other ship
     *
     * @throws  IllegalArgumentException
     *          The other ship does not exist
     *          | other == null
     */

    public Vector getCollisionPosition(Ship other) throws NullPointerException {

            if (!this.willCollide(other)) {
                return null;
            } else {
                double deltaT = getTimeToCollision(other);

                double newThisCoordX = this.getPosition().getX() + deltaT * this.getVelocity().getX();
                double newThisCoordY = this.getPosition().getY() + deltaT * this.getVelocity().getY();
                Vector newPositionThis = new Vector(newThisCoordX, newThisCoordY);

                double newOtherCoordX = other.getPosition().getX() + deltaT * other.getVelocity().getX();
                double newOtherCoordY = other.getPosition().getY() + deltaT * other.getVelocity().getY();
                Vector newPositionOther = new Vector(newOtherCoordX, newOtherCoordY);

                Vector pointingVector = newPositionOther.sum(newPositionThis.resizeVector(-1)).normalize();
                Vector hullPosition = newPositionThis.sum(pointingVector.resizeVector(this.getRadius()));

                return hullPosition;
            }
    }

    /**
     * Returns the vectorial difference of the velocity vectors of two ships.
     */
    private Vector deltaV(Ship other){
        return other.getVelocity().sum(this.getVelocity().resizeVector(-1));
    }

    /**
     * Returns the vectorial difference of the centers of the two ships.
     */
    private Vector deltaR(Ship other){
        return other.getPosition().sum(this.getPosition().resizeVector(-1));
    }

    /**
     * Returns the sum of the radii of the ships.
     */
    private double sigma(Ship other){
        return this.getRadius() + other.getRadius();
    }

    /**
     * Returns the constant d, which was defined in the assignment.
     */
    private double d(Vector deltaV, Vector deltaR, double sigma){
        return deltaV.scalarProduct(deltaR) * deltaV.scalarProduct(deltaR)
                - deltaV.scalarProduct(deltaV) * (deltaR.scalarProduct(deltaR) - sigma * sigma);
    }
}