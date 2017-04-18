package asteroids.model;

/**
 * Created by WimKunnen on 18/04/2017.
 */
public class Planetoid extends MinorPlanet {

    public Planetoid(double x, double y, double velocityX, double velocityY, double radius)
            throws IllegalArgumentException{
        super(x, y, velocityX, velocityY, radius);
        this.setDensity(0.917E12);
    }

    public Planetoid() {
        super();
        this.setDensity(0.917E12);
        this.setRadius(this.getMinimumRadius());
    }

    protected void decrementRadius(double timeDifference){
        double velocity = this.getVelocity().vectorLength();
        double distanceTravelled = velocity * timeDifference;

//        Vector position1 = this.getPosition();
//        this.move(timeDifference);
//        Vector position2 = this.getPosition();
//        Vector travel = position2.sum(position1.negate());
//        double distanceTravelled = travel.vectorLength();

        double newRadius = this.getRadius() - 0.0001 * distanceTravelled;
        if (newRadius < 5){
            this.terminate();
        }
        this.setRadius(newRadius);
    }

    @Override
    public void terminate(){

        if (this.getRadius() >= 30){
            double positionAngle = Math.random() * 2 * Math.PI;
            double velocityAngle = Math.random() * 2 * Math.PI;

            Vector velocityA = new Vector(this.getVelocity().vectorLength() * 1.5 * Math.cos(velocityAngle),
                    this.getVelocity().vectorLength() * 1.5 * Math.sin(velocityAngle));
            Vector velocityB = velocityA.negate();

            Vector positionA = new Vector(this.getRadius() / 2 * Math.cos(positionAngle),
                    this.getRadius() / 2 * Math.sin(positionAngle));
            Vector positionB = positionA.negate();

            Asteroid childA = new Asteroid();
            childA.setPosition(positionA);
            childA.setVelocity(velocityA);

            Asteroid childB = new Asteroid();
            childB.setPosition(positionB);
            childB.setVelocity(velocityB);
        }

        this.isTerminated = true;
        if (getWorld() != null)
            this.getWorld().removeEntity(this);


    }
}
