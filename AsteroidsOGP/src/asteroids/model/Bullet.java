package asteroids.model;

import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;

/**
 * @author Wim Kunnen and Maarten Doclo.
 */

public class Bullet extends Entity {

    /**
     * Initializes a new Bullet by using the initializer defined in the Entity super class.
     * @param   x
     *          The initial position of the new bullet along the x-axis.
     *
     * @param   y
     *          The initial position of the new bullet along the y-axis.
     *
     * @param   velocityX
     *          The initial velocity of the new bullet along the x-axis.
     *
     * @param   velocityY
     *          The initial velocity of the new bullet along the y-axis.
     *
     * @invar   A bullet cannot be held by a world and a ship at the same time.
     *          | getWorld() == null || getSource() == null
     *
     */
    public Bullet(double x, double y, double velocityX, double velocityY, World world) throws IllegalArgumentException{
        super(x, y, velocityX, velocityY, world);
        this.setMinimumRadius(1);
        if(isValidRadius(radius)){
            this.setRadius(radius);
        }else{
            throw new IllegalArgumentException();
        }
        this.setDensity(7.8 * Math.pow(10, 12));
    }

    /**
     * Default initializer which uses the initializer defined in the Entity super class.
     */
    public Bullet(){
        super();
        this.setDensity(7.8 * Math.pow(10, 12));
        this.setMinimumRadius(1);
        this.setRadius(this.getMinimumRadius());
    }
    //Radius
    /**
     * Variable registering the minimum radius of all entities.
     */
    private double minimumRadius = 10;


    protected double getMinimumRadius(){
        return this.minimumRadius;
    }

    protected void setMinimumRadius(double newMinimumRadius){
        this.minimumRadius = newMinimumRadius;
    }
    /**
     * Variable registering the radius of this entity.
     */
    private double radius;

    /**
     * Returns the radius of the entity.
     */
    @Basic
    @Immutable
    public double getRadius(){
        return this.radius;
    }

    public void setRadius(double newRadius){
        this.radius = newRadius;
    }

    /**
     * Returns true if and only if the given radius is larger than the minimum radius and not NaN.
     *
     * @param   radius
     *          The radius which validity will be checked.
     */
    public boolean isValidRadius(double radius){
        return (radius >= minimumRadius && ! Double.isNaN(radius));
    }

    private int maxNbBounces = 3;
    public int getMaxNbBounces(){
        return this.maxNbBounces;
    }
    private void setMaxNbBounces(int newMaxNb){
        this.maxNbBounces = newMaxNb;
    }
    private int nbOfBounces = 0;
    public int getNbOfBounces(){
        return this.nbOfBounces;
    }
    protected void riseNbOfBounces() {
        this.nbOfBounces += 1;
    }

    public Ship source;
    public Ship getSource(){
        return this.source;
    }
    protected void setSource(Ship sourceShip){
        this.source = sourceShip;
    }

//    public void loadOnShip(Ship ship){
//        this.ship = ship;
//    }
//
//    public void loadOnShip(Ship ship, Vector vector){
//        this.ship = ship;
//        setPosition(vector);
//    }



}
