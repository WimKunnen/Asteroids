package asteroids.model;

import be.kuleuven.cs.som.annotate.*;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.*;

/**
 *
 * @invar The width and height of world always lie between the lowerbound and upperbound.
 *        | (lowerbound <= width && width <= upperbound
 *        |     && lowerbound <= height && height <= upperbound)
 *
 * @author Maarten Doclo and Wim Kunnen
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
        if (width < lowerBound)
            this.width = lowerBound;
        else if (width > upperBound)
            this.width = upperBound;
        else
            this.width = width;

        if (height < lowerBound)
            this.height = lowerBound;
        else if (height > upperBound)
            this.height = upperBound;
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
    public boolean isTerminated = false;

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
        for (Entity entity : getAllEntities()){
            removeEntity(entity);
        }
    }

    /**
     * Variable registering all ships in the world as a set.
     */
    public HashSet<Ship> allShips = new HashSet<>();
    /**
     * Variable registering all bullets in the world as a set.
     */
    public HashSet<Bullet> allBullets = new HashSet<>();

    /**
     * Variable registering all entities in the world as a hashmap, with the entity's position as a key.
     */
    public HashMap<Vector, Entity> entityPositionMap = new HashMap<>();

    /**
     * Variable registering the lower bound of the values of width and height.
     */
    public final static double lowerBound = 0;

    /**
     * Variable registering the upper bound of the values of width and height.
     */
    public static double upperBound = Double.MAX_VALUE;

    /**
     * Return a set with all entities in this world.
     * @see implementation
     */
    public Set<Entity> getAllEntities() {
        Set<Entity> allEntities = new HashSet<>();
        allEntities.addAll(getAllBullets());
        allEntities.addAll(getAllShips());
        return allEntities;
    }
    /**
     * Return a set of all ships in this world.
     * @see implementation
     */
    public Set<Ship> getAllShips() {
        return this.allShips;
    }
    /**
     * Return a set of all bullets in this world.
     * @see implementation
     */
    public Set<Bullet> getAllBullets() {
        return this.allBullets;
    }

    /**
     * Add an entity to this world.
     * @see implementation
     */
    public void addEntity(Entity entity) throws IllegalArgumentException {
        if (entity == null)
            throw new IllegalArgumentException("Not an existing entity!");
        if (entity.noOverlapsInNewWorld(this) && entity.fitsInBoundaries(this)) {
            entity.setWorld(this);
            entityPositionMap.put(entity.getPosition(),entity);
            if (entity instanceof Ship) {
                allShips.add((Ship) entity);
            } else if (entity instanceof Bullet && !(((Bullet) entity).hasBeenOutOfShip())) {
                allBullets.add((Bullet) entity);
            }
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

            if (!getAllEntities().contains(entity)){
                throw new IllegalArgumentException("Entity not in this world!");
            }

            if (entity instanceof Ship) {
                if (!allShips.contains(entity))
                    throw new IllegalArgumentException();
                allShips.remove(entity);
            }
            else if (entity instanceof Bullet){
                if (!allBullets.contains(entity))
                    throw new IllegalArgumentException();
                allBullets.remove(entity);
            }
            entity.setWorld(null);
        }
    }


    /**
     * Get the entity at a given position
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
     * Evolve the world a given time difference.
     *
     */
    public void evolve (double timeDifference) throws IllegalArgumentException {
        if (timeDifference > 0) {
            if (timeDifference <= getTimeToFirstCollision()) { //No collision in the given time.
                for (Entity entity : getAllEntities()) {
                    entity.move(timeDifference);
                    updatePositionMap();
                    if (entity instanceof Ship)
                        if (((Ship) entity).getThrusterState())
                            ((Ship) entity).thrust(timeDifference);
                    if (entity instanceof Bullet && !((Bullet) entity).hasBeenOutOfShip())
                        ((Bullet) entity).switchBeenOutOfShip(true);
                }

            } else {
                double timeToFirstCollision = getTimeToFirstCollision();
                evolve(timeToFirstCollision);

                List<Entity> allEntities = new ArrayList<>();
                allEntities.addAll(getAllEntities());
                for (int i = 0; i < allEntities.size(); i++) {

                    Entity currentEntity = allEntities.get(i);

                    for (int k = i + 1; k < allEntities.size(); k++) { //Entity collision resolve
                        Entity otherEntity = allEntities.get(k);
                        if (currentEntity.apparentlyCollidesWithEntity(otherEntity) && !currentEntity.fliesApartFrom(otherEntity)) {
                            resolveEntityCollision(currentEntity, otherEntity);
                        }
                    }

                    if (currentEntity.apparentlyCollidesWithBoundary()) { //Boundary collision resolve
                        if ((currentEntity.apparentlyCollidesWithLeft() && currentEntity.getVelocity().getX() < 0) ||
                                (currentEntity.apparentlyCollidesWithRight() && currentEntity.getVelocity().getX() > 0)) {
                            currentEntity.negateVelocityX();
                            if (currentEntity instanceof Bullet) {
                                ((Bullet) currentEntity).riseNbOfBounces();
                                if (((Bullet) currentEntity).getNbOfBounces() >= ((Bullet) currentEntity).getMaxNbBounces()) {
                                    (currentEntity).terminate();
                                }
                            }
                        }

                        if ((currentEntity.apparentlyCollidesWithBottom() && currentEntity.getVelocity().getY() < 0) ||
                                (currentEntity.apparentlyCollidesWithTop() && currentEntity.getVelocity().getY() > 0)) {
                            currentEntity.negateVelocityY();
                            if (currentEntity instanceof Bullet) {
                                ((Bullet) currentEntity).riseNbOfBounces();
                                if (((Bullet) currentEntity).getNbOfBounces() >= ((Bullet) currentEntity).getMaxNbBounces()) {
                                    (currentEntity).terminate();
                                }
                            }
                        }
                    }
                }
                if (getTimeToFirstCollision() <=0){ //TODO
                    throw new IllegalArgumentException();
                }
                evolve(timeDifference - timeToFirstCollision);

            }
        }
        else{
            throw new IllegalArgumentException();
        }
    }


    /**
     * Resolve the collision between a two ships.
     * @param entity1 one colliding ship
     * @param entity2 other colliding ship
     */
    public void resolveEntityCollision(Entity entity1, Entity entity2) {
        if (entity1 instanceof Ship && entity2 instanceof Ship){
            Ship ship1 = (Ship)entity1;
            Ship ship2 = (Ship)entity2;

            List<Ship> shipPair = new ArrayList<>();

            shipPair.add(ship1);
            shipPair.add(ship2);
            Vector velocity1 = new Vector(entity1.getVelocity().getX() +
                    Jx(shipPair)/ship1.getTotalMass(),
                    entity1.getVelocity().getY() + Jy(shipPair)/ship1.getTotalMass());
            Vector velocity2 = new Vector(entity2.getVelocity().getX() -
                    Jx(shipPair)/ship2.getTotalMass(),
                    entity2.getVelocity().getY() - Jy(shipPair)/ship2.getTotalMass());

            entity1.setVelocity(velocity1);
            entity2.setVelocity(velocity2);
        }

        else if (entity1 instanceof Bullet && entity2 instanceof Ship)
            resolveBulletShipCollision((Ship) entity2,(Bullet) entity1);
        else if (entity1 instanceof Ship && entity2 instanceof Bullet)
            resolveBulletShipCollision((Ship) entity1,(Bullet) entity2);
        else if (entity1 instanceof Bullet && entity2 instanceof Bullet && ((Bullet) entity1).hasBeenOutOfShip() && ((Bullet) entity2).hasBeenOutOfShip()){
            entity1.terminate();
            entity2.terminate();
        }


    }
//TODO

    /**
     * Resolve the collision between a bullet and a ship.
     * @param ship the colliding ship
     * @param bullet the colliding bullet
     */
    public void resolveBulletShipCollision(Ship ship, Bullet bullet){
        if (bullet.getSource() == ship && bullet.hasBeenOutOfShip()){
            ship.reload(bullet);
        }

        else if (bullet.getSource() != ship) {
            ship.terminate();
            bullet.terminate();
        }
    }

    public Entity firstEntityToCollideBoundary;
    public List<Entity> firstEntityPairToCollide = new ArrayList<>();


    /**
     * Return the time to the first collision between two entities.
     * @return
     */
    public double getTimeToFirstEntityCollision(){
        double timeToFirstCollision = Double.POSITIVE_INFINITY;
        List<Entity> allEntities = new ArrayList<>();
        allEntities.addAll(getAllEntities());
        for (int i = 0; i < allEntities.size(); i++){
            for (int k = i+1; k < allEntities.size(); k++){
                double newTime = allEntities.get(i).getTimeToCollision(allEntities.get(k));
                if (newTime < timeToFirstCollision) {
                    timeToFirstCollision = newTime;
                    firstEntityPairToCollide.clear();
                    firstEntityPairToCollide.add(allEntities.get(i));
                    firstEntityPairToCollide.add(allEntities.get(k));
                }
            }
        }

        return timeToFirstCollision;
    }

    /**
     * Return the time to the first collision between an entity and a boundary of the world.
     * @return
     */
    public double getTimeToFirstBoundaryCollision() {
        double timeToFirstCollision = Double.POSITIVE_INFINITY;
        List<Entity> allEntities = new ArrayList<>();
        allEntities.addAll(getAllEntities());
        for (int i = 0; i < allEntities.size(); i++) {
            double newTime = allEntities.get(i).getTimeToCollisionWithBoundary();
            if (newTime < timeToFirstCollision) {
                timeToFirstCollision = newTime;
                firstEntityToCollideBoundary = allEntities.get(i);
            }
        }
        return timeToFirstCollision;
    }

    /**
     * | return (Math.min(getTimeToFirstBoundaryCollision,getTimeToFirstEntityCollision))
     */
    public double getTimeToFirstCollision() {
        double time = getTimeToFirstBoundaryCollision();
        double entityTime = getTimeToFirstEntityCollision();
        if (entityTime < time)
            time = entityTime;

        return time;
    }

    //TODO
    /**
     * @see implementation
     */
    public Vector getFirstCollisionPosition(){

        double timeToFirstEntityCollision = getTimeToFirstEntityCollision();
        double timeToFirstBoundaryCollision = getTimeToFirstBoundaryCollision();

        if (timeToFirstBoundaryCollision == Double.POSITIVE_INFINITY
                && timeToFirstEntityCollision == Double.POSITIVE_INFINITY){
            return null;
        }

        if (timeToFirstBoundaryCollision < timeToFirstEntityCollision){
            return firstEntityToCollideBoundary.getCollisionPositionWithBoundary();
        }

        if (timeToFirstBoundaryCollision > timeToFirstEntityCollision){
            return firstEntityPairToCollide.get(0).getCollisionPosition(firstEntityPairToCollide.get(1));
        }

        throw new AssertionError();
    }

    /**
     * J as defined in the assignment.
     * @see implementation
     */
    public double J(List<Ship> shipPair) {
        Ship ship1 = shipPair.get(0);
        Ship ship2 = shipPair.get(1);
        return 2 * ship1.getTotalMass() * ship2.getTotalMass() *
                ship1.deltaV(ship2).scalarProduct(ship1.deltaR(ship2))
                / ( ship1.sigma(ship2) * (ship1.getTotalMass() + ship2.getTotalMass()));
    }

    /**
     * J_x as defined in the assignment.
     * @see implementation
     */
    public double Jx(List<Ship> shipPair) {
        Ship ship1 = shipPair.get(0);
        Ship ship2 = shipPair.get(1);
        return J(shipPair) * ship1.deltaR(ship2).getX() / ship1.sigma(ship2);
    }

    /**
     * J_y as defined in the assignment.
     * @see implementation
     */
    public double Jy(List<Ship> shipPair) {
        Ship ship1 = shipPair.get(0);
        Ship ship2 = shipPair.get(1);
        return J(shipPair) * ship1.deltaR(ship2).getY() / ship1.sigma(ship2);
    }
}
