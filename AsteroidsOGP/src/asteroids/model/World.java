package asteroids.model;

import java.util.Set;

/**
 * Created by maarten on 14/03/2017.
 */
public class World {

    // TODO setters for height and width!  Also, change default initializer!

    public World(double width, double height){
        this.width = width;
        this.height = height;
    }

    public World(){
        this.width = 800;
        this.height = 800;
    }



    private final double width;
    public double getWidth(){return this.width;}



    private final double height;
    public double getHeight() {return this.height;}


    public final static double lowerBound = 0;
    public static double upperBound = Double.MAX_VALUE;

//    public Set<> getAllShips() {...}
//    public Set<> getAllBullets() {...}
//
//    public void addEntity(Ship ship) throws IllegalArgumentException {
//        ...
//    }
//
//    public void addEntity(Bullet bullet) throws IllegalArgumentException {
//        ...
//    }
//
//    public void removeEntity(Ship ship) throws IllegalArgumentException {
//        ...
//    }
//    public void removeEntity(Bullet bullet) throws IllegalArgumentException {
//        ...
//    }
//
//    public Ship getEntityAt(Vector position) {
//        ...
//    }
//
//    public Bullet getEntityAt(Vector position) {...}
//
//    public void evolve(double timeDifference) {...}
}
