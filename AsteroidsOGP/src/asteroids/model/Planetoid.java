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

    public void decrementRadius(double timeDifference){
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

    public void decrementRadiusDistance(double distanceTravelled){
        double newRadius = this.getRadius() - 0.0001 * distanceTravelled;
        if (newRadius < 5){
            this.terminate();
        }
        this.setRadius(newRadius);
    }

    @Override
    public void terminate(){
        World thisWorld = getWorld();

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
            childA.setPosition(positionA.sum(getPosition()));
            childA.setVelocity(velocityA);
            childA.setRadius(this.getRadius()/2);

            Asteroid childB = new Asteroid();
            childB.setPosition(positionB.sum(getPosition()));
            childB.setVelocity(velocityB);
            childB.setRadius(this.getRadius()/2);

            this.isTerminated = true;
            if (getWorld() != null)
                this.getWorld().removeEntity(this);

            thisWorld.addEntity(childA);
            thisWorld.addEntity(childB);
        }
        else {
            this.isTerminated = true;
            if (getWorld() != null)
                this.getWorld().removeEntity(this);

        }
    }

    private double distanceTravelled;

    public double getDistanceTravelled(){
        return distanceTravelled;
    }

    protected void travel(double distance){
        this.distanceTravelled += distance;
    }

    /**
     * Changes the position of the entity by the velocity * time difference.
     *
     * @param   timeDifference
     *          The difference in time between two frames.
     *
     * @post    The new position is equal to the sum of the old position and the velocity times the time difference.
     *          | new.getPosition == this.getPosition + this.getVelocity * timeDifference
     *
     * @throws  IllegalArgumentException
     *          The given time difference is smaller than zero.
     *          | !isValidTimeDifference
     */
    @Override
    public void move(double timeDifference) throws IllegalArgumentException{

        if(isValidTimeDifference(timeDifference)){
            setPosition(this.getPosition().sum(getVelocity().resizeVector(timeDifference)));
            double distanceTravelled = timeDifference * this.getVelocity().vectorLength();
            this.travel(distanceTravelled);
            this.decrementRadius(timeDifference);
        }else{
            throw new IllegalArgumentException();
        }
    }
}
