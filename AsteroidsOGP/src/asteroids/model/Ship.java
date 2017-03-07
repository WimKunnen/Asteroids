package asteroids.model;
import be.kuleuven.cs.som.annotate.*;


/**
 * A class of spaceships for the game Asteroids.
 *
 * @author Wim Kunnen and Maarten Doclo
 * 
 * @invar   The heading of a ship will always be a number between zero and 2 * PI.
 * 		    | isValidAngle()
 * @invar   The velocity of a ship is always smaller than or equal to the speed of light.
 * 		    |getVelocity <= speedOfLight
 */

//TODO Exception, Vector Class, Check the file, Testing

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
     * @throws  IllegalArgumentException
     *          The given radius is not a valid radius for any ship.
     *          | (!isValidRadius(radius)
     */
    public Ship(double x, double y, double velocityX, double velocityY, double radius, double heading)
            throws IllegalArgumentException{
                if(x == Double.NaN || y == Double.NaN)
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
     */
    public Ship(){

        setPosition(new Vector());
        this.radius = this.minimumRadius;
        this.setMaximumVelocity(this.speedOfLight);
        this.setVelocity(new Vector());
        this.setHeading(0);

    }

    //Position:
    //TODO check defensive style!

    private Vector position = new Vector(this.getCoordX(), this.getCoordY());

    private double coordX;

    private double coordY;

    /**
     * Returns the coordinate of the ship along the X-axis.
     */
    @Basic
    private double getCoordX(){return coordX;}

    /**
     * Returns the coordinate of the ship along the Y-axis.
     */
    @Basic
    private double getCoordY(){return coordY;}

    /**
     * Changes the current position vector to a new position vector.
     *
     * @param   newPosition
     *          The new position vector.
     *
     * @post    The position is set to the new position.
     *          |new.getPosition == newPosition
     */
    private void setPosition(Vector newPosition){this.position = newPosition;}

    /**
     * Returns the position vector of the ship.
     */
    public Vector getPosition(){return position;}

    //Velocity:
    //TODO check total style!

    private final double speedOfLight = 300000;

    private final double speedOfLightSquared = speedOfLight*speedOfLight;

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
    public double getMaximumVelocitySquared(){
        return this.maximumVelocitySquared;
    }

    /**
     * Sets the maximum velocity at the given velocity.
     *
     * @param   velocity
     *          The new maximum velocity
     *
     * @Post    If the given velocity is smaller or equal to speedOfLight and nonegative, the new maximum velocity is set at the given velocity.
     *          If the given velocity is greater than speedOfLight, the new maximum velocity is set at the speedOfLight.
     *          Else, the new maximum velocity is set at 0.
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

    private double velocityX;

    private double velocityY;

    /**
     * Returns the velocity vector.
     */
    private Vector velocity = new Vector(velocityX, velocityY);

    /**
     * The new velocity is set at the given velocity.
     *
     * @param   velocity
     *          The new velocity vector.
     *
     * @Post    If the square of the new total velocity does not exceed the square of the maximum velocity,
     *          the new velocity is equal the the given velocity.
     *          | new.getVelocityX() == velocity
     *          If the square of the new total velocity does exceed the square of the maximum velocity,
     *          the new total velocity is set at the maximum velocity, but the new direction of the velocity remains unaltered.
     *
     */
    private void setVelocity(Vector velocity){
        this.velocity = velocity.vectorLengthSquared() > this.getMaximumVelocitySquared()
                ? velocity.normalize().resizeVector(maximumVelocity) :  velocity;
    }

    public Vector getVelocity(){
        return this.velocity;
    }

    // Heading
    // TODO check nominal style!

    private double heading;

    /**
     * Return the heading of the ship.
     */
    @Basic
    public double getHeading(){return this.heading;}

    /**
     * Set the heading at the given angle.
     *
     *
     * @param   angle
     *          The angle at which the new heading will be set.
     *
     * @pre     The angle must be a valid angle.
     *          |isValidAngle(angle)
     */
    private void setHeading(double angle) {
        this.heading = angle;
    }

    /**
     * Checks if the angle is valid.
     *
     * @param   angle
     *          The angle between the ship's direction and the x-axis.
     *
     * @return  True if and only if the angle is between 0 and 4*PI.
     *          | result == ((this.getHeading() + angle < 4 * Math.PI) && (0 <= this.getHeading() + angle))
     */
    public boolean isValidAngle(double angle){return ((angle < 2 * Math.PI) && (0 <= angle));}


    //Radius
    //TODO Implement Defensive style!

    private double minimumRadius = 10;

    private final double radius;

    /**
     * Returns the radius of the ship.
     */
    @Basic
    @Immutable
    public double getRadius(){return this.radius;}

    /**
     * Returns true if and only if the given radius is larger than the minimum radius.
     *
     */
    public boolean isValidRadius(double radius){return radius >= this.minimumRadius;}

    //Move
    //TODO Implement defensive style!

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
            setPosition(this.position.sum(velocity.resizeVector(timeDifference)));
        }else{
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns true if and only if the given time difference is nonnegative.
     *
     */
    private boolean isValidTimeDifference(double timeDifference){return timeDifference >= 0;}

    //TODO check nominal style!

    /**
     * Increases the heading of the ship by a given angle.
     *
     * @param   angle
     *          The angle by which the heading is increased.
     *
     * @post    The heading is increased by the given angle.
     *          | new.getHeading() == this.getHeading + angle
     *
     * @post    If the new angle is greater than 2 * PI or smaller than 0, it is changed to an angle between 0 and 2 * PI.
     */
    public void turn(double angle) {
        if (isValidAngle(this.getHeading() + angle)) {
            this.setHeading(this.getHeading() + angle);
        } else if (angle + this.getHeading() >= 2 * Math.PI) {
            this.turn(angle + this.getHeading() - 2 * Math.PI);
        } else if (0 > angle + this.getHeading()) {
            this.turn(angle + this.getHeading() + 2 * Math.PI);
        }
    }

    //TODO check total style!

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
        Vector addedVelocity = new Vector(addedVelocitySize * Math.cos(this.getHeading()),
                addedVelocitySize * Math.sin(this.getHeading()));
        Vector newVelocity = this.velocity.sum(addedVelocity);
        if(addedVelocity.vectorLengthSquared() >= 0){
            this.setVelocity(newVelocity);
        }else{
            this.thrust(0);
        }
    }

    // Collision detection
    // TODO check defensive style!

    /**
     * Returns the distance between two ships.
     *
     * @param   other
     *          The second ship.
     *
     * @throws IllegalArgumentException
     *          The other ship does not exist.
     *          | other == null
     */
    public double getDistanceBetween(Ship other) throws IllegalArgumentException{
        if(other != null){
            return Math.sqrt((this.getPosition().getX() - other.getPosition().getX()) * (this.getPosition().getX() - other.getPosition().getX())
                    - (this.getPosition().getY() - other.getPosition().getY()) * (this.getPosition().getY() - other.getPosition().getY()))
                    - (this.getRadius() + other.getRadius());
        }else{
            throw new IllegalArgumentException("Not an existing ship!");
        }
    }

    /**
     * Returns true if and only if the distance between the two ships is nonnegative.
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
            return this.getDistanceBetween(other) < 0;
        }else{
            throw new IllegalArgumentException("Not an existing ship!");
        }
    }

    /**
     * Returns true if and only if the ships will collide at some point.
     *
     * @param   other
     *          The other ship
     *
     * @throws  IllegalArgumentException
     *          The other ship does not exist.
     *          | other == null
     */
    public boolean willCollide(Ship other) throws IllegalArgumentException{
        try {
            Vector deltaV = this.deltaV(other);
            Vector deltaR = this.deltaR(other);
            double sigma = this.getRadius() + other.getRadius();
            double d = deltaV.scalarProduct(deltaR) * deltaV.scalarProduct(deltaR)
                    - deltaV.scalarProduct(deltaV) * (deltaR.scalarProduct(deltaR) - sigma * sigma);


            return  !(deltaV.scalarProduct(deltaR) >= 0 || d <= 0);

        }catch (NullPointerException e){
            throw new IllegalArgumentException("The other ship does not exist!");
        }
    }



    /**
     * Returns the time it takes for two ships to collide.
     *
     * @param other
     *        | The other ship
     *
     * @throws IllegalArgumentException
     *        | The other ship does not exist
     *        | other == null
     */
    public double getTimeToCollision(Ship other) throws NullPointerException{
        try {
            Vector deltaV = this.deltaV(other);
            Vector deltaR = this.deltaR(other);
            double sigma = this.sigma(other);
            double d = this.d(deltaV, deltaR, sigma);

            if (this.willCollide(other)) {
                return -(deltaV.scalarProduct(deltaR) + Math.sqrt(d)) / deltaV.scalarProduct(deltaV);
            } else {
                return Double.POSITIVE_INFINITY;
            }

        }catch (NullPointerException e){
            throw new NullPointerException("The other ship does not exist!");
        }
    }

    /**
     * Returns the position at which the ships will collide.
     *
     * @param    other
     *          |the other ship
     *
     * @throws  IllegalArgumentException
     *          |The other ship does not exist
     *          | other == null
     */
    public Vector getCollisionPosition(Ship other) throws IllegalArgumentException {
        try {
            Vector deltaV = this.deltaV(other);
            Vector deltaR = this.deltaR(other);
            double sigma = this.sigma(other);
            double d = this.d(deltaV, deltaR, sigma);

            if (!this.willCollide(other)) {
                return null;

            } else {
                double deltaT = -(deltaV.scalarProduct(deltaR) + Math.sqrt(d)) / deltaV.scalarProduct(deltaV);

                double newCoordXthis = this.getPosition().getX() + deltaT * this.getVelocity().getX();
                double newCoordYthis = this.getPosition().getY() + deltaT * this.getVelocity().getY();
                Vector newPositionThis = new Vector(newCoordXthis, newCoordYthis);

                double newCoordXother = other.getPosition().getX() + deltaT * other.getVelocity().getX();
                double newCoordYother = other.getPosition().getY() + deltaT * other.getVelocity().getY();
                Vector newPositionOther = new Vector(newCoordXother, newCoordYother);

                Vector pointingVector = new Vector(newCoordXother - newCoordXthis, newCoordYother - newCoordYthis);
                pointingVector = pointingVector.normalize();

                return newPositionThis.sum(pointingVector.resizeVector(this.getRadius()));
            }
        }catch (NullPointerException e){
            throw new IllegalArgumentException("The other ship does not exist!");
        }

    }

    /**
     * Returns the difference of the velocity vectors of two ships.
     *
     */
    private Vector deltaV(Ship other){
        return new Vector(other.getVelocity().getX() - this.getVelocity().getX(),
                other.getVelocity().getY() - this.getVelocity().getY());
    }

    /**
     * Returns the vectorial difference of the centers of the two ships.
     *
     */
    private Vector deltaR(Ship other){
        return new Vector(other.getPosition().getX() - this.getPosition().getX(),
                other.getPosition().getY() - this.getPosition().getY());
    }

    /**
     * Returns the sum of the radii of the ships.
     *
     */
    private double sigma(Ship other){
        return this.getRadius() + other.getRadius();
    }

    /**
     * Returns the constant d.
     *
     */
    private double d(Vector deltaV, Vector deltaR, double sigma){
        return deltaV.scalarProduct(deltaR) * deltaV.scalarProduct(deltaR)
                - deltaV.scalarProduct(deltaV) * (deltaR.scalarProduct(deltaR) - sigma * sigma);
    }



}
	   
			  
   
   
