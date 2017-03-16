package asteroids.model;

import java.util.*;

/**
 * Created by maarten on 14/03/2017.
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
    public double getWidth(){return this.width;}

    private final double height;
    public double getHeight() {return this.height;}

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
        if (timeDifference <= getTimeToFirstcollision()){
            for (Entity entity : getAllEntities()) {
                entity.move(timeDifference);
                if (entity instanceof Ship)
                    if (((Ship) entity).getThrusterState())
                        ((Ship) entity).thrust(timeDifference);
            }

        }
        else{
            evolve(getTimeToFirstcollision());
        }
    }
    public double getTimeToFirstcollision(){ //Control collision with world boundaries!
        double timeToFirstCollision = Double.POSITIVE_INFINITY;
        List<Entity> allEntities = new ArrayList<>();
        allEntities.addAll(getAllBullets());
        allEntities.addAll(getAllShips());
        for (int i = 0; i < allEntities.size(); i++){
            for (int k = i+1; k < allEntities.size(); k++){
                double newTime = allEntities.get(i).getTimeToCollision(allEntities.get(k));
                if (newTime < timeToFirstCollision)
                    timeToFirstCollision = newTime;

            }
        }
        return timeToFirstCollision;
    }
}
