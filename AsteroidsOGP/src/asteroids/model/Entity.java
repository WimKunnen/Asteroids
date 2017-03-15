package asteroids.model;

import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;

/**
 * A class of entities for the game Asteroids.
 * Possible entities are currently ships and bullets.
 */
public abstract class Entity {

    /**
     * Initializes a new Entity.
     *
     * @param   x
     *          The initial position of the new entity along the x-axis.
     *
     * @param   y
     *          The initial position of the new entity along the y-axis.
     *
     * @param   velocityX
     *          The initial velocity of the new entity along the x-axis.
     *
     * @param   velocityY
     *          The initial velocity of the new entity along the y-axis.
     *
     * @param   radius
     *          The initial radius of the new entity.
     *
     * @param   heading
     *          The initial heading of the new entity.
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
     */
    public Entity(double x, double y, double velocityX, double velocityY, double radius, double heading)
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
     * Default initializer for the Entity class.
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
    public Entity(){

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
     * Returns the position vector of the entity.
     */
    @Basic
    public Vector getPosition(){
        return position;
    }

    //Move
    /**
     * Changes the position of the entity by the velocity * time difference.
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
     * Variable registering the maximum velocity of this entity.
     */
    private double maximumVelocity;

    private double maximumVelocitySquared;

    /**
     * Returns the maximum velocity of this entity
     *
     */
    @Basic @Immutable
    public double getMaximumVelocity(){
        return this.maximumVelocity;
    }

    /**
     * Returns the square of the maximum velocity of this entity
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
    protected Vector velocity;

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
    protected void setVelocity(Vector velocity){
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
    // Heading

    /**
     * Variable registering the orientation of this entity.
     */
    private double heading;

    /**
     * Returns the heading of the entity.
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
    protected void setHeading(double angle) {
        assert isValidAngle(angle);
        this.heading = angle;
    }

    /**
     * Checks if the angle is valid.
     *
     * @param   angle
     *          The angle between the entity's direction and the x-axis.
     *
     * @return  True if and only if the angle is between 0 and 2 * π.
     *          | result == ((this.getHeading() + angle < 2 * Math.PI) && (0 <= this.getHeading() + angle))
     */
    public boolean isValidAngle(double angle){
        return ((angle < 2 * Math.PI) && (0 <= angle));
    }

    //Radius
    /**
     * Variable registering the minimum radius of all entities.
     */
    private static double minimumRadius = 10;


    /**
     * Variable registering the radius of this entity.
     */
    private final double radius;

    /**
     * Returns the radius of the entity.
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
     * Returns the distance between two entities.
     * | return sqrt(xDifference * xDifference + yDifference * yDifference) - radiusDifference
     * The distance between a entity and itself is zero.
     * @param   other
     *          The second entity.
     *
     * @throws IllegalArgumentException
     *          The other entity does not exist.
     *          | other == null
     */

    public double getDistanceBetween(Entity other) throws IllegalArgumentException{
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
            throw new IllegalArgumentException("Not an existing entity!");
        }
    }

    /**
     * Returns true if and only if the distance between the two entities is nonnegative or the two entities are the same.
     * | return getDistanceBetween < 0 || this == other
     *
     * @param   other
     *          The second entity.
     *
     *  @throws IllegalArgumentException
     *          The other entity does not exist.
     *          | other == null
     */
    public boolean overlap(Entity other) throws IllegalArgumentException{
        if (other != null) {
            return this.getDistanceBetween(other) < 0 || this == other;
        }else{
            throw new IllegalArgumentException("Not an existing entity!");
        }
    }

    /**
     * Returns true if and only if the entities will collide at some point in time.
     *
     * @param   other
     *          The other entity
     *
     * @throws  IllegalArgumentException
     *          The other entity does not exist.
     *          | other == null
     */
    public boolean willCollide(Entity other) throws NullPointerException{

        Vector deltaV = this.deltaV(other);
        Vector deltaR = this.deltaR(other);
        double sigma = this.sigma(other);
        double d = d(deltaV, deltaR, sigma);

        return  !(deltaV.scalarProduct(deltaR) >= 0 || d <= 0 || this.overlap(other));
    }



    /**
     * If two entities will collide, the time it takes them to collide is returned.
     * | if (willCollide) then
     * |    return timeToCollision
     *
     * @param other
     *        | The other entity
     *
     * @throws NullPointerException
     *        The other entity does not exist
     *        | other == null
     */

    public double getTimeToCollision(Entity other) throws NullPointerException{
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
     * If the entities will collide, the position of the hull where the entities hit is returned.
     * | if (willCollide()) then
     * |    return hullPosition
     * Else, null is returned.
     * |else
     * |    return null
     *
     * @param    other
     *          |the other entity
     *
     * @throws  IllegalArgumentException
     *          The other entity does not exist
     *          | other == null
     */

    public Vector getCollisionPosition(Entity other) throws NullPointerException {

        if (! this.willCollide(other)) {
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
     * Returns the vectorial difference of the velocity vectors of two entities.
     */
    private Vector deltaV(Entity other){
        return other.getVelocity().sum(this.getVelocity().resizeVector(-1));
    }

    /**
     * Returns the vectorial difference of the centers of the two entities.
     */
    private Vector deltaR(Entity other){
        return other.getPosition().sum(this.getPosition().resizeVector(-1));
    }

    /**
     * Returns the sum of the radii of the entities.
     */
    private double sigma(Entity other){
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
