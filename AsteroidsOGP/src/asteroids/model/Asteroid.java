package asteroids.model;

/**
 * Created by WimKunnen on 18/04/2017.
 */
public class Asteroid extends MinorPlanet {

    public Asteroid(double x, double y, double velocityX, double velocityY, double radius)
            throws IllegalArgumentException{
        super(x, y, velocityX, velocityY, radius);
        this.setDensity(2.56E12);
    }

    public Asteroid(){
        super();
        this.setDensity(2.56E12);
        this.setRadius(this.getMinimumRadius());
    }

}
