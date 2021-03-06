package asteroids.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")

/**
 * A class of collisions for the Asteroid project.
 * Collisions happen in a world and involve two entities or an entity and a border of the world.
 *
 * @author WimKunnen and Maarten Doclo
 *
 * @version 1.0
 */
public class Collision {
    /**
     * A variable registering the world to which the collision belongs.
     */
    private World world;

    /**
     * Initializer for a new collision.
     * @param world
     *        The world in which the collision takes place.
     */

    public Collision(World world){
        this.setWorld(world);
    }

    /**
     * Return the world of this collision.
     */
    private World getWorld(){
        return this.world;
    }

    /**
     * Set the world of this collision to a given world.
     *
     * @post
     *      | new.getWorld() == world
     */
    private void setWorld(World world){
        this.world = world;
    }

    /**
     * Resolve the collision between an entity and the boundaries it collides with.
     *
     * @param currentEntity The possibly colliding entity
     */
    protected void resolveBoundaryCollision(Entity currentEntity){
        if ((currentEntity.apparentlyCollidesWithLeft() && currentEntity.getVelocity().getX() < 0) ||
                (currentEntity.apparentlyCollidesWithRight() && currentEntity.getVelocity().getX() > 0)) {
            currentEntity.negateVelocityX();
            if (currentEntity instanceof Bullet) {
                ((Bullet) currentEntity).riseNbOfBounces();
                if (((Bullet) currentEntity).getNbOfBounces() >= ((Bullet) currentEntity).getMaxNbBounces()) {
                    (currentEntity).terminate();
                }
            }
        }

        if ((currentEntity.apparentlyCollidesWithBottom() && currentEntity.getVelocity().getY() < 0) ||
                (currentEntity.apparentlyCollidesWithTop() && currentEntity.getVelocity().getY() > 0)) {
            currentEntity.negateVelocityY();
            if (currentEntity instanceof Bullet) {
                ((Bullet) currentEntity).riseNbOfBounces();
                if (((Bullet) currentEntity).getNbOfBounces() >= ((Bullet) currentEntity).getMaxNbBounces()) {
                    (currentEntity).terminate();
                }
            }
        }
    }

    /**
     * Resolve the collision between two entities.
     *
     * If both entities are ships or minor planets, they bounce of each other.
     * (New velocities: see implementation)
     * | if((entity1 instanceof Ship && entity2 instanceof Ship) ||
     * |        (entity1 instanceof MinorPlanet && entity2 instanceof MinorPlanet)) then
     * |    entity1.setVelocity(velocity1)
     * |    entity2.setVelocity(velocity2)
     *
     * If one of the entities is an enemy bullet which has been out of its ship and the other entity is a ship,
     * both are terminated.
     * |@see implementation
     *
     * If a ship collides with an asteroid, the ship is terminated.
     * |@see implementation
     *
     * If a ship collides with a planetoid, the ship is teleported to a random location in its world.
     * |@see implementation
     *
     * @param   entity1
     *          One colliding entity
     *
     * @param   entity2
     *          Other colliding entity
     */
    public void resolveEntityCollision(Entity entity1, Entity entity2) {

        if ((entity1 instanceof Bullet && ((Bullet) entity1).getSource() != entity2 && ((Bullet) entity1).hasBeenOutOfShip())
                || (entity2 instanceof Bullet && ((Bullet) entity2).getSource() != entity1 && ((Bullet) entity2).hasBeenOutOfShip())){
//            this.getWorld().addScore(entity1.getScore());
//            this.getWorld().addScore(entity2.getScore());
            if (entity1 instanceof Bullet && ((Bullet) entity1).getSource() != null){
                ((Bullet) entity1).getSource().addPoints(entity2.getScore());
            }else if(entity2 instanceof Bullet && ((Bullet) entity2).getSource() != null){
                ((Bullet) entity2).getSource().addPoints(entity1.getScore());
            }
            entity1.terminate();
            entity2.terminate();
        }

        if (entity1 instanceof Ship && entity2 instanceof Ship){
            Ship ship1 = (Ship)entity1;
            Ship ship2 = (Ship)entity2;

            ArrayList<Ship> shipPair = new ArrayList<>();

            shipPair.add(ship1);
            shipPair.add(ship2);
            Vector velocity1 = new Vector(entity1.getVelocity().getX() +
                    Jx(shipPair)/ship1.getTotalMass(),
                    entity1.getVelocity().getY() + Jy(shipPair)/ship1.getTotalMass());
            Vector velocity2 = new Vector(entity2.getVelocity().getX() -
                    Jx(shipPair)/ship2.getTotalMass(),
                    entity2.getVelocity().getY() - Jy(shipPair)/ship2.getTotalMass());

            entity1.setVelocity(velocity1);
            entity2.setVelocity(velocity2);
        }

        else if (entity1 instanceof MinorPlanet && entity2 instanceof MinorPlanet){
            MinorPlanet planet1 = (MinorPlanet)entity1;
            MinorPlanet planet2 = (MinorPlanet)entity2;

            List<MinorPlanet> planetPair = new ArrayList<>();

            planetPair.add(planet1);
            planetPair.add(planet2);

            Vector velocity1 = new Vector(entity1.getVelocity().getX() +
                    Jx(planetPair)/planet1.getMassOfEntity(),
                    entity1.getVelocity().getY() + Jy(planetPair)/planet1.getMassOfEntity());
            Vector velocity2 = new Vector(entity2.getVelocity().getX() -
                    Jx(planetPair)/planet2.getMassOfEntity(),
                    entity2.getVelocity().getY() - Jy(planetPair)/planet2.getMassOfEntity());
            entity1.setVelocity(velocity1);
            entity2.setVelocity(velocity2);
        }

        else if (entity1 instanceof Bullet && entity2 instanceof Ship)
            resolveBulletEntityCollision((Ship) entity2,(Bullet) entity1);
        else if (entity1 instanceof Ship && entity2 instanceof Bullet)
            resolveBulletEntityCollision((Ship) entity1,(Bullet) entity2);
        
        else if (entity1 instanceof Ship && entity2 instanceof Asteroid)
            entity1.terminate();
        else if (entity1 instanceof Asteroid && entity2 instanceof Ship)
            entity2.terminate();
        else if (entity1 instanceof Ship && entity2 instanceof Planetoid)
            ((Ship) entity1).teleportRandomLocation();
        else if (entity1 instanceof Planetoid && entity2 instanceof Ship)
            ((Ship) entity2).teleportRandomLocation();


    }

    /**
     * Resolve the collision between a bullet and a ship.
     *
     * @param   entity
     *          The colliding entity
     * @param   bullet
     *          The colliding bullet
     */
    public void resolveBulletEntityCollision(Entity entity, Bullet bullet){
        if (bullet.getSource() == (Ship)entity && bullet.hasBeenOutOfShip()){
            bullet.setPosition(entity.getPosition());
            ((Ship)entity).reload(bullet);
        }

        else if (bullet.getSource() != entity) {
            //this.getWorld().addScore(entity.getScore());
            if(bullet.getSource() != null)
                bullet.getSource().addPoints(entity.getScore());
            entity.terminate();
            bullet.terminate();
        }
    }

    public Entity firstEntityToCollideBoundary;
    public List<Entity> firstEntityPairToCollide = new ArrayList<>();


    /**
     * Return the time to the first collision between two entities.
     */
    public double getTimeToFirstEntityCollision(){
        double timeToFirstCollision = Double.POSITIVE_INFINITY;
        List<Entity> allEntities = new ArrayList<>();
        allEntities.addAll(getWorld().getAllEntities());
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

    /**
     * Return the time to the first collision between an entity and a boundary of the world.
     * @see implementation
     */
    public double getTimeToFirstBoundaryCollision() {
        double timeToFirstCollision = Double.POSITIVE_INFINITY;
        List<Entity> allEntities = new ArrayList<>();
        allEntities.addAll(getWorld().getAllEntities());
        for (int i = 0; i < allEntities.size(); i++) {
            double newTime = allEntities.get(i).getTimeToCollisionWithBoundary();
            if (newTime < timeToFirstCollision) {
                timeToFirstCollision = newTime;
                firstEntityToCollideBoundary = allEntities.get(i);
            }
        }
        return timeToFirstCollision;
    }

    /**
     * Return the time to the first collision happening in this world.
     * @return  result == (Math.min(getTimeToFirstBoundaryCollision,getTimeToFirstEntityCollision))
     */
    public double getTimeToFirstCollision() {
        double time = getTimeToFirstBoundaryCollision();
        double entityTime = getTimeToFirstEntityCollision();
        if (entityTime < time)
            time = entityTime;

        return time;
    }


    /**
     * Return the position of the first collision happening in this world.
     * @see implementation
     */
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

        throw new AssertionError();
    }

    /**
     * J as defined in the assignment.
     *
     * @param shipPair
     *        a list of two ships
     *
     * @see implementation
     */
    private double J(ArrayList<Ship> shipPair) {
        Ship ship1 = shipPair.get(0);
        Ship ship2 = shipPair.get(1);
        return 2 * ship1.getTotalMass() * ship2.getTotalMass() *
                ship1.deltaV(ship2).scalarProduct(ship1.deltaR(ship2))
                / ( ship1.sigma(ship2) * (ship1.getTotalMass() + ship2.getTotalMass()));
    }

    /**
     * J as defined in the assignment.
     *
     * @param planetPair
     *        a list of two minor planets.
     *
     * @see implementation
     */
    private double J(List<MinorPlanet> planetPair) {
        MinorPlanet planet1 = planetPair.get(0);
        MinorPlanet planet2 = planetPair.get(1);
        return 2 * planet1.getMassOfEntity() * planet2.getMassOfEntity() *
                planet1.deltaV(planet2).scalarProduct(planet1.deltaR(planet2))
                / ( planet1.sigma(planet2) * (planet1.getMassOfEntity() + planet2.getMassOfEntity()));
    }

    /**
     * J_x as defined in the assignment.
     *
     * @param shipPair
     *        a list of two ships
     *
     * @see implementation
     */
    private double Jx(ArrayList<Ship> shipPair) {
        Ship ship1 = shipPair.get(0);
        Ship ship2 = shipPair.get(1);
        return J(shipPair) * ship1.deltaR(ship2).getX() / ship1.sigma(ship2);
    }

    /**
     * J_x as defined in the assignment.
     *
     * @param planetPair
     *        a list of two minor planets
     *
     * @see implementation
     */
    private double Jx(List<MinorPlanet> planetPair) {
        MinorPlanet planet1 = planetPair.get(0);
        MinorPlanet planet2 = planetPair.get(1);
        return J(planetPair) * planet1.deltaR(planet2).getX() / planet1.sigma(planet2);
    }

    /**
     * J_y as defined in the assignment.
     *
     * @param shipPair
     *        a list of two ships
     *
     * @see implementation
     */
    private double Jy(ArrayList<Ship> shipPair) {
        Ship ship1 = shipPair.get(0);
        Ship ship2 = shipPair.get(1);
        return J(shipPair) * ship1.deltaR(ship2).getY() / ship1.sigma(ship2);
    }

    /**
     * J_y as defined in the assignment.
     *
     * @param planetPair
     *        a list of two planets
     *
     * @see implementation
     */
    private double Jy(List<MinorPlanet> planetPair) {
        MinorPlanet planet1 = planetPair.get(0);
        MinorPlanet planet2 = planetPair.get(1);
        return J(planetPair) * planet1.deltaR(planet2).getY() / planet1.sigma(planet2);
    }
}
