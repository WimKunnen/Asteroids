package asteroids.model;

import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.annotate.Immutable;
import jdk.nashorn.internal.ir.annotations.*;

import java.util.*;

/**
 *
 * @author Maarten Doclo and Wim Kunnen
 */
public class World {

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

    }

    public World(){
        this.height = 800;
        this.width = 800;
    }

    private final double width;
    @Basic @Immutable
    public double getWidth(){return this.width;}

    private final double height;
    @Basic @Immutable
    public double getHeight() {return this.height;}

    private final double[] worldSize = new double[]{this.getWidth(), this.getHeight()};
    @Basic @Immutable
    public double[] getWorldSize(){
        return this.worldSize;
    }

    public boolean isTerminated = false;
    public boolean checkTermination(){
        return this.isTerminated;
    }
    public void terminate(){
        this.isTerminated = true;
        for (Entity entity : getAllEntities()){
            removeEntity(entity);
        }
    }

    public HashSet<Ship> allShips = new HashSet<>();
    public HashSet<Bullet> allBullets = new HashSet<>();

    public HashMap<Vector, Entity> entityPositionMap = new HashMap<>();

    public final static double lowerBound = 0;
    public static double upperBound = Double.MAX_VALUE;

    public Set<Entity> getAllEntities() {
        Set<Entity> allEntities = new HashSet<>();
        allEntities.addAll(getAllBullets());
        allEntities.addAll(getAllShips());
        return allEntities;
    }
    public Set<Ship> getAllShips() {return this.allShips;}
    public Set<Bullet> getAllBullets() {return this.allBullets;}

    public void addEntity(Entity entity) throws IllegalArgumentException {
        if (entity == null)
            throw new IllegalArgumentException("Not an existing entity!");
        if (entity.noOverlapsInNewWorld(this) && entity.fitsInBoundaries(this)) {
            entity.setWorld(this);
            entityPositionMap.put(entity.getPosition(),entity);
            if (entity instanceof Ship) {
                getAllShips().add((Ship) entity);
            } else if (entity instanceof Bullet && !(((Bullet) entity).hasBeenOutOfShip())) {
                getAllBullets().add((Bullet) entity);
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

    public void removeEntity(Entity entity) throws IllegalArgumentException {
        if (entity == null)
            throw new IllegalArgumentException("Not an existing entity!");
        else{

            if (entityPositionMap.containsValue(entity)){
                Vector keyPosition = entity.getPosition();
                entityPositionMap.remove(keyPosition);
            }

//            else{
//                throw new IllegalArgumentException("Entity not in this world!");
//            }

            if (entity instanceof Ship) {
                if (!allShips.contains(entity))
                    throw new IllegalArgumentException();
                allShips.remove(entity);
            }
            if (entity instanceof Bullet){
                if (!allBullets.contains(entity))
                    throw new IllegalArgumentException();
                allBullets.remove(entity);
            }
            entity.setWorld(null);
        }
    }

    //TODO problem: when two entities have the same position and after some time they don't... only one entity is remembered.

    public Entity getEntityAt(Vector position){
        return entityPositionMap.get(position);
    }

    public void updatePositionMap() {
        entityPositionMap.clear();
        Set<Entity> allEntities = getAllEntities();
        for (Entity entity : allEntities){
            entityPositionMap.put(entity.getPosition(),entity);
        }
    }

    public void evolve(double timeDifference) {
        //System.out.println(getAllBullets().size());
        if (timeDifference <= getTimeToFirstCollision()){ //No collision in the given time.
            for (Entity entity : getAllEntities()) {
                entity.move(timeDifference);
                updatePositionMap();
                if (entity instanceof Ship)
                    if (((Ship) entity).getThrusterState())
                        ((Ship) entity).thrust(timeDifference);

            }

        }
        else {
            double timeToFirstCollision = getTimeToFirstCollision();
            evolve(timeToFirstCollision);

            List<Entity> allEntities = new ArrayList<>();
            allEntities.addAll(getAllEntities());
            for (int i = 0; i < allEntities.size(); i++) {

                Entity currentEntity = allEntities.get(i);

                for (int k = i + 1; k < allEntities.size(); k++) { //Entity collision resolve
                    Entity otherEntity = allEntities.get(k);
                    if (currentEntity.apparentlyCollidesWithEntity(otherEntity)) {
                        resolveCollision(currentEntity, otherEntity);
                    }
                }
            }
            for (int i = 0; i < allEntities.size(); i++) {

                Entity currentEntity = allEntities.get(i);

                if (currentEntity.apparentlyCollidesWithBoundary()){ //Boundary collision resolve
                    if (currentEntity.apparentlyCollidesWithLeft() || currentEntity.apparentlyCollidesWithRight()){
                        currentEntity.negateVelocityX();
                        if (currentEntity instanceof Bullet) {
                            ((Bullet) currentEntity).riseNbOfBounces();
                            if (((Bullet) currentEntity).getNbOfBounces() >= ((Bullet) currentEntity).getMaxNbBounces())
                                (currentEntity).terminate();
                        }
                    }

                    if (currentEntity.apparentlyCollidesWithBottom() || currentEntity.apparentlyCollidesWithTop()){
                        currentEntity.negateVelocityY();
                        if (currentEntity instanceof Bullet) {
                            ((Bullet) currentEntity).riseNbOfBounces();
                            if (((Bullet) currentEntity).getNbOfBounces() >= ((Bullet) currentEntity).getMaxNbBounces())
                                (currentEntity).terminate();
                        }
                    }
                }
            }
            if (timeDifference - timeToFirstCollision < 0){System.out.println("NEGATIVEEEEEEEEEE");}
            System.out.println(timeDifference - timeToFirstCollision + "     " + timeToFirstCollision);
            evolve(timeDifference - timeToFirstCollision);

        }
    }

//TODO check method with firing bullets (List usage)
    public void resolveCollision(Entity entity1, Entity entity2) {
        if (entity1 instanceof Ship && entity2 instanceof Ship){
            Ship ship1 = (Ship)entity1;
            Ship ship2 = (Ship)entity2;

            List<Ship> shipPair = new ArrayList<>();

            shipPair.add(ship1);shipPair.add(ship2);
            entity1.setVelocity(new Vector(entity1.getVelocity().getX() +
                    Jx(shipPair)/ship1.getTotalMass(),
                    entity1.getVelocity().getY() + Jy(shipPair)/ship1.getTotalMass()));
            entity2.setVelocity(new Vector(entity2.getVelocity().getX() -
                    Jx(shipPair)/ship2.getTotalMass(),
                    entity2.getVelocity().getY() - Jy(shipPair)/ship2.getTotalMass()));
        }

        else if (entity1 instanceof Bullet && entity2 instanceof Ship)
            resolveBulletShipCollision((Ship) entity2,(Bullet) entity1);
        else if (entity1 instanceof Ship && entity2 instanceof Bullet)
            resolveBulletShipCollision((Ship) entity1,(Bullet) entity2);
        else if (entity1 instanceof Bullet && entity2 instanceof Bullet){
            entity1.terminate();
            entity2.terminate();
        }


    }
//TODO
    public void resolveBulletShipCollision(Ship ship,Bullet bullet){
        if (bullet.getSource() == ship && bullet.hasBeenOutOfShip()){
            ship.reload(bullet);
        }
        if (bullet.getSource() != ship) {
            ship.terminate();
            bullet.terminate();
        }
    }

    public Entity firstEntityToCollideBoundary;
    public List<Entity> firstEntityPairToCollide = new ArrayList<>();

    //TODO Use getAllEntities() instead of adding to a new array! Done? Done!
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

    public double getTimeToFirstBoundaryCollision() {
        double timeToFirstCollision = Double.POSITIVE_INFINITY;
        List<Entity> allEntities = new ArrayList<>();
        allEntities.addAll(getAllBullets());
        allEntities.addAll(getAllShips());
        for (int i = 0; i < allEntities.size(); i++) {
            double newTime = allEntities.get(i).getTimeToCollisionWithBoundary();
            if (newTime < timeToFirstCollision) {
                timeToFirstCollision = newTime;
                firstEntityToCollideBoundary = allEntities.get(i);
            }
        }
        return timeToFirstCollision;
    }

    public double getTimeToFirstCollision() {
        double time = getTimeToFirstBoundaryCollision();
        double entityTime = getTimeToFirstEntityCollision();
        if (entityTime < time)
            time = entityTime;

        return time;
    }

    //TODO
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

        return null; //TODO can this differently?
    }

    public double J(List<Ship> shipPair) {
        Ship ship1 = shipPair.get(0);
        Ship ship2 = shipPair.get(1);
        return 2 * ship1.getTotalMass() * ship2.getTotalMass() *
                ship1.deltaV(ship2).scalarProduct(ship1.deltaR(ship2))
                / ( ship1.sigma(ship2) * (ship1.getTotalMass() + ship2.getTotalMass()));
    }
    public double Jx(List<Ship> shipPair) {
        Ship ship1 = shipPair.get(0);
        Ship ship2 = shipPair.get(1);
        return J(shipPair) * ship1.deltaR(ship2).getX() / ship1.sigma(ship2);
    }

    public double Jy(List<Ship> shipPair) {
        Ship ship1 = shipPair.get(0);
        Ship ship2 = shipPair.get(1);
        return J(shipPair) * ship1.deltaR(ship2).getY() / ship1.sigma(ship2);
    }
}
