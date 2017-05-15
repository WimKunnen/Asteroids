package asteroids.tests;

import asteroids.model.*;
import org.junit.Before;

import static org.junit.Assert.*;
import org.junit.Test;

import asteroids.model.Ship;

/**
 * Created by maarten on 28/03/2017.
 */
public class TestWorld {

    private static final double EPSILON = 0.0001;

    private Ship ship1;
    private Ship ship2;
    private Ship ship3;
    private Ship ship4;

    private Bullet bullet1;
    private Bullet bullet2;

    private Vector position = new Vector(150.0,150.0);
    private Vector position2 = new Vector(110.0,100.0);
    private Vector position3 = new Vector(100.0,100.0);
    private World world;

    /**
     * A set up method which initializes four ships, two bullets and a world.
     */
    @Before
    public void setUp() {
        ship1 = new Ship(150.0, 150.0, 10.0, 20.0, 0.0, 10.0, 100000000);
        ship2 = new Ship(100.0, 100.0, 10.0, 0.0, 0.0, 10.0, 100000000);
        ship3 = new Ship(100.0, 0.0, 0.0, 0.0, 0.0, 30.0, 100000000);
        ship4 = new Ship(150.0, 160.0, 10.0, 0.0, 0.0, 30.0, 100000000);


        bullet1 = new Bullet(150.0,150.0,0.0,0.0,1.0);
        bullet2 = new Bullet(300.0,300.0,0.0,0.0,10.0);
        bullet2.switchBeenOutOfShip(false);

        ship1.reload(bullet1);
        world = new World(1000,1000);

        world.addEntity(ship1);
        world.addEntity(ship2);
        world.addEntity(bullet2);
    }

    /**
     * A test suit for the updatePositionMap() method of the World class.
     */
    @Test
    public void positionMapUpdateTest(){
        assertEquals(ship2,world.getEntityAt(position3));
        world.evolve(1.0, null);
        assertEquals(position2,ship2.getPosition());
        assertEquals(ship2,world.getEntityAt(position2));
    }

    /**
     * A test suit for the initializer method of the World class.
     */
    @Test
    public void worldSizeTest(){
        World world2 = new World(-10,-20);
        assertEquals(world2.getLowerBound(), world2.getHeight(),EPSILON);
        assertEquals(world2.getLowerBound(), world2.getWidth(),EPSILON);
    }

    /**
     * A test suit for the terminate() method of the World class.
     */
    @Test
    public void terminateTest(){
        assertEquals(world,ship1.getWorld());
        assertEquals(world,ship2.getWorld());
        assertFalse(world.checkTermination());

        world.terminate();

        assertEquals(null,ship1.getWorld());
        assertEquals(null,ship2.getWorld());
        assertTrue(world.checkTermination());
    }

    /**
     * A test suit for the addEntity() method of the World class, which handles the case in which the entity to add
     * doesn't fit in the boundaries of the new world.
     */
    @Test (expected = IllegalArgumentException.class)
    public void addEntityOutOfBoundariesTest(){
        world.addEntity(ship3);
    }

    /**
     * A test suit for the addEntity() method of the World class, which handles the case in which the entity to add
     * overlaps with an entity in the new world.
     */
    @Test (expected = IllegalArgumentException.class)
    public void addEntityOnOtherEntityTest(){
        world.addEntity(ship4);
    }

    /**
     * A test suit for the addEntity() method of the World class.
     */
    @Test
    public void addEntityTest(){
        assertEquals(3,world.getAllEntities().size());
        assertEquals(2,world.getAllEntitiesOfType(Ship.class).size());
        assertEquals(1,world.getAllEntitiesOfType(Bullet.class).size());
    }

    /**
     * A test suit for the removeEntity() method of the World class.
     */
    @Test
    public void removeEntityTest(){
        assertTrue(world.entityPositionMap.containsValue(ship1));

        world.removeEntity(ship1);

        assertEquals(2,world.getAllEntities().size());
        assertEquals(1,world.getAllEntitiesOfType(Ship.class).size());
        assertFalse(world.entityPositionMap.containsValue(ship1));

        world.removeEntity(ship2);

        assertEquals(1,world.getAllEntities().size());
        assertEquals(0,world.getAllEntitiesOfType(Ship.class).size());
    }

//    /**
//     * A test suit for the resolveBulletEntityCollision() method of the World class.
//     */
//    @Test
//    public void resolveBulletShipCollisionTest(){
//        world.resolveBulletEntityCollision(ship1,bullet2);
//        assertTrue(ship1.checkTermination());
//        assertTrue(bullet2.checkTermination());
//    }


}
