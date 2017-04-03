package asteroids.model;
import be.kuleuven.cs.som.annotate.*;

import java.util.Collection;
import java.util.HashSet;


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
    public Ship(double x, double y, double velocityX, double velocityY, double heading, double radius, double mass){
        super(x, y, velocityX, velocityY, radius);
        this.setMinimumRadius();
        if(isValidRadius(radius)){
            this.setRadius(radius);
        }else{
            throw new IllegalArgumentException();
        }
        this.setHeading(heading);
        this.setDensity(1.42 * Math.pow(10, 12));
        this.setMassOfEntity(this.getDensity());
        this.setMass(mass);
        //this.setTotalMass(this.getBullets());
        this.setThrustForce(1.1 *  Math.pow(10, 21));
    }

    /**
     * Default initializer which uses the initializer defined in the Entity super class.
     */
    public Ship(){

        super();
        this.setMinimumRadius();
        this.setRadius(this.getMinimumRadius());
        this.setDensity(1.42E12);
        this.setMassOfEntity(this.getDensity());
        //this.setTotalMass(new HashSet<>());
        this.setThrustForce(1.1 *  Math.pow(10, 21));
        this.setHeading(0);
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
     *          The angle between the ship's direction and the x-axis.
     *
     * @return  True if and only if the angle is between 0 and 2 * π.
     *          | result == ((angle < 2 * Math.PI) && (0 <= angle))
     */
    public boolean isValidAngle(double angle){
        return ((angle < 2 * Math.PI) && (0 <= angle));
    }

    //Mass
    /**
     * Variable registering the current mass of the ship.
     */
    private double mass;
    private double getMass(){
        return this.mass;
    }

    public void setMass(double newMass){
        if (newMass > this.getMassOfEntity())
            this.mass = newMass;
    }

    /**
     * Variable registering the total mass of the ship.
     */
    private double totalMass = this.mass;
    public double getTotalMass(){
        return this.totalMass;
    }
    protected void setTotalMass(HashSet<Bullet> bullets){
        //this.totalMass = this.getMass();
        for(Bullet bullet : bullets){
            this.totalMass += bullet.getMassOfEntity();
        }
    }
    //Thruster

    private boolean thruster = false;
    public boolean getThrusterState(){
        return this.thruster;
    }

    //TODO check thrust enable meaning
    public void thrustOn(){
        this.thruster = true;
    }
    public void thrustOff(){
        this.thruster = false;
    }

    private double thrustForce;
    protected double getThrustForce(){ return this.thrustForce; }
    private void setThrustForce(double newForce){
        this.thrustForce = newForce;
    }

    public double getAcceleration(){
        return this.getThrustForce() / this.getTotalMass();
    }

    public void thrust(double timeDifference){

        double acceleration = this.getAcceleration();
        Vector accelerationVector = new Vector(acceleration * Math.cos(this.getHeading()) * timeDifference,
                acceleration*Math.sin(this.getHeading()) * timeDifference);
        Vector newVelocity = this.getVelocity().sum(accelerationVector);
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

    //Bullets

    private HashSet<Bullet> bullets = new HashSet<>();
    public HashSet<Bullet> getBullets(){
        return this.bullets;
    }

    public Bullet getRandomBulletOnShip(){
        for(Bullet bullet : this.getBullets())
            return bullet;
        return null;
    }

    public void reload(Bullet bullet) throws IllegalArgumentException{
        if (bullet.getSource() == null)
            bullet.setSource(this);
        if (bullet.getSource() != this)
            throw new IllegalArgumentException("Bullet and Spaceship don't match");
        if (bullet.hasBeenOutOfShip()) {
            this.bullets.add(bullet);
            this.setTotalMass(bullets);
            bullet.setPosition(this.getPosition());
            bullet.setVelocity(this.velocity);
            bullet.switchBeenOutOfShip(false);
            if (bullet.getWorld() != null) {
                bullet.getWorld().removeEntity(bullet);
            }
        }
    }

    public void reload(Collection<Bullet> newBullets){
        for(Bullet bullet : newBullets){
            this.reload(bullet);
        }
    }

// TODO change fire
    public void fire() {
        if(this.getWorld() != null && this.bullets.size() > 0) {
            Bullet bullet = this.getRandomBulletOnShip();
            bullets.remove(bullet);

            bullet.switchBeenOutOfShip(false);

            Vector pointingVector = new Vector(Math.cos(this.getHeading()), Math.sin(this.getHeading()));

            double offset = this.getRadius() + bullet.getRadius();
            Vector relativePositionBullet = pointingVector.resizeVector(offset);
            Vector absolutePositionBullet = this.getPosition().sum(relativePositionBullet);
            bullet.setPosition(absolutePositionBullet);

            if (!bullet.fitsInBoundaries(this.getWorld())){
                bullet.terminate();
            }
            
            this.getWorld().addEntity(bullet);

            Vector bulletVelocity = pointingVector.resizeVector(250);
            bullet.setVelocity(bulletVelocity);

            for(Entity entity : this.getWorld().getAllEntities()){
                if (bullet.overlap(entity)) {
                    this.getWorld().resolveEntityCollision(bullet, entity);
                }
            }
        }

    }

}