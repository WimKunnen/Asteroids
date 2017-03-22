package asteroids.model;
import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;



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
    public Ship(double x, double y, double velocityX, double velocityY, double heading, World world, double radius){
        super(x, y, velocityX, velocityY, radius, world);
        this.setMinimumRadius();
        if(isValidRadius(radius)){
            this.setRadius(radius);
        }else{
            throw new IllegalArgumentException();
        }
        this.setHeading(heading);
        this.setDensity(1.42 * Math.pow(10, 12));
        this.setThrustForce(1.1 *  Math.pow(10, 21));
        for (int i = 0; i < 15; i++){
            Bullet bullet = new Bullet();
            bullet.setSource(this);
            bullet.setRadius(0.1 * this.getRadius());
            this.reload(new Bullet());
        }
    }

    /**
     * Default initializer which uses the initializer defined in the Entity super class.
     */
    public Ship(){

        super();
        this.setMinimumRadius();
        this.setRadius(this.getMinimumRadius());
        this.setDensity(1.42 * Math.pow(10, 12));
        this.setThrustForce(1.1 *  Math.pow(10, 21));
        this.setHeading(0);
        for (int i = 0; i < 15; i++){
            Bullet bullet = new Bullet();
            bullet.setSource(this);
            bullet.setRadius(0.1 * this.getRadius());
            this.reload(new Bullet());
        }
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
     *          | result == ((this.getHeading() + angle < 2 * Math.PI) && (0 <= this.getHeading() + angle))
     */
    public boolean isValidAngle(double angle){
        return ((angle < 2 * Math.PI) && (0 <= angle));
    }
    //Mass
    private double totalMass = this.getMassOfEntity();
    protected double getTotalMass(){
        return this.totalMass;
    }
    protected void setTotalMass(ArrayList<Bullet> bullets){
        totalMass = this.getMassOfEntity();
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
    private void thrustOn(double timeDifference){
        this.thruster = true;
        this.thrust(timeDifference);
    }
    private void thrustOff(){
        this.thruster = false;
    }

    private double thrustForce;
    private double getThrustForce(){
        return this.thrustForce;
    }
    private void setThrustForce(double newForce){
        this.thrustForce = newForce;
    }

    public double getAcceleration(){
        return this.getTotalMass() / this.getThrustForce();
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

    private ArrayList<Bullet> bullets = new ArrayList<>();
    public ArrayList<Bullet> getBullets(){
        return this.bullets;
    }

    protected void reload(Bullet bullet) throws IllegalArgumentException{
        if (bullet.getSource() != this)
            throw new IllegalArgumentException("Bullet and Spaceship don't match");
        this.bullets.add(bullet);
        this.setTotalMass(bullets);
        bullet.setPosition(this.getPosition());
        bullet.setVelocity(this.velocity);
    }

    private void reload(ArrayList<Bullet> newBullets){
        for(Bullet bullet : newBullets){
            this.reload(bullet);
        }
    }

// TODO change fire
    public void fire() {
        if(this.getWorld() != null) {
            Bullet bullet = bullets.get(bullets.size() - 1);
            bullets.remove(bullet);

            Vector pointingVector = new Vector(Math.cos(this.getHeading()), Math.sin(this.getHeading()));

            double offset = this.getRadius() + bullet.getRadius();
            Vector relativePositionBullet = pointingVector.resizeVector(offset);
            Vector absolutePositionBullet = this.getPosition().sum(relativePositionBullet);
            bullet.setPosition(absolutePositionBullet);

            if (!bullet.fitsInBoundaries(this.getWorld())){
                bullet.terminate();
            }

            for(Entity entity : this.getWorld().getAllEntities()){
                if (bullet.overlap(entity)) {
                    List<Entity> entityList = new ArrayList<>();
                    entityList.add(bullet);
                    entityList.add(entity);
                    this.getWorld().resolveCollision(entityList);
                }
            }

            Vector bulletVelocity = pointingVector.resizeVector(250);
            bullet.setVelocity(bulletVelocity);
        }
    }

}