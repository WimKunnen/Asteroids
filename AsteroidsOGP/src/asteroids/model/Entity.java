package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

import java.util.ArrayList;
import java.util.List;


/**
 * A class of entities for the game Asteroids.
 * Possible entities are currently ships and bullets.
 *
 * @invar An entity located in a world will always fit in the boundaries of that world.
 *        | if (getWorld() != null)
 *        |     this.fitsInBoundaries(getWorld())
 * @invar An entity located in a world never overlaps with other entities in that world.
 *        | for (Entity entity : this.getWorld().getAllEntities()){
 *        |     if (this.overlap(entity)) {
 *        |         entity instanceof Ship && this instanceof Bullet && ((Ship) entity).getBullets().contains(this)
 *        |         || entity instanceof Bullet && this instanceof Ship && ((Ship) this).getBullets().contains(entity)
 *        |         || this == entity;
 *        |     }
 *        | }
 *
 * @author WimKunnen and Maarten Doclo
 *
 * @version 2.0
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
     *          The radius of this entity.
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
    public Entity(double x, double y, double velocityX, double velocityY, double radius)
            throws IllegalArgumentException{
        this.setRadius(radius);
        this.setWorld(world);
        Vector position = new Vector(x, y);
        Vector velocity = new Vector(velocityX,velocityY);
        if(isInvalidPosition(position))
            throw new IllegalArgumentException();
        else
            this.setPosition(position);

        this.setMaximumVelocity(speedOfLight);

        if(Double.isNaN(velocityX) || Double.isNaN(velocityY)) {
            double angle = Math.random() * Math.PI * 2;
            Vector newVelocity = new Vector(this.getMaximumVelocity() * Math.cos(angle),
                    getMaximumVelocity() * Math.sin(angle));
            this.setVelocity(newVelocity);
        }
        else
            this.setVelocity(velocity);

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
        this.setRadius(this.getMinimumRadius());
        this.setPosition(new Vector());
        this.setMaximumVelocity(this.speedOfLight);
        this.setVelocity(new Vector());
    }

    //Position:
    /**
     * Variable registering the position of the entity as a vector.
     */
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
    protected void setPosition(Vector newPosition){
        this.position = newPosition;
    }

    /**
     * Method to check whether or not a position is valid.
     *
     * @see implementation
     */
    protected boolean isInvalidPosition(Vector vector){
        return (Double.isNaN(vector.getX()) || Double.isNaN(vector.getY()));
    }

    /**
     * Method to check whether or not a position is valid.
     *
     * @see implementation
     */
    protected boolean isInvalidVelocity(Vector vector){
        return (Double.isNaN(vector.getX()) || Double.isNaN(vector.getY()));
    }

    /**
     * Returns the position vector of the entity.
     */
    @Basic
    public Vector getPosition(){
        return position;
    }

    // The world of the entity.
    /**
     * Variable registering the current world of the entity, initialized at null.
     */
    protected World world = null;

    /**
     * Method to check what world the entity is part of.
     *
     * @see implementation
     */
    public World getWorld(){
        return this.world;
    }

    /**
     * Method to set the current world the entity is placed in.
     *
     * @see implementation
     */
    protected void setWorld(World newWorld) {
        this.world = newWorld;
    }

    //Move
    /**
     * Changes the position of the entity by the velocity * time difference.
     *
     * @param   timeDifference
     *          The difference in time between two frames.
     *
     * @post    The new position is equal to the sum of the old position and the velocity times the time difference.
     *          | new.getPosition == this.getPosition + this.getVelocity * timeDifference
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
    protected boolean isValidTimeDifference(double timeDifference){
        return timeDifference >= 0;
    }

    //Velocity:
    /**
     * Constant registering the speed of light which equals 300 000 km/s.
     */
    private final double speedOfLight = 300000;

    /**
     * Variable registering the maximum velocity of this entity.
     */
    private double maximumVelocity;

    /**
     * Returns the maximum velocity of this entity
     *
     */
    @Basic @Immutable
    public double getMaximumVelocity(){
        return this.maximumVelocity;
    }


    /**
     * Sets the maximum velocity at the given velocity.
     *
     * @param   velocity
     *          The new maximum velocity
     *
     * @post    If the given velocity is smaller or equal to speedOfLight and nonnegative, the new maximum velocity is set at the given velocity.
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
        }else if(velocity > speedOfLight){
            this.maximumVelocity = speedOfLight;
        }else{
            this.maximumVelocity = 0;
        }
    }

    /**
     * Variable registering the velocity of the entity as a vector.
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
     */
    @Model
    protected void setVelocity(Vector velocity){
        this.velocity = (velocity.vectorLengthSquared() > this.getMaximumVelocity()*this.getMaximumVelocity())
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
     * Method to negate the X component of the velocity.
     *
     * @see implementation
     */
    public void negateVelocityX() {
        setVelocity(new Vector(-this.getVelocity().getX(), this.getVelocity().getY()));
    }

    /**
     * Method to negate the Y component of the velocity.
     *
     * @see implementation
     */
    public void negateVelocityY() {
        setVelocity(new Vector(this.getVelocity().getX(), -this.getVelocity().getY()));
    }

    //radius:
    /**
     * Method to check the minimum radius of an entity.
     *
     * @see implementation
     */
    protected abstract double getMinimumRadius();

    /**
     * Variable registering the radius of this entity.
     */
    protected double radius;

    /**
     * Method to set the radius of an entity.
     *
     * @param newRadius
     *        The new radius of this entity.
     *
     * @see implementation
     */
    protected void setRadius(double newRadius){
        if (!isValidRadius(newRadius)) {
            throw new IllegalArgumentException("Not a valid radius!");
        }
        this.radius = newRadius;
    }

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
    public boolean isValidRadius(double radius) {
        if (this instanceof Ship){
            return (radius >= ((Ship) this).getMinimumRadius() && !Double.isNaN(radius));
        }
        else if (this instanceof Bullet){
            return (radius >= ((Bullet) this).getMinimumRadius() && !Double.isNaN(radius));
        }
        else if (this instanceof MinorPlanet){
            return (radius >= ((MinorPlanet) this).getMinimumRadius() && !Double.isNaN(radius));
        }

        throw new AssertionError();
    }

    // Collision detection
    /**
     * Returns the distance between two entities.
     * The distance between a entity and itself is zero.
     *
     * @return The distance between the outer edges of two entities
     *         | result ==
     *         |    if (this == other)
     *         |        then 0
     *         |    else sqrt(
     *         |        (this.getPosition().getX() - other.getPosition().getX())*(this.getPosition().getX() - other.getPosition().getX())
     *         |        + (this.getPosition().getY() - other.getPosition().getY())*(this.getPosition().getY() - other.getPosition().getY())
     *         |        )
     *         |        - (this.getRadius() + other.getRadius())
     *
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
                double sumRadii = this.getRadius() + other.getRadius();
                return Math.sqrt(xDifference * xDifference + yDifference * yDifference) - sumRadii;
            }
        }else{
            throw new IllegalArgumentException("Not an existing entity!");
        }
    }

    /**
     * Returns true if and only if the two ships significantly overlap or the two entities are the same.
     * @return  ...
     *          | return getDistanceBetween < -0.01 * sumRadii || this == other
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
            double sumRadii = this.getRadius() + other.getRadius();
            return this.getDistanceBetween(other) < -0.01 * sumRadii || this == other;
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

        return  !(deltaV.scalarProduct(deltaR) >= 0 || d <= 0 || this.overlap(other)) && this.getWorld() == other.getWorld();
    }

    /**
     * Returns true if and only if the entity appears to collide with the other entity.
     * An entity appears to collide with another entity if the distance between their centers is between
     * 99% and 101% of the sum of their radii.
     *
     * @see implementation
     *
     * @param   entity
     *          Another entity with which the entity appears to collide.
     */
    public boolean apparentlyCollidesWithEntity(Entity entity){
        double sumRadii = this.sigma(entity);
        return (0.99*sumRadii < this.getDistanceBetween(entity) + sumRadii &&
                this.getDistanceBetween(entity) + sumRadii < 1.01*sumRadii);
    }

    /**
     * Returns true if and only if the entity appears to collide with a boundary.
     * An entity appears to collide with a boundary if the distance between the center of the entity and
     * the boundary is between 99% and 101% of the radius of the ship.
     *
     * @see implementation
     */
    public boolean apparentlyCollidesWithBoundary(){
        return apparentlyCollidesWithLeft()
                || apparentlyCollidesWithRight()
                || apparentlyCollidesWithTop()
                || apparentlyCollidesWithBottom();
    }

    /**
     * Returns true if and only if the entity appears to collide with the left boundary.
     * An entity appears to collide with the left boundary if the distance between the center of the entity and
     * the left boundary is between 99% and 101% of the radius of the ship.
     *
     * @see implementation
     */
    public boolean apparentlyCollidesWithLeft(){
        double left = (Double)getDistancesToBoundaries().get(0);
        return (left < 1.01*getRadius() && left > 0.99*getRadius());
    }
    /**
     * Returns true if and only if the entity appears to collide with the right boundary.
     * An entity appears to collide with the right boundary if the distance between the center of the entity and
     * the right boundary is between 99% and 101% of the radius of the ship.
     *
     * @see implementation
     */
    public boolean apparentlyCollidesWithRight(){
        double right = (Double) getDistancesToBoundaries().get(1);
        return (right < 1.01*getRadius() && right > 0.99*getRadius());
    }
    /**
     * Returns true if and only if the entity appears to collide with the top boundary.
     * An entity appears to collide with the top boundary if the distance between the center of the entity and
     * the top boundary is between 99% and 101% of the radius of the ship.
     *
     * @see implementation
     */
    public boolean apparentlyCollidesWithTop(){
        double top = (Double) getDistancesToBoundaries().get(2);
        return (top < 1.01*getRadius() && top > 0.99*getRadius());
    }
    /**
     * Returns true if and only if the entity appears to collide with the bottom boundary.
     * An entity appears to collide with the bottom boundary if the distance between the center of the entity and
     * the bottom boundary is between 99% and 101% of the radius of the ship.
     *
     * @see implementation
     */
    public boolean apparentlyCollidesWithBottom(){
        double bottom = (Double) getDistancesToBoundaries().get(3);
        return (bottom < 1.01*getRadius() && bottom > 0.99*getRadius());
    }

    /**
     * The distance from the centre of the ship to the four boundaries: left, right, top, bottom.
     * These values are added to a list.
     *
     * @see implementation
     */
    private List getDistancesToBoundaries(){
        if (getWorld() == null){
            List<Double> distances = new ArrayList<>();
            distances.add(Double.POSITIVE_INFINITY);
            distances.add(Double.POSITIVE_INFINITY);
            distances.add(Double.POSITIVE_INFINITY);
            distances.add(Double.POSITIVE_INFINITY);
            return distances;
        }

        double top = getWorld().getHeight() - getPosition().getY();
        double bot = getPosition().getY();
        double left = getPosition().getX();
        double right = getWorld().getWidth() - getPosition().getX();
        List<Double> distances = new ArrayList<>();
        distances.add(left);
        distances.add(right);
        distances.add(top);
        distances.add(bot);
        return distances;
    }



    /**
     * If two entities will collide, the time it takes them to collide is returned.
     *
     * @param other
     *        | The other entity
     *
     * @throws NullPointerException
     *        The other entity does not exist
     *        | other == null
     *
     * @note  The time returned by this method is the time it takes for these entities to collide for the first time.
     *        When the entities move through each other, they collide again. This is not the time this method looks for.
     * @note  During the time returned by this method the two entities move closer to each other.
     *        When this time is past, the distance between the two entities is zero.
     * @note  The time returned by this method shall only be correct if the path of the two entities is not
     *        disturbed by collisions with other entities or by accelerating.
     */
    public double getTimeToCollision(Entity other) throws NullPointerException{
        Vector deltaV = this.deltaV(other);
        Vector deltaR = this.deltaR(other);
        double sigma = this.sigma(other);
        double d = this.d(deltaV, deltaR, sigma);
        double timeToCollision = -(deltaV.scalarProduct(deltaR) + Math.sqrt(d)) / deltaV.scalarProduct(deltaV);

        if (this.willCollide(other)) {
            if (timeToCollision < 0 ){
                timeToCollision = 0;
            }
            return timeToCollision;
        }
        if( this instanceof Bullet && other instanceof Ship && ((Bullet)this).getSource() == other && !((Bullet)this).hasBeenOutOfShip()){
            return Double.POSITIVE_INFINITY;
        }
        if( other instanceof Bullet && this instanceof Ship && ((Bullet)other).getSource() == this && !((Bullet)other).hasBeenOutOfShip()){
            return Double.POSITIVE_INFINITY;
        }
        else{
            return Double.POSITIVE_INFINITY;
        }
    }

    /**
     * Returns the time before the entity will collide with one of the boundaries.
     * If two collisions are possible with the current position and velocity, the time until the first collision is returned.
     *
     * @see implementation
     */
    public double getTimeToCollisionWithBoundary() {
        double time = Double.POSITIVE_INFINITY;
        if (this.world == null)
            return time;
        if (getVelocity().getX() > 0) {
            double timeToCollisionX = (this.world.getWidth() - getRadius() - getPosition().getX()) / getVelocity().getX();
            if (timeToCollisionX < time)
                time = timeToCollisionX;
        }
        else if (getVelocity().getX() < 0) {
            double timeToCollisionX = Math.abs((getPosition().getX() - getRadius()) / getVelocity().getX());
            if (timeToCollisionX < time)
                time = timeToCollisionX;
        }
        if (getVelocity().getY() > 0) {
            double timeToCollisionY = (world.getHeight() - getRadius() - getPosition().getY()) / getVelocity().getY();
            if (timeToCollisionY < time)
                time = timeToCollisionY;
        }
        else if (getVelocity().getY() < 0){
            double timeToCollisionY = Math.abs((getPosition().getY() - getRadius()) / getVelocity().getY());
            if (timeToCollisionY < time)
                time = timeToCollisionY;
        }
        if (time < 0 ) {
            time = 0;
        }
        return time;
    }

    /**
     * Return at which side of the world the first collision with a boundary would happen: Right ("R"), Left("L"),
     * Top("T"), Bottom("B") or no boundary("X").
     *
     * @see implementation
     */
    public String getBoundaryOfCollision(){
        double time = Double.POSITIVE_INFINITY;
        String boundary = "X";
        if (this.world == null)
            return boundary;
        if (getVelocity().getX() > 0) {
            double timeToCollisionX = (this.world.getWidth() - getRadius() - getPosition().getX()) / getVelocity().getX();
            if (timeToCollisionX < time) {
                time = timeToCollisionX;
                boundary = "R";
            }

        }
        else if (getVelocity().getX() < 0) {
            double timeToCollisionX = Math.abs((getPosition().getX() - getRadius()) / getVelocity().getX());
            if (timeToCollisionX < time) {
                time = timeToCollisionX;
                boundary = "L";
            }
        }
        if (getVelocity().getY() > 0) {
            double timeToCollisionY = (world.getHeight() - getRadius() - getPosition().getY()) / getVelocity().getY();
            if (timeToCollisionY < time) {
                boundary = "T";
            }
        }
        else if (getVelocity().getY() < 0){
            double timeToCollisionY = Math.abs((getPosition().getY() - getRadius()) / getVelocity().getY());
            if (timeToCollisionY < time) {
                boundary = "B";
            }
        }
        return boundary;
    }

    /**
     * Returns the position at which the first collision with one of the boundaries will happen.
     *
     * @see implementation
     */
    public Vector getCollisionPositionWithBoundary() {
        double time = getTimeToCollisionWithBoundary();
        String boundary = getBoundaryOfCollision();
        Vector radiusVector;
        if (boundary.equals("X")) {
            return null;
        }
        else if (boundary.equals("R")) {
            radiusVector = new Vector(getRadius(),0);
        }
        else if (boundary.equals("L")) {
            radiusVector = new Vector(-getRadius(),0);
        }
        else if (boundary.equals("T")) {
            radiusVector = new Vector(0, getRadius());
        }
        else {
            radiusVector = new Vector(0, -getRadius());
        }

        return this.getPosition().sum(getVelocity().resizeVector(time)).sum(radiusVector);
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
    public Vector deltaV(Entity other){
        return other.getVelocity().sum(this.getVelocity().resizeVector(-1));
    }

    /**
     * Returns the vectorial difference of the centers of the two entities.
     */
    public Vector deltaR(Entity other){
        return other.getPosition().sum(this.getPosition().resizeVector(-1));
    }

    /**
     * Returns the sum of the radii of the entities.
     */
    public double sigma(Entity other){
        return this.getRadius() + other.getRadius();
    }

    /**
     * Returns the constant d, which was defined in the assignment.
     */
    private double d(Vector deltaV, Vector deltaR, double sigma){
        return deltaV.scalarProduct(deltaR) * deltaV.scalarProduct(deltaR)
                - deltaV.scalarProduct(deltaV) * (deltaR.scalarProduct(deltaR) - sigma * sigma);
    }

    /**
     * Returns true if and only if the two entities fly away from each other.
     *
     * @param   other
     *          The other entity.
     *
     * @see implementation
     */
    public boolean fliesApartFrom(Entity other){
        Vector pointingVector = other.getPosition().sum(this.getPosition().negate());

        double thisVelocityLength = this.getVelocity().vectorLength();
        double otherVelocityLength = other.getVelocity().vectorLength();

        double angleBetweenThis = this.getVelocity().angleBetween(pointingVector);
        double angleBetweenOther = other.getVelocity().angleBetween(pointingVector);

        double thisProjectedLength = thisVelocityLength * Math.cos(angleBetweenThis);
        double otherProjectedLength = otherVelocityLength * Math.cos(angleBetweenOther);

        return (otherProjectedLength >= thisProjectedLength);
    }

    // Mass
    /**
     * Variable registering the density of the entity.
     */
    protected double density;

    /**
     * Method to check the density of the entity.
     *
     * @see implementation
     */
    protected double getDensity(){
        return this.density;
    }
    /**
     * Method to set the density of an entity.
     *
     * @param newDensity
     *        The new density of this entity.
     *
     * @see implementation
     */
    protected void setDensity(double newDensity){
        if (newDensity  < 0){
            newDensity = -newDensity;
        }
        this.density = newDensity;
    }

    /**
     * Returns the volume of the entity based on its radius.
     */
    protected double getVolume(){
        return 4.0/3.0 * Math.PI * Math.pow(this.getRadius(), 3);
    }


    /**
     * Method to check the mass of an entity.
     *
     * @see implementation
     */
    public double getMassOfEntity(){
        return this.getVolume() * getDensity();
    }

    // Termination
    /**
     * Variable registering whether or not an entity is terminated.
     */
    public boolean isTerminated = false;

    /**
     * Method to check whether or not an entity is terminated.
     *
     * @see implementation
     */
    public boolean checkTermination(){
        return this.isTerminated;
    }

    /**
     * The entity will be terminated and removed from its world.
     *
     * @see implementation
     */
    public void terminate(){
        isTerminated = true;
        if (getWorld() != null)
            getWorld().removeEntity(this);
    }

    //Interaction with world

    /**
     * Returns true if and only if the entity fits within the boundaries of the given world.
     *
     * @see implementation
     *
     * @param   world
     *          A candidate world for the entity to be placed in.
     */
    public boolean fitsInBoundaries(World world){
        if (world == null){
            return true;
        }
        return !(getPosition().getX() + 0.99*this.getRadius() > world.getWidth()
                || getPosition().getX() - 0.99*this.getRadius() < 0
                || getPosition().getY() + 0.99*this.getRadius() > world.getHeight()
                || getPosition().getY() - 0.99*this.getRadius() < 0);
    }

    /**
     * Returns true is and only if the entity can be placed within the world, based on the vector.
     *
     * @see implementation
     *
     * @param   world
     *          A candidate world for the entity to be placed in.
     *
     * @param   vector
     *          A vector which is part of an entity.
     */
    public boolean fitsInBoundaries(World world, Vector vector){
        if (world == null){
            return true;
        }
        return !(vector.getX() + 0.99*this.getRadius() > world.getWidth()
                || vector.getX() - 0.99*this.getRadius() < 0
                || vector.getY() + 0.99*this.getRadius() > world.getHeight()
                || vector.getY() - 0.99*this.getRadius() < 0);
    }

    /**
     * Returns true is and only if the entity does not overlap with any entity in the given world.
     *
     * @param   world
     *          A candidate world in which the ship might be placed
     *
     * @see implementation
     */
    public boolean noOverlapsInNewWorld(World world){
        for (Entity entity : world.getAllEntities()){
            if (this.overlap(entity)) {
                if (!(entity instanceof Ship && this instanceof Bullet && ((Ship) entity).getBullets().contains(this) //If they are not a bullet and its ship
                        || (entity instanceof Bullet && this instanceof Ship && ((Ship) this).getBullets().contains(entity)))) {
                    return false;
                }
            }
        }
        return true;
    }


}
