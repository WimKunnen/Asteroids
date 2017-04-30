package asteroids.model;

import asteroids.part2.CollisionListener;
import asteroids.part2.internal.Sound;
import be.kuleuven.cs.som.annotate.*;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.*;

/**
 *A class of two dimensional worlds.
 *
 * @invar The width and height of world always lie between the lowerbound and upperbound.
 *        | (lowerbound <= width && width <= upperbound
 *        |     && lowerbound <= height && height <= upperbound)
 *
 * @author Maarten Doclo and Wim Kunnen
 *
 * @version 1.0
 */
public class World {

    /**
     * Initializer for the World.
     *
     * @param   width
     *          The new width of the newly created world.
     *
     * @param   height
     *          The new width of the newly created world.
     */
    public World(double width, double height){
        if (width < getLowerBound())
            this.width = getLowerBound();
        else if (width > getUpperBound() || Double.isNaN(width))
            this.width = getUpperBound();
        else
            this.width = width;

        if (height < getLowerBound())
            this.height = getLowerBound();
        else if (height > getUpperBound() || Double.isNaN(height))
            this.height = getUpperBound();
        else
            this.height = height;
        this.worldSize = new double[]{this.getWidth(), this.getHeight()};
    }

    /**
     * Variable registering the width of a world.
     */
    private final double width;
    /**
     * @see implementation
     */
    @Basic @Immutable
    public double getWidth(){return this.width;}

    /**
     * Variable registering the height of a world.
     */
    private final double height;
    /**
     * @see implementation
     */
    @Basic @Immutable
    public double getHeight() {return this.height;}

    /**
     * Variable registering the worldsize as an array of doubles.
     */
    private final double[] worldSize;
    /**
     * Return the size of the world as an array of doubles.
     * @see implementation
     */
    @Basic @Immutable
    public double[] getWorldSize(){
        return this.worldSize;
    }

    /**
     * Variable registering whether or not a world is terminated.
     */
    private boolean isTerminated = false;

    /**
     * Return whether or not a world is terminated.
     * @see implementation
     */
    public boolean checkTermination(){
        return this.isTerminated;
    }
    /**
     * Terminate the world.
     * @see implementation
     */
    public void terminate(){
        this.isTerminated = true;
        Set<Entity> allEntities = new HashSet<>();
        allEntities.addAll(getAllEntities());
        for (Entity entity : allEntities){
            removeEntity(entity);
        }
    }

//    /**
//     * Variable registering all ships in the world as a set.
//     */
//    private HashSet<Ship> allShips = new HashSet<>();
//    /**
//     * Variable registering all bullets in the world as a set.
//     */
//    private HashSet<Bullet> allBullets = new HashSet<>();
//
//    /**
//     * Variable registering all minor planets in the world as a set.
//     */
//    private HashSet<MinorPlanet> allMinorPlanets = new HashSet<>();

    private HashSet<Entity> allEntities = new HashSet<>();

    /**
     * Variable registering all entities in the world as a hashmap, with the entity's position as a key.
     */
    public HashMap<Vector, Entity> entityPositionMap = new HashMap<>();

    /**
     * Variable registering the lower bound of the values of width and height.
     */
    private final static double lowerBound = 0;

    /**
     * Return the lower bound of the size of worlds.
     * @see implementation
     */
    public double getLowerBound(){
        return lowerBound;
    }

    /**
     * Variable registering the upper bound of the values of width and height.
     */
    private static double upperBound = Double.MAX_VALUE;

    /**
     * Return the upper bound of the size of worlds.
     * @see implementation
     */
    public double getUpperBound(){
        return upperBound;
    }

    /**
     * Return a set with all entities in this world.
     * @see implementation
     */
    public Set<Entity> getAllEntities() {
        return this.allEntities;
    }
    /**
     * Return a set of all ships in this world.
     * @see implementation
     */
    public Set<Ship> getAllShips() {
        HashSet<Ship> shipSet = new HashSet<>();
        for (Entity entity : this.getAllEntitiesOfType(Ship.class)){
            shipSet.add((Ship)entity);
        }
        return shipSet;
    }
    /**
     * Return a set of all bullets in this world.
     * @see implementation
     */
    public Set<Bullet> getAllBullets() {
        HashSet<Bullet> bulletSet = new HashSet<>();
        for (Entity entity : this.getAllEntitiesOfType(Bullet.class)){
            bulletSet.add((Bullet)entity);
        }
        return bulletSet;
    }

    /**
     * Return a set of all asteroids in this world.
     * @see implementation
     */
    public Set<Asteroid> getAllAsteroids() {
        Set<Entity> allEntitiesOfType =  this.getAllEntitiesOfType(Asteroid.class);
        Set<Asteroid> allAsteroids = new HashSet<>();
        for (Entity entity : allEntitiesOfType)
            allAsteroids.add((Asteroid)entity);
        return allAsteroids;
    }

    /**
     * Return a set of all planetoids in this world.
     * @see implementation
     */
    public Set<Planetoid> getAllPlanetoids(){
        Set<Entity> allEntitiesOfType =  this.getAllEntitiesOfType(Planetoid.class);
        Set<Planetoid> allAsteroids = new HashSet<>();
        for (Entity entity : allEntitiesOfType)
            allAsteroids.add((Planetoid)entity);
        return allAsteroids;
    }

    /**
     * Return a set of all entities from a given subclass of Entity in this world.
     * @see implementation
     */
    public Set<Entity> getAllEntitiesOfType(Class c){
        HashSet<Entity> allEntitiesFrom = new HashSet<>();
        for (Entity entity : getAllEntities()){
            if(c.isInstance(entity)){
                allEntitiesFrom.add(entity);
            }
        }
        return allEntitiesFrom;
    }

    /**
     * Add an entity to this world.
     *
     * @param entity
     *        The entity that needs to be added to this world.
     *
     * @see implementation
     */
    public void addEntity(Entity entity) throws IllegalArgumentException {
        if (entity == null)
            throw new IllegalArgumentException("Not an existing entity!");
        if (entity.noOverlapsInNewWorld(this) && entity.fitsInBoundaries(this)) {
            entity.setWorld(this);
            entityPositionMap.put(entity.getPosition(),entity);
            //if (!(entity instanceof Bullet) || !(((Bullet) entity).hasBeenOutOfShip())) {
                allEntities.add(entity);
            //}
//            if (entity instanceof Ship) {
//                allShips.add((Ship) entity);
//            } else if (entity instanceof Bullet && !(((Bullet) entity).hasBeenOutOfShip())) {
//                allBullets.add((Bullet) entity);
//            }
//            else if (entity instanceof MinorPlanet){
//                allMinorPlanets.add((MinorPlanet)entity);
//            }
        }
        else{
            if(entity instanceof Bullet){
                entity.terminate();
            }
            else
                throw new IllegalArgumentException("Can't be added!");
        }

    }

    /**
     * Remove an entity from this world.
     *
     * @param entity
     *        The entity that needs to be removed from this world.
     *
     * @see implementation
     */
    public void removeEntity(Entity entity) throws IllegalArgumentException {
        if (entity == null)
            throw new IllegalArgumentException("Not an existing entity!");
        else{
            if (entityPositionMap.containsValue(entity)){
                Vector keyPosition = entity.getPosition();
                entityPositionMap.remove(keyPosition);
            }

            else{
                throw new IllegalArgumentException("Entity not in this world!");
            }

            if (getAllEntities().contains(entity)){
                allEntities.remove(entity);
            }
            else{
                throw new IllegalArgumentException("Entity not in this world!");
            }
            entity.setWorld(null);
        }
    }


    /**
     * Get the entity at a given position
     *
     * @param position
     *        The vector position at which an entity might be located.
     *
     * @see implementation
     */
    public Entity getEntityAt(Vector position){
        return entityPositionMap.get(position); // returns null if no such key (total programming)
    }

    /**
     * Updates the map that holds the positions of all entities as keys.
     * @see implementation
     */
    public void updatePositionMap() {
        entityPositionMap.clear();
        Set<Entity> allEntities = getAllEntities();
        for (Entity entity : allEntities){
            entityPositionMap.put(entity.getPosition(),entity);
        }
    }

    /**
     * Evolve this world for a given timedifference.
     *
     * @param timeDifference
     *        The amount of seconds the world should evolve.
     */
    public void evolve (double timeDifference, CollisionListener collisionListener) throws IllegalArgumentException {
        if (timeDifference >= 0 || !(Double.isNaN(timeDifference))) {
            Collision collision = new Collision(this);
            if (timeDifference <= collision.getTimeToFirstCollision()) { //No collision in the given time.
                moveAllEntities(timeDifference);

            } else {
                double timeToFirstCollision = collision.getTimeToFirstCollision();
                evolve(timeToFirstCollision, collisionListener);

                List<Entity> allEntities = new ArrayList<>();
                allEntities.addAll(getAllEntities());

                for (int i = 0; i < allEntities.size(); i++) {

                    Entity currentEntity = allEntities.get(i);

                    for (int k = i + 1; k < allEntities.size(); k++) { //Entity collision resolve
                        Entity otherEntity = allEntities.get(k);
                        if (currentEntity.apparentlyCollidesWithEntity(otherEntity) && !currentEntity.fliesApartFrom(otherEntity)) {
                            collision.resolveEntityCollision(currentEntity, otherEntity);

                            if ((currentEntity instanceof Ship && otherEntity instanceof Bullet
                                    && ((Bullet) otherEntity).getSource() != currentEntity)||
                                    (currentEntity instanceof Bullet && otherEntity instanceof Ship
                                            && ((Bullet) currentEntity).getSource() != otherEntity)
                                    || (currentEntity instanceof Bullet && otherEntity instanceof  Bullet)
                                    ||(currentEntity instanceof MinorPlanet && otherEntity instanceof Bullet) ||
                                    (currentEntity instanceof Bullet && otherEntity instanceof MinorPlanet))
                            {
                                Vector collisionPosition = currentEntity.getCollisionPosition(otherEntity);
                                collisionListener.objectCollision(currentEntity, otherEntity, collisionPosition.getX(), collisionPosition.getY());
                            }
                        }
                    }

                    if (currentEntity.apparentlyCollidesWithBoundary()) { //Boundary collision resolve
                        collision.resolveBoundaryCollision(currentEntity);

                        if ( currentEntity instanceof Bullet && ((Bullet) currentEntity).getNbOfBounces() < 3 ) {
                            Vector collisionPosition = currentEntity.getCollisionPositionWithBoundary();
                            collisionListener.boundaryCollision(currentEntity, collisionPosition.getX(), collisionPosition.getY());
                        }
                        else if (currentEntity instanceof Ship){
                            Vector collisionPosition = currentEntity.getCollisionPositionWithBoundary();
                            collisionListener.boundaryCollision(currentEntity, collisionPosition.getX(), collisionPosition.getY());
                        }
                        else if (currentEntity instanceof MinorPlanet){
                            Vector collisionPosition = currentEntity.getCollisionPositionWithBoundary();
                            collisionListener.boundaryCollision(currentEntity, collisionPosition.getX(), collisionPosition.getY());
                        }
                    }
                }
                evolve(timeDifference - timeToFirstCollision, collisionListener);
            }
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    /**
     * Move all entities in this world for a given timeDifference.
     *
     * @param timeDifference How much time the entities need to move for. 
     */
    private void moveAllEntities(double timeDifference){
        Set<Entity> allEntities = new HashSet<>();
        allEntities.addAll(getAllEntities());
        Iterator<Entity> iter = allEntities.iterator();

        while (iter.hasNext()) {
            Entity entity = iter.next();
            entity.move(timeDifference);
            updatePositionMap();
            if (entity instanceof Ship)
                if (((Ship) entity).getThrusterState())
                    ((Ship) entity).thrust(timeDifference);
            if (entity instanceof Bullet && !((Bullet) entity).hasBeenOutOfShip())
                ((Bullet) entity).switchBeenOutOfShip(true);
        }
    }

    /**
     * Returns the time difference until the first collision in this world.
     *
     * @return  collision.getTimeToFirstCollision()
     */
    public double getTimeToFirstCollision(){
        Collision collision = new Collision(this);
        return collision.getTimeToFirstCollision();
    }

    /**
     * Returns the position of the first collision that will take place in this world.
     *
     * @return  collision.getFirstCollisionPosition()
     */
    public Vector getFirstCollisionPosition(){
        Collision collision = new Collision(this);
        return collision.getFirstCollisionPosition();
    }
}
