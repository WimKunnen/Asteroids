package asteroids.model;
import be.kuleuven.cs.som.annotate.*;

import java.util.*;

@SuppressWarnings("all")

/**
 * A class of spaceships for the game Asteroids.
 * The class involves a position, velocity, orientation, maximum velocity, a radius, bullets and their actions, and a program.
 *
 * @invar A ship located in a world will always fit in the boundaries of that world.
 *        | if (getWorld() != null)
 *        |     this.fitsInBoundaries(getWorld())
 * @invar A ship located in a world never overlaps with other entities in that world.
 *        | for (Entity entity : this.getWorld().getAllEntities()){
 *        |     !(this.overlap(entity))
 *
 * @invar   The heading of a ship will always be a number between zero and 2 * PI.
 * 		    | isValidAngle()
 *
 * @invar   The velocity of a ship is always smaller than or equal to the speed of light.
 * 		    | velocity.vectorLength() <= speedOfLight
 *
 * @invar   The radius will always be greater or equal to th minimum radius.
 *          | isValidRadius()
 *
 * @invar   The maximum velocity of the ship shall always be smaller or equal to the speed of light.
 *          | getMaximumVelocity() <= speedOfLight
 *
 * @invar   The mass of the ship will always be greater then the mass of an entity given its radius and density.
 *          | getMass() >= getMassOfEntity()
 *
 *
 * @author  Wim Kunnen and Maarten Doclo
 *
 * @version 3.0
 *
 * About the authors and the software:
 *  Wim Kunnen:     Studies: Ingenieurswetenschappen: Elektrotechniek - Computer Wetenschappen.
 *  Maarten Doclo:  Studies: Ingenieurswetenschappen: Computer Wetenschappen - Elektrotechniek.
 *
 *  This Java class was created for the Asteroids Part 3 assignment for the course Objectoriented Programming
 *  given by Prof. dr. ir. E. Steegmans.
 *
 *  The code for this assignment can also be found at our private Github Repository:
 *  https://github.com/WimKunnen/AsteroidsGithub
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
     *
     * @param   mass
     *          The mass of this ship.
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
     * @post    The new heading is equal to heading
     *          | new.getHeading() == heading
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
    public Ship(double x, double y, double velocityX, double velocityY, double heading, double radius, double mass)
            throws IllegalArgumentException{
        super(x, y, velocityX, velocityY, radius);
        this.setRadius(radius);
        this.setHeading(heading);
        this.setDensity(1.42E12);
        this.setMass(mass);
        this.totalMass = this.getMass();
        this.setThrustForce(1.1E18);
        this.setScore(500 + Math.ceil(this.getRadius() * 2));
    }

    /**
     * Default initializer which uses the initializer defined in the Entity super class.
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
     * @post    The new heading is equal to 0.
     *          | new.getHeading() == 0
     *
     * @effect
     *          | this(0,0,0,0,0,this.minimumRadius,0)
     */
    public Ship(){
        super();
        this.setRadius(this.getMinimumRadius());
        this.setDensity(1.42E12);
        this.setThrustForce(1.1E18);
        this.setHeading(0);
        this.setScore(500 + Math.ceil(this.getRadius() * 2));
    }

    // Heading

    /**
     * Variable registering the orientation of this entity.
     */
    private double heading;

    /**
     * Variable registering the minimum radius of the ship
     */
    private static double minimumRadius = 10;

    /**
     * Returns the minimum radius of the ship.
     *
     * @return  result == minimumRadius
     */
    @Basic
    @Override
    protected double getMinimumRadius(){
        return minimumRadius;
    }

    /**
     * Returns the heading of the ship.
     *
     * @return  result == heading
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
     * @post    ...
     *          | new.getHeading == angle
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
    private double mass = this.getMassOfEntity();

    /**
     * A method which changes the mass of the ship if the new mass is greater than
     * the mass of an entity with the same radius and density.
     *
     * @pre     The new mass must be greater than or equal to the mass of a similar entity.
     *          | newMass >= getMassOfEntity
     *
     * @post    The mass of the ship is equal to the new mass.
     *          | new.getMass == newMass
     *
     * @param   newMass
     *          The new mass of the ship.
     */
    public void setMass(double newMass){
        this.mass = newMass > this.getMassOfEntity() ? newMass : this.getMassOfEntity();
    }

    /**
     * Returns the mass of the ship.
     *
     * @return  result == mass
     */
    private double getMass(){
        return this.mass;
    }

    /**
     * Variable registering the total mass of the ship.
     */
    private double totalMass;

    /**
     * Returns the total mass of the ship.
     *
     * @return  result == totalMass
     */
    public double getTotalMass(){
        return this.totalMass;
    }

    //Thruster
    /**
     * Variable registering the state of the ship's thruster.
     */
    private boolean thruster = false;

    /**
     * Returns the state of the ship's thruster.
     *
     * @return  result == thruster
     */
    @Basic
    public boolean getThrusterState(){
        return this.thruster;
    }


    /**
     * Method turning the thruster on.
     *
     * @post    thruster == true
     */
    public void thrustOn(){
        this.thruster = true;
    }
    /**
     * Method turning the thruster off.
     *
     * @post    thruster == false
     */
    public void thrustOff(){
        this.thruster = false;
    }

    /**
     * Variable registering the force the thruster can exert.
     */
    private double thrustForce;

    /**
     * Returns the force the thruster can exert.
     *
     * @return  result == thrustForce
     */
    protected double getThrustForce(){ return this.thrustForce; }

    /**
     * A method which sets the ship's thrust force to the new force.
     *
     * @post    The ship's thrust force is equal to the given force.
     *          | new.getThrustForce == newForce
     *
     * @param   newForce
     *          The new force the ship's thruster can exert.
     */
    public void setThrustForce(double newForce){
        this.thrustForce = newForce;
    }

    /**
     * Returns the ships acceleration based on the thruster force and the ship's mass.
     *
     * @return  result == thrustForce / totalMass
     */
    public double getAcceleration(){
        return this.getThrusterState() ? this.getThrustForce() / this.getTotalMass() : 0;
    }

    /**
     * A method which changes the velocity of the ship based on its acceleration and a given time difference.
     *
     * @post    The new velocity is equal to the sum of the old velocity and the acceleration times the timeDifference.
     *          | new.getVelocity == this.getVelocity + this.getAcceleration * timeDifference
     *
     * @param   timeDifference
     *          The difference in time in which the velocity changes.
     */
    public void thrust(double timeDifference){
        double acceleration = this.getAcceleration();
        if (acceleration < 0){
            acceleration = 0;
        }

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
    /**
     * Set registering all bullets that are loaded on a ship.
     */
    private HashSet<Bullet> bullets = new HashSet<>();

    /**
     * Returns a set containing all bullets on a ship.
     *
     * @return  result == bullets
     */
    public HashSet<Bullet> getBullets(){
        HashSet<Bullet> bullets = new HashSet<>();
        bullets.addAll(this.bullets);
        return bullets;
    }

    /**
     * Removes the given bullet from this ship.
     *
     * @post The given bullet doesn't belong to this ships collection of bullets anymore.
     *      | !new.getBullets().contains(bullet)
     *
     * @post The bullet doesn't belong to a world.
     *      | bullet.getWorld() == null
     *
     * @param bullet
     *        The bullet to remove from this ship.
     *
     * @throws  IllegalArgumentException
     *          Thrown if the bullets source is not this ship.
     *          | bullet.getSource() != this
     */
    public void removeBullet(Bullet bullet){
        if (bullet.getSource() != this){
            throw new IllegalArgumentException();
        }
        bullets.remove(bullet);
        bullet.setWorld(null);
    }

    /**
     * A method which returns a random bullet out of the set of bullets
     *
     * @return  result == bullet
     */
    public Bullet getRandomBulletOnShip(){
        for(Bullet bullet : this.getBullets())
            return bullet;
        return null;
    }

    /**
     * A method which loads a bullet on the ship, if and only if the bullet was fired by this ship
     * and the bullet has already been out of the ship.
     * | If (bullet.getSource() == this && bullet.hasBeenOutOfShip()) then
     * |    bullets.add(bullet)
     *
     * When a bullet is loaded on the ship, the mass of the bullet is added to the total mass of the ship.
     * | totalMass += bullet.getMass()
     *
     * When a bullet is loaded on the ship, the position and velocity of the bullet and the ship are equal.
     * | bullet.sePosition(this.getPosition())
     * | bullet.setVelocity(this.getVelocity())
     *
     * When a bullet is loaded on a ship, its state changes to "Has not been out of the ship".
     * | bullet.switchBeenOutOfShip(false)
     *
     * When a bullet is loaded on the ship, it is removed from its world.
     * | bullet.getWorld().removeEntity(this)
     *
     * @param   bullet
     *          A bullet to be loaded on the ship
     *
     * @post    The amount of bullets on the ship is increased by one.
     *          | new.getBullets().size() == this.getBullets().size() + 1
     *
     * @post    The ships bullets contains the bullet.
     *          | bullets.contains(bullet) == true
     *
     * @post    The bullet's and ship's position and velocity are equal.
     *          | this.getVelocity() == bullet.getVelocity() && this.getPosition() == bullet.getPosition()
     *
     * @post    The world no longer contains the bullet.
     *          | this.getWorld.getAllBullets.contains(bullet) == false
     *
     * @throws  IllegalArgumentException
     *          Throws an exception if the bullet wasn't fired by this ship.
     *          | bullet.getSource() != this || !(bullet != null && bullet.liesWithinShip(this))
     */
    public void reload(Bullet bullet) throws IllegalArgumentException{
        if (bullet != null && bullet.liesWithinShip(this)) {
            if (bullet.getSource() == null)
                bullet.setSource(this);
            if (bullet.getSource() != this)
                throw new IllegalArgumentException("Bullet and Spaceship don't match");
            if (bullet.hasBeenOutOfShip() && bullet.getSource() == this) {
                this.bullets.add(bullet);
                this.totalMass = this.totalMass + bullet.getMassOfEntity();
                bullet.setPosition(this.getPosition());
                bullet.setVelocity(this.velocity);
                bullet.switchBeenOutOfShip(false);
                if (bullet.getWorld() != null) {
                    bullet.getWorld().removeEntity(bullet);
                }
            }
        }
        else{
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * A method which loads a collection of bullets on the ship based on the method to reload a single bullet on a ship.
     *
     * @param   newBullets
     *          A collection of bullets to be loaded on the ship.
     */
    public void reload(Collection<Bullet> newBullets){
        if (newBullets.contains(null)){
            throw new IllegalArgumentException();
        }
        for(Bullet bullet : newBullets){
            this.reload(bullet);
        }
    }

    /**
     * A method which fires a bullet from the ship, if the ship is in a non null world
     * and there is at least one bullet on the ship.
     * | If (this.getWorld() != null && getBullets.size() > 0) then
     * |    bullet = getRandomBulletOnShip
     * |    bullets.remove(bullet)
     *
     * When a bullet is fired from a ship, the ship's total mass is reduced by the bullets mass.
     * | totalMass -= bullet.getMassOfEntity
     *
     * When a bullet is fired from a ship, its state changes to "Has been out of the ship".
     * | bullet.switchBeenOutOfShip(false)
     *
     * When a bullet is fired from a ship, its position is changed to a point where the distance between the ship
     * and bullet is equal to the sum of there radii in the direction of the ships heading.
     * |Vector pointingVector = new Vector(Math.cos(this.getHeading()), Math.sin(this.getHeading()));
     * |double offset = this.getRadius() + bullet.getRadius();
     * |Vector relativePositionBullet = pointingVector.resizeVector(offset);
     * |Vector absolutePositionBullet = this.getPosition().sum(relativePositionBullet);
     * |bullet.setPosition(absolutePositionBullet);
     *
     * When a bullet is fired from a ship and the bullet would not fit within the boundaries of the world,
     * it is terminated.
     * | If !(bullet.fitsInBoundaries(this.getWorld()) then
     * |    bullet.terminate
     *
     * When a bullet is fired from a ship, its velocity is set at 250 km/s in the direction the ship is heading.
     * | Vector bulletVelocity = pointingVector.resizeVector(250);
     * | bullet.setVelocity(bulletVelocity);
     *
     * When a bullet is fired from a ship and would collide with another entity, the collision is resolved.
     * | for(Entity entity : this.getWorld().getAllEntities())
     * |   if (bullet.overlap(entity))
     * |     this.getWorld().resolveEntityCollision(bullet, entity);
     *
     * @pre     The ships world is not null.
     *          | this.getWorld() != null
     *
     * @pre     The ship has at least one bullet
     *          | getBullets.size() > 0
     *
     * @post    The amount of bullets on the ship is decreased by one.
     *          | new.getBullets().size() == this.getBullets().size() - 1
     *
     * @post    The ships bullets does not contain the bullet.
     *          | bullets.contains(bullet) == false
     *
     * @post    The ships velocity is equal to 250 km/s in the direction the ship is heading.
     *          | bullet.getVelocity.vectorLength == 250
     */
    public void fire() {
        if(this.getWorld() != null && this.getBullets().size() > 0) {
            Bullet bullet = this.getRandomBulletOnShip();
            this.bullets.remove(bullet);
            this.totalMass -= bullet.getMassOfEntity();

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

            Set<Entity> allEntities = new HashSet<>();
            allEntities.addAll(getWorld().getAllEntities());
            Iterator<Entity> iter = allEntities.iterator();

            while (iter.hasNext()){
                Entity thisEntity = iter.next();
                if (bullet.overlap(thisEntity)){
                    Collision collision = new Collision(this.getWorld());
                    collision.resolveEntityCollision(bullet, thisEntity);
                }
            }

        }

    }

    /**
     * A method which teleports the ship to a valid location in its world. The location is chosen at random.
     * If the ship would overlap with another entity when placed at the new location, the ship is terminated.
     *  | if(!noOverlapsInNewWorld) then
     *  |   this.terminate()
     */
    public void teleportRandomLocation() {
        World thisWorld = getWorld();
        thisWorld.removeEntity(this);
        double xRange = thisWorld.getWidth() - 2 * getRadius();
        double yRange = thisWorld.getHeight() - 2 * getRadius();
        Vector newPos = new Vector(getRadius() + Math.random() * xRange, getRadius() + Math.random() * yRange);
        setPosition(newPos);
        if (this.noOverlapsInNewWorld(thisWorld)){
            thisWorld.addEntity(this);
        }
        else{
            this.terminate();
        }
    }

    private Program program;

    /**
     * Method which returns the program that is currently loaded on the ship.
     *
     * @return  The ships program
     *          | this.program
     */
    @Basic
    public Program getProgram(){
        return this.program;
    }

    /**
     * Sets the ships program to the given program.
     *
     * @param   newProgram
     *          | Program that will be loaded on the ship.
     */
    public void setProgram(Program newProgram){
        this.program = newProgram;
        newProgram.setShip(this);
    }

    /**
     * Executes the program loaded on the ship with a runtime of dt.
     *
     * @param   dt
     *          The amount of time the program is allowed to run.
     */
    public List<Object> executeProgram(double dt){
        if(getProgram() == null){
            return new ArrayList<>();
        }
        System.out.println("executing");
        List<Object> printedList = getProgram().execute(dt);
        if (getProgram().isExecuted()){
            List<Object> printedObjectList = new ArrayList<>(printedList);
            return printedObjectList;
        }
        else{
            return null;
        }
    }

    private double points;

    public void addPoints(double points){
        this.points += points;
    }

    public double getPoints() {
        return points;
    }
}