package asteroids.model;

import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;
import asteroids.model.Bullet;


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
    public Entity(double x, double y, double velocityX, double velocityY, World world)
            throws IllegalArgumentException{

        if(Double.isNaN(x) ||  Double.isNaN(y))
            throw new IllegalArgumentException();
        else if(this.getWorld() != null && (x + this.getRadius() > this.getWorld().getWidth()
                || y + this.getRadius() > this.getWorld().getHeight()))
            throw new IllegalArgumentException();
        else
            this.setPosition(new Vector(x, y));

        this.setMaximumVelocity(speedOfLight);
        this.setVelocity(new Vector(velocityX,velocityY));
        this.setWorld(world);
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
        this.setMaximumVelocity(this.speedOfLight);
        this.setVelocity(new Vector());
        this.setWorld(null);

    }

    // Termination
//    public boolean isTerminated = false;
//    public void terminate(){
//        this.isTerminated = true;
//        this.setWorld(null);
//    }

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
    protected void setPosition(Vector newPosition){
        this.position = newPosition;
    }

    /**
     * Returns the position vector of the entity.
     */
    @Basic
    public Vector getPosition(){
        return position;
    }

    // The entities world

    protected World world = new World();

    public World getWorld(){
        return this.world;
    }

    protected void setWorld(World newWorld){
        this.world = newWorld;
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

    public void negateVelocityX() {
        setVelocity(new Vector(-this.getVelocity().getX(), this.getVelocity().getY()));
    }
    public void negateVelocityY() {
        setVelocity(new Vector(this.getVelocity().getX(), -this.getVelocity().getY()));
    }

    //Radius
    /**
     * Variable registering the minimum radius of all entities.
     */
    protected double minimumRadius = 10;

    protected double getMinimumRadius(){
        return this.minimumRadius;
    }

    /**
     * Variable registering the radius of this entity.
     */
    protected double radius;

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

        return  !((deltaV.scalarProduct(deltaR) >= 0 || d <= 0 || this.overlap(other)) && this.getWorld() != other.getWorld());
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

    public double getTimeToCollisionWithBoundary() {
        double time = Double.POSITIVE_INFINITY;
        if (this.world == null)
            return time;
        if (getVelocity().getX() > 0) {
            double timeToCollisionX = (this.world.getWidth() - getRadius() - getPosition().getX()) / getVelocity().getX();
            if (timeToCollisionX < time)
                time = timeToCollisionX;
        }
        if (getVelocity().getX() < 0) {
            double timeToCollisionX = Math.abs((getPosition().getX() - getRadius()) / getVelocity().getX());
            if (timeToCollisionX < time)
                time = timeToCollisionX;
        }
        if (getVelocity().getY() > 0) {
            double timeToCollisionY = (world.getHeight() - getRadius() - getPosition().getY()) / getVelocity().getY();
            if (timeToCollisionY < time)
                time = timeToCollisionY;
        }
        if (getVelocity().getY() < 0){
            double timeToCollisionY = Math.abs((getPosition().getY() - getRadius()) / getVelocity().getY());
            if (timeToCollisionY < time)
                time = timeToCollisionY;
        }
        return time;

    }

    /**
     * Return at which side of the world the first collision with a boundary would happen: Right ("R"), Left("L"),
     * Top("T"), Bottom("B") or no boundary("X").
     *
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
        if (getVelocity().getX() < 0) {
            double timeToCollisionX = Math.abs((getPosition().getX() - getRadius()) / getVelocity().getX());
            if (timeToCollisionX < time) {
                time = timeToCollisionX;
                boundary = "L";
            }
        }
        if (getVelocity().getY() > 0) {
            double timeToCollisionY = (world.getHeight() - getRadius() - getPosition().getY()) / getVelocity().getY();
            if (timeToCollisionY < time) {
                time = timeToCollisionY;
                boundary = "T";
            }
        }
        if (getVelocity().getY() < 0){
            double timeToCollisionY = Math.abs((getPosition().getY() - getRadius()) / getVelocity().getY());
            if (timeToCollisionY < time) {
                time = timeToCollisionY;
                boundary = "B";
            }
        }
        return boundary;
    }

    public Vector getCollisionPositionWithBoundary() {
        double time = getTimeToCollisionWithBoundary();
        String boundary = getBoundaryOfCollision();
        Vector radiusVector;
        if (boundary.equals("X")) {return null;}
        else if (boundary.equals("R")) {radiusVector = new Vector(getRadius(),0);}
        else if (boundary.equals("L")) {radiusVector = new Vector(-getRadius(),0);}
        else if (boundary.equals("T")) {radiusVector = new Vector(0, getRadius());}
        else {radiusVector = new Vector(0, -getRadius());}

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

    // Mass
    protected double density;
    protected double getDensity(){
        return this.density;
    }
    protected void setDensity(double newDensity){
        this.density = newDensity;
    }

    protected double massOfEntity = 4.0/3 * Math.pow(this.getRadius(), 3) * this.density;
    protected double getMassOfEntity(){
        return this.massOfEntity;
    }

    // Termination
    public boolean isTerminated = false;

    public void terminate(){
        isTerminated = true;
        if (getWorld() != null)
            getWorld().removeEntity(this);
    }

    //Interaction with world
    public boolean fitsInBoundaries(World world){
        return !(getPosition().getX() + this.getRadius() > world.getWidth()
                || getPosition().getX() - this.getRadius() < 0
                || getPosition().getY() + this.getRadius() > world.getHeight()
                || getPosition().getY() - this.getRadius() < 0);
    }

    public boolean noOverlapsInNewWorld(World world){
        for (Entity entity : world.getAllEntities()){
            if (this.overlap(entity)){
                if (!(entity instanceof Ship && this instanceof Bullet && ((Ship) entity).getBullets().contains(this) //If they are not a bullet and its ship
                        || (entity instanceof Bullet && this instanceof Ship && ((Ship) this).getBullets().contains(entity))))
                        { return false;}
                }
        }
        return true;
    }
}
