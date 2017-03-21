package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

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

    public boolean isTerminated = false;

    public void terminate(){
        this.isTerminated = true;
        for (Entity entity : getAllEntities()){
            removeEntity(entity);
        }
    }

    public Set<Ship> allShips;
    public Set<Bullet> allBullets;


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
        else{
            entity.setWorld(this);
            if (entity instanceof Ship)
                allShips.add((Ship)entity);
            if (entity instanceof Bullet)
                allBullets.add((Bullet)entity);

        }

    }

    public void removeEntity(Entity entity) throws IllegalArgumentException {
        if (entity == null)
            throw new IllegalArgumentException("Not an existing entity!");
        else{
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

    public Entity getEntityAt(Vector position) {
        return null;
    }


    public void evolve(double timeDifference) {
        if (timeDifference <= getTimeToFirstCollision()){ //No collision in the given time.
            for (Entity entity : getAllEntities()) {
                entity.move(timeDifference);
                if (entity instanceof Ship)
                    if (((Ship) entity).getThrusterState())
                        ((Ship) entity).thrust(timeDifference);
            }

        }
        else{
            evolve(getTimeToFirstCollision());

            if (getTimeToFirstBoundaryCollision() < getTimeToFirstEntityCollision()){ //Boundary collision
                String boundary = firstEntityToCollideBoundary.getBoundaryOfCollision();
                if (boundary.equals("L") || boundary.equals("R")) {
                    firstEntityToCollideBoundary.negateVelocityX();
                    if (firstEntityToCollideBoundary instanceof Bullet){
                        ((Bullet) firstEntityToCollideBoundary).riseNbOfBounces();
                        if (((Bullet) firstEntityToCollideBoundary).getNbOfBounces() >= ((Bullet) firstEntityToCollideBoundary).getMaxNbBounces() )
                            (firstEntityToCollideBoundary).terminate();
                    }
                }
                if (boundary.equals("T") || boundary.equals("B")) {
                    firstEntityToCollideBoundary.negateVelocityY();
                    if (firstEntityToCollideBoundary instanceof Bullet){
                        ((Bullet) firstEntityToCollideBoundary).riseNbOfBounces();
                        if (((Bullet) firstEntityToCollideBoundary).getNbOfBounces() >= ((Bullet) firstEntityToCollideBoundary).getMaxNbBounces() )
                            (firstEntityToCollideBoundary).terminate();
                    }
                }
            }

            else if (getTimeToFirstBoundaryCollision() > getTimeToFirstEntityCollision()){ //Entity collision
                resolveCollision(firstEntityPairToCollide);
            }

            evolve(timeDifference - getTimeToFirstCollision());

        }
    }


    public void resolveCollision(List<Entity> entityPair) {
        Entity entity1 = entityPair.get(0);
        Entity entity2 = entityPair.get(1);
        if (entity1 instanceof Ship && entity2 instanceof Ship){
            Ship ship1 = (Ship)entityPair.get(0);
            Ship ship2 = (Ship)entityPair.get(1);

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
        if (bullet.getSource() == ship){
//            bullet.loadOnShip(ship,ship.getPosition());
            ship.reloadSingleBullet(bullet);
        }
        else{
            ship.terminate();
            bullet.terminate();
        }
    }

    public Entity firstEntityToCollideBoundary;
    public List<Entity> firstEntityPairToCollide;

    public double getTimeToFirstEntityCollision(){
        double timeToFirstCollision = Double.POSITIVE_INFINITY;
        List<Entity> allEntities = new ArrayList<>();
        allEntities.addAll(getAllBullets());
        allEntities.addAll(getAllShips());
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

    public double J(List<Ship> shipPair) {
        Ship ship1 = shipPair.get(0);
        Ship ship2 = shipPair.get(1);
        return 2*ship1.getTotalMass()*ship2.getTotalMass()*
                ship1.deltaV(ship2).scalarProduct(ship1.deltaR(ship2))
                / ( ship1.sigma(ship2) * (ship1.getTotalMass()+ship2.getTotalMass()));
    }
    public double Jx(List<Ship> shipPair) {
        Ship ship1 = shipPair.get(0);
        Ship ship2 = shipPair.get(1);
        return J(shipPair)/ship1.sigma(ship2) * ship1.deltaR(ship2).getX();
    }

    public double Jy(List<Ship> shipPair) {
        Ship ship1 = shipPair.get(0);
        Ship ship2 = shipPair.get(1);
        return J(shipPair)/ship1.sigma(ship2) * ship1.deltaR(ship2).getY();
    }
}
