package asteroids.model;

import java.util.Set;

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
            entity.setWorld(null);
            if (!allShips.contains(entity) && !allBullets.contains(entity))
                throw new IllegalArgumentException();
            if (entity instanceof Ship)
                allShips.remove(entity);
            if (entity instanceof Bullet)
                allBullets.remove(entity);
        }
    }

    public Entity getEntityAt(Vector position) {
        return null;
    }


    public void evolve(double timeDifference) {}
}
