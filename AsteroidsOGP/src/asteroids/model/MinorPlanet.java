package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * Created by WimKunnen on 18/04/2017.
 */
public abstract class MinorPlanet extends Entity {

    public MinorPlanet(double x, double y, double velocityX, double velocityY, double radius){
        super(x, y, velocityX, velocityY, radius);
    }

    public MinorPlanet(){
        super();
        this.setRadius(this.getMinimumRadius());
    }

    /**
     * Variable registering the minimum radius of bullets.
     */
    private final static double minimumRadius = 5;

    /**
     * Return the minimum radius of all bullets.
     * @see implementation
     */
    @Basic
    @Override
    public double getMinimumRadius(){
        return minimumRadius;
    }

}
