package asteroids.model;

@SuppressWarnings("all")
/**
 * A class of planetoids for the Asteroid project.
 *
 * @invar A planetoid located in a world will always fit in the boundaries of that world.
 *        | if (getWorld() != null)
 *        |     this.fitsInBoundaries(getWorld())
 *
 * @invar A planetoid located in a world never overlaps with other entities in that world.
 *        | for (Entity entity : this.getWorld().getAllEntities()){
 *        |     !(this.overlap(entity))
 *
 * @invar   The velocity of a planetoid is always smaller than or equal to the speed of light.
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
public class Planetoid extends MinorPlanet {

    /**
     * Initializes a new planetoid by using the initializer defined in the Entity super class.
     * @param   x
     *          The initial position of the new planetoid along the x-axis.
     *
     * @param   y
     *          The initial position of the new planetoid along the y-axis.
     *
     * @param   velocityX
     *          The initial velocity of the new planetoid along the x-axis.
     *
     * @param   velocityY
     *          The initial velocity of the new planetoid along the y-axis.
     *
     * @param   radius
     *          The initial radius of the new planetoid.

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
    public Planetoid(double x, double y, double velocityX, double velocityY, double radius, double distanceTravelled)
            throws IllegalArgumentException{
        super(x, y, velocityX, velocityY, radius);
        this.setDensity(0.917E12);
        this.setDistanceTravelled(distanceTravelled);
        decrementRadiusDistance(distanceTravelled);
    }

    /**
     * Default initializer which uses the initializer defined in the MinorPlanet super class.
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
     *
     * @effect
     *          | this(0,0,0,0,this.minimumRadius)
     */
    public Planetoid() {
        super();
        this.setDensity(0.917E12);
        this.setRadius(this.getMinimumRadius());
    }

    /**
     * A method to decrement the radius of the planetoid based on the traveled time.
     *
     * @param timeDifference
     *        The amount of time the planetoid has traveled.
     *
     * @see implementation
     *
     * @throws IllegalArgumentException
     *       | !isValidTimeDifference(timeDifference)
     */
    public void decrementRadius(double timeDifference) throws IllegalArgumentException {
        if (!isValidTimeDifference(timeDifference)){
            throw new IllegalArgumentException();
        }
        double velocity = this.getVelocity().vectorLength();
        double distanceTravelled = velocity * timeDifference;

        double newRadius = this.getRadius() - 0.000001 * distanceTravelled;
        if (newRadius <= 5){
            this.terminate();
        }
        else{
            this.setRadius(newRadius);
        }
    }

    /**
     * A method to decrement the radius of the planetoid based on the traveled distance.
     *
     * @param distanceTravelled
     *        The distance traveled by this planetoid.
     * @throws IllegalArgumentException
     *         | distanceTravelled <=0
     */
    public void decrementRadiusDistance(double distanceTravelled) throws IllegalArgumentException{
        if (distanceTravelled < 0){
            throw new IllegalArgumentException();
        }
        double newRadius = this.getRadius() - 0.000001 * distanceTravelled;
        if (newRadius < 5){
            this.terminate();
        }
        else{
            this.setRadius(newRadius);
        }
    }

    /**
     * A method to terminate a planetoid.
     * When a planetoid has a radius greater than 30 km and is terminated, two new asteroids spawn with
     * a difference of half the planetoids  between their centers. One of asteroids get a random velocity
     * while the other will move in the opposite direction.
     *
     * @post    The entity is no longer part of its (non null) world.
     *          | !getWorld().getEntities().contains(this)
     *
     * @see implementation
     */
    @Override
    public void terminate(){
        World thisWorld = getWorld();

        if (this.getRadius() >= 30){
            double positionAngle = Math.random() * 2 * Math.PI;
            double velocityAngle = Math.random() * 2 * Math.PI;

            Vector velocityA = new Vector(this.getVelocity().vectorLength() * 1.5 * Math.cos(velocityAngle),
                    this.getVelocity().vectorLength() * 1.5 * Math.sin(velocityAngle));
            Vector velocityB = velocityA.negate();

            Vector positionA = new Vector(this.getRadius() / 2 * Math.cos(positionAngle),
                    this.getRadius() / 2 * Math.sin(positionAngle));
            Vector positionB = positionA.negate();

            Asteroid childA = new Asteroid();
            childA.setPosition(positionA.sum(getPosition()));
            childA.setVelocity(velocityA);
            childA.setRadius(this.getRadius()/2);

            Asteroid childB = new Asteroid();
            childB.setPosition(positionB.sum(getPosition()));
            childB.setVelocity(velocityB);
            childB.setRadius(this.getRadius()/2);

            this.isTerminated = true;
            if (getWorld() != null)
                this.getWorld().removeEntity(this);

            thisWorld.addEntity(childA);
            thisWorld.addEntity(childB);
        }
        else {
            this.isTerminated = true;
            if (getWorld() != null)
                this.getWorld().removeEntity(this);

        }
    }

    /**
     * Variable registering the total distance traveled by this planetoid.
     */
    private double distanceTravelled;

    /**
     * Return the distance travelled by this planetoid.
     */
    public double getDistanceTravelled(){
        return distanceTravelled;
    }

    /**
     * Method to set the distance travelled by the planetoid.
     *
     * @param distanceTravelled
     */
    public void setDistanceTravelled(double distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    /**
     * Method to add an certain distance to the variable distanceTravelled.
     * @param distance
     *        The distance with which distanceTravelled needs to be increased.
     * @post ...
     *       | new.distanceTravelled = this.distanceTravelled + distance.
     */
    protected void travel(double distance){
        this.distanceTravelled += distance;
    }

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
    @Override
    public void move(double timeDifference) throws IllegalArgumentException{

        if(isValidTimeDifference(timeDifference)){
            setPosition(this.getPosition().sum(getVelocity().resizeVector(timeDifference)));
            double distanceTravelled = timeDifference * this.getVelocity().vectorLength();
            this.travel(distanceTravelled);
            this.decrementRadius(timeDifference);
            this.setDistanceTravelled(this.getDistanceTravelled() + distanceTravelled);
        }else{
            throw new IllegalArgumentException();
        }
    }
}

