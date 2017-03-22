package asteroids.facade;

import asteroids.model.*;
import asteroids.part2.CollisionListener;
import asteroids.part2.facade.IFacade;
import asteroids.util.ModelException;

import java.util.Collection;
import java.util.Set;

/**
 * A class implementing the IFacade class.
 * It connects the Ship class to the GUI (Graphical User Interface).
 *
 * @author  WimKunnen and Maarten Doclo.
 *
 * @version 2.0
 */


public class Facade implements IFacade {

    /**
     * Default initializer for the Ship class.
     */
    public Facade() {
    }

    /******
     * SHIP
     *****/


    /**
     * Returns a new Ship at the origin point (0,0) with a velocity of 0, a heading of 0 and a radius equal to the minimum radius.
     */
    @Override @Deprecated
    public Ship createShip() throws ModelException {
        return new Ship();
    }

    /**
     * Returns a ne Ship at (x,y) with a velocity vector of (xVelocity, yVelocity), a heading of orientation and a radius equal to radius.
     *
     * @param   x
     *          The position of the newly created Ship along the X-axis.
     *
     * @param   y
     *          The position of the newly created Ship along the Y-axis.
     *
     * @param   xVelocity
     *          The velocity of the newly created Ship along the X-axis.
     *
     * @param   yVelocity
     *          The velocity of the newly created Ship along the Y-axis.
     *
     * @param   radius
     *          The radius of the newly created Ship.
     *
     * @param   orientation
     *          The heading of the newly created Ship.
     */
    public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius, double orientation,
                           double mass) throws ModelException {
       try{
           return new Ship(x, y, xVelocity, yVelocity, orientation, radius,mass);
       }catch(IllegalArgumentException e) {
           throw new ModelException(e);
       }
    }

    /**
     * Terminate the given ship.
     */
    public void terminateShip(Ship ship) throws ModelException{
        ship.terminate();
    }

    /**
     * Check whether the given ship is terminated.
     */
    public boolean isTerminatedShip(Ship ship) throws ModelException{
        return ship.checkTermination();
    }

    /**
     * Return the total mass of the given ship.
     */
    public double getShipMass(Ship ship) throws ModelException{
        return ship.getTotalMass();
    }

    /**
     * Return the world of the given ship.
     */
    public World getShipWorld(Ship ship) throws ModelException{
        return ship.getWorld();
    }

    /**
     * Return whether the given ship's thruster is active.
     */
    public boolean isShipThrusterActive(Ship ship) throws ModelException{
        return ship.getThrusterState();
    }

    /**
     * Enables or disables the given ship's thruster depending on the value
     * of the parameter active.
     */
    public void setThrusterActive(Ship ship, boolean active) throws ModelException{
        if(active){
            ship.thrustOn();
        }else{
            ship.thrustOff();
        }
    }

    /**
     * Return the acceleration of the given ship.
     */
    public double getShipAcceleration(Ship ship) throws ModelException{
        return ship.getAcceleration();
    }

    /**
     * Returns an array of doubles of the ship's current position.
     */
    public double[] getShipPosition(Ship ship){
        return ship.getPosition().getValues();
    }

    /**
     * Returns an array of doubles of the ship's current velocity.
     */
    public double[] getShipVelocity(Ship ship){
        return ship.getVelocity().getValues();
    }

    /**
     * Returns the ship's radius.
     */
    public double getShipRadius(Ship ship){
        return ship.getRadius();
    }

    /**
     * Returns the ship's current heading.
     */
    public double getShipOrientation(Ship ship) {
        return ship.getHeading();
    }

    /**
     * Changes the ships position by the current velocity * dt.
     *
     * @param   ship
     *          The ship that will be moved.
     *
     * @param   dt
     *          The difference in time between the new calculation and the previous.
     *
     * @throws  ModelException
     *          The time difference is invalid.
     *          | dt < 0
     */
    @Override @Deprecated
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
     * @param   ship
     *          The ship who's speed will be changed.
     *
     * @param   amount
     *          The given factor by which the speed will be increased.
     */
    @Override @Deprecated
    public void thrust(Ship ship, double amount){

    }

    /********
     * BULLET
     *******/

    /**
     * Create a new non-null bullet with the given position, velocity and
     * radius,
     *
     * The bullet is not located in a world nor loaded on a ship.
     */
    public Bullet createBullet(double x, double y, double xVelocity, double yVelocity, double radius)
            throws ModelException{
        try{
            return new Bullet(x, y, xVelocity, yVelocity, radius);
        }catch(IllegalArgumentException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Terminate bullet.
     */
    public void terminateBullet(Bullet bullet) throws ModelException{
        bullet.terminate();
    }

    /**
     * Check whether bullet is terminated.
     */
    public boolean isTerminatedBullet(Bullet bullet) throws ModelException{
        return bullet.checkTermination();
    }

    /**
     * Return the position of a given bullet as an array containing the
     * x-coordinate, followed by the y-coordinate.
     */
    public double[] getBulletPosition(Bullet bullet) throws ModelException{
        return bullet.getPosition().getValues();
    }

    /**
     * Return the velocity of a given bullet as an array containing the
     * velocity along the X-axis, followed by the velocity along the Y-axis.
     */
    public double[] getBulletVelocity(Bullet bullet) throws ModelException{
        return bullet.getVelocity().getValues();
    }

    /**
     * Return the radius of a given bullet.
     */
    public double getBulletRadius(Bullet bullet) throws ModelException{
        return bullet.getRadius();
    }

    /**
     * Return the mass of a given bullet.
     */
    public double getBulletMass(Bullet bullet) throws ModelException{
        return bullet.getMassOfEntity();
    }

    /**
     * Return the world in which the given bullet is positioned.
     *
     * This method must return null if a bullet is not positioned in a world, or
     * if it is positioned on a ship.
     */
    public World getBulletWorld(Bullet bullet) throws ModelException{
        return bullet.getWorld();
    }

    /**
     * Return the ship in which the given bullet is positioned.
     *
     * This method must return null if a bullet is not positioned on a ship.
     */
    public Ship getBulletShip(Bullet bullet) throws ModelException{
        if (bullet.getWorld() == null)
            throw new ModelException("The bullet is not part of any world");
        if (bullet.getPosition() == bullet.getSource().getPosition())
            return bullet.getSource();
        else
            return null;
    }

    /**
     * Return the ship that fired the given bullet.
     */
    public Ship getBulletSource(Bullet bullet) throws ModelException{
        if (bullet.getPosition() == bullet.getSource().getPosition())
            throw new ModelException("The bullet hasn't been fires");
        else
            return bullet.getSource();
    }

    /*******
     * WORLD
     ******/
    /**
     * Create a new world with the given width and height.
     */
    public World createWorld(double width, double height) throws ModelException{
        return new World(width,height);
    }

    /**
     * Terminate the given world>.
     */
    public void terminateWorld(World world) throws ModelException{
        world.terminate();
    }

    /**
     * Check whether the given world is terminated.
     */
    public boolean isTerminatedWorld(World world) throws ModelException{
        return world.checkTermination();
    }

    /**
     * Return the size of the given world as an array containing the width,
     * followed by the height.
     */
    public double[] getWorldSize(World world) throws ModelException{
        return world.getWorldSize();
    }

    /**
     * Return all ships located within the given world.
     */
    public Set<? extends Ship> getWorldShips(World world) throws ModelException{
        return world.getAllShips();
    }

    /**
     * Return all bullets located in the given world.
     */
    public Set<? extends Bullet> getWorldBullets(World world) throws ModelException{
        return world.getAllBullets();
    }

    /**
     * Add the given ship to the given world.
     */
    public void addShipToWorld(World world, Ship ship) throws ModelException{
        world.addEntity(ship);
    }

    /**
     * Remove the given ship from the given world.
     */
    public void removeShipFromWorld(World world, Ship ship) throws ModelException{
        world.removeEntity(ship);
    }

    /**
     * Add bullet to world.
     */
    public void addBulletToWorld(World world, Bullet bullet) throws ModelException{
        world.addEntity(bullet);
    }

    /**
     * Remove ship from world.
     */
    public void removeBulletFromWorld(World world, Bullet bullet) throws ModelException{
        world.removeEntity(bullet);
    }

    /*****************
     * LOADING BULLETS
     ****************/
    /**
     * Return the set of all bullets loaded on the given ship.
     *
     * For students working alone, this method may return null.
     */
    public Set<? extends Bullet> getBulletsOnShip(Ship ship) throws ModelException{
        return ship.getBullets();
    }

    /**
     * Return the number of bullets loaded on the given ship.
     */
    public int getNbBulletsOnShip(Ship ship) throws ModelException{
        return ship.getBullets().size();
    }

    /**
     * Load a given bullet on the given ship.
     */
    public void loadBulletOnShip(Ship ship, Bullet bullet) throws ModelException{
        try {
            ship.reload(bullet);
        }catch (IllegalArgumentException e) {
            throw new ModelException("Bullet and Spaceship don't match");
        }
    }

    /**
     * Load a collection of bullets on the given ship.
     */
    public void loadBulletsOnShip(Ship ship, Collection<Bullet> bullets) throws ModelException{
        try {
            ship.reload(bullets);
        }catch (IllegalArgumentException e) {
            throw new ModelException("Bullet and Spaceship don't match");
        }
    }

    /**
     * Remove a given bullet from a given ship.
     */
    public void removeBulletFromShip(Ship ship, Bullet bullet) throws ModelException{
        ship.getBullets().remove(bullet);
    }

    /**
     * The given ship fires a bullet.
     */
    public void fireBullet(Ship ship) throws ModelException{
        ship.fire();
    }

    /************
     * COLLISIONS
     ***********/

    /**
     * Return the shortest time in which the given entity will collide with the
     * boundaries of its world.
     */
    public double getTimeCollisionBoundary(Object object) throws ModelException{
        Entity entity = (Entity) object;
        return entity.getTimeToCollisionWithBoundary();
    }

    /**
     * Return the first position at which the given entity will collide with the
     * boundaries of its world.
     */
    public double[] getPositionCollisionBoundary(Object object) throws ModelException{
        Entity entity = (Entity) object;
        Vector positionOfCollision =  entity.getCollisionPositionWithBoundary();
        return positionOfCollision == null ? null : positionOfCollision.getValues();
    }

    /**
     * Return the shortest time in which the first entity will collide with the
     * second entity.
     */
    public double getTimeCollisionEntity(Object entity1, Object entity2) throws ModelException{
        Entity entityA = (Entity) entity1;
        Entity entityB = (Entity) entity2;
        return entityA.getTimeToCollision(entityB);
    }

    /**
     * Return the first position at which the first entity will collide with the
     * second entity.
     */
    public double[] getPositionCollisionEntity(Object entity1, Object entity2) throws ModelException{
        Entity entityA = (Entity) entity1;
        Entity entityB = (Entity) entity2;
        Vector positionOfCollision =  entityA.getCollisionPosition(entityB);
        return positionOfCollision == null ? null : positionOfCollision.getValues();
    }

    /**
     * Return the time that must pass before a boundary collision or an entity
     * collision will take place in the given world. Positive Infinity is
     * returned if no collision will occur.
     */
    public double getTimeNextCollision(World world) throws ModelException{
        return world.getTimeToFirstCollision();
    }

    /**
     * Return the position of the first boundary collision or entity collision
     * that will take place in the given world. Null is returned if no collision
     * will occur.
     */
    public double[] getPositionNextCollision(World world) throws ModelException{
        Vector positionOfCollision =  world.getFirstCollisionPosition();
        return positionOfCollision == null ? null : positionOfCollision.getValues();
    }
    /**
     * Advance the given world by dt seconds.
     */
    public void evolve(World world, double dt, CollisionListener collisionListener) throws ModelException{
        world.evolve(dt);
    }

    /**
     * Return the entity at the given <code>position</code> in the given
     * <code>world</code>.
     */
    public Object getEntityAt(World world, double x, double y) throws ModelException{
        return world.getEntityAt(new Vector(x,y));
    }

    /**
     * Return a set of all the entities in the given world.
     */
    public Set<? extends Object> getEntities(World world) throws ModelException{
        return world.getAllEntities();
    }

    /**
     * Changes the heading of the ship by the given angle.
     *
     * @param   ship
     *          The ship of which the heading will be changed.
     *
     * @param   angle
     *          The angle by which the ship's heading will be changed.
     */
    public void turn(Ship ship, double angle){
        ship.turn(angle);
    }

    /**
     * Returns the distance between the two given ships.
     *
     * @throws  ModelException
     *          The second ship is not a valid ship
     *          | ship2 == null
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
     * @throws  ModelException
     *          The ships do not overlap.
     *          | ship1.getDistanceBetween(ship2) >= ship1.getRadius() + ship2.getRadius()
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
     * @throws  ModelException
     *          The ships will not collide.
     *          | ship1.willCollide(ship2) == false
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
     * @throws  ModelException
     *          The ships will not collide.
     *          | ship1.willCollide(ship2) == false
     */
    public double[] getCollisionPosition(Ship ship1, Ship ship2) throws ModelException {
        try {
            Vector collisionPosition = ship1.getCollisionPosition(ship2);
            return collisionPosition == null ? null : collisionPosition.getValues();
        } catch (IllegalArgumentException e) {
            throw new ModelException(e);
        }
    }
}
