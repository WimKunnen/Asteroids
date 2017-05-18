package asteroids.tests;

import asteroids.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A class containing test suits for the class of entities.
 *
 * @author Maarten Doclo and Wim Kunnen
 */
public class TestEntity {
    private static final double EPSILON = 0.0001;

    private Ship ship1;
    private Ship ship2;
    private Ship ship3;
    private Entity ship4;
    private Entity ship5;


    private Bullet bullet1;
    private Bullet bullet2;

    private World world;

    /**
     * A set up method which initializes five ships, two bullets and a world.
     */
    @Before
    public void setUp() {
        ship1 = new Ship(150.0, 150.0, 10.0, 20.0, 0.0, 10.0, 100000000);
        ship2 = new Ship(100.0, 100.0, 10.0, 0.0, 0.0, 10.0, 100000000);
        ship3 = new Ship(120.0, 100.0, 0.0, 0.0, 0.0, 10.0, 100000000);
        ship4 = new Ship(150.0, 160.0, 10.0, 0.0, 0.0, 30.0, 100000000);
        ship5 = new Ship(969.9, 160.0, 10.0, 0.0, 0.0, 30.0, 100000000);


        bullet1 = new Bullet(150.0,150.0,0.0,0.0,1.0);
        bullet2 = new Bullet(300.0,300.0,0.0,0.0,1.0);
        bullet2.switchBeenOutOfShip(false);

        ship1.reload(bullet1);
        world = new World(1000,1000);

        world.addEntity(ship1);
        world.addEntity(ship2);
        world.addEntity(bullet2);
    }

    /**
     * A test suit for the initializer of the Entity class.
     */
    @Test (expected = IllegalArgumentException.class)
    public void initializerTest(){
        assertEquals(null, ship4.getWorld());
        assertEquals(new Vector(150.0,160.0),ship4.getPosition());
        new Ship(Double.POSITIVE_INFINITY,1,1,1,1,1,1);
    }

    /**
     * A test suit for the negateVelocity() method of the Entity class.
     */
    @Test
    public void negateVelocityTest(){
        ship1.negateVelocityY();
        assertEquals(-20.0,ship1.getVelocity().getY(),EPSILON);
        ship1.negateVelocityX();
        assertEquals(-10,ship1.getVelocity().getX(),EPSILON);
    }

    /**
     * A test suit for the isValidRadius() method of the Entity class.
     */
    @Test
    public void isValidRadiusTest(){
        assertFalse(ship1.isValidRadius(9));
        assertTrue(ship1.isValidRadius(11));
        assertFalse(bullet1.isValidRadius(0.5));
        assertTrue(bullet1.isValidRadius(1.5));
    }

    /**
     * A test suit for the apparentlyCollidesWithEntity() method of the Entity class.
     */
    @Test
    public void apparentlyCollideTest(){
        assertTrue(ship2.apparentlyCollidesWithEntity(ship3));
        ship2 = new Ship(100.0, 100.0, 10.0, 0.0, 0.0, 10.0, 100000000);
        ship3 = new Ship(120.01, 100.0, 0.0, 0.0, 0.0, 10.0, 100000000);
        assertTrue(ship2.apparentlyCollidesWithEntity(ship3));
        ship2 = new Ship(100.0, 100.0, 10.0, 0.0, 0.0, 10.0, 100000000);
        ship3 = new Ship(119.9, 100.0, 0.0, 0.0, 0.0, 10.0, 100000000);
        assertTrue(ship2.apparentlyCollidesWithEntity(ship3));
        ship2 = new Ship(100.0, 100.0, 10.0, 0.0, 0.0, 10.0, 100000000);
        ship3 = new Ship(123.0, 100.0, 0.0, 0.0, 0.0, 10.0, 100000000);
        assertFalse(ship2.apparentlyCollidesWithEntity(ship3));
    }

    /**
     * A test suit for the apparentlyCollidesWithEBoundary() method of the Entity class.
     */
    @Test
    public void apparentlyCollidesWithBoundaryTest(){
        world.addEntity(ship5);
        assertTrue(ship5.apparentlyCollidesWithBoundary());
        assertTrue(ship5.apparentlyCollidesWithRight());
        assertFalse(ship5.apparentlyCollidesWithBottom());
        assertFalse(ship5.apparentlyCollidesWithLeft());
        assertFalse(ship5.apparentlyCollidesWithTop());
    }

    /**
     * A test suit for the methods getTimeToCollisionWithBoundary(), getBoundaryOfCollision() and
     * getCollisionPositionWithBoundary() of the Entity class.
     */
    @Test
    public void CollisionWithBoundaryTest(){
        world.addEntity(ship5);

        assertEquals(0.01,ship5.getTimeToCollisionWithBoundary(),EPSILON);
        assertEquals("R",ship5.getBoundaryOfCollision());
        assertEquals("T", ship1.getBoundaryOfCollision());
        assertEquals("R",ship2.getBoundaryOfCollision());

        assertEquals(1000.0,ship5.getCollisionPositionWithBoundary().getX(),EPSILON);
        assertEquals(160.0,ship5.getCollisionPositionWithBoundary().getY(),EPSILON);
    }

    /**
     * A test suit for the fliesApartFrom() method of the Entity class.
     */
    @Test
    public void fliesApartFromTest(){
        ship2 = new Ship(100.0, 100.0, -10.0, 0.0, 0.0, 10.0, 100000000);
        ship3 = new Ship(120, 100.0, 10.0, 0.0, 0.0, 10.0, 100000000);
        assertTrue(ship2.fliesApartFrom(ship3));

        ship2 = new Ship(100.0, 100.0, 0.0, 0.0, 0.0, 10.0, 100000000);
        ship3 = new Ship(120, 100.0, 0.0, 0.0, 0.0, 10.0, 100000000);
        assertTrue(ship2.fliesApartFrom(ship3));

        ship2 = new Ship(100.0, 100.0, 10.0, 0.0, 0.0, 10.0, 100000000);
        ship3 = new Ship(120, 100.0, 11.0, 0.0, 0.0, 10.0, 100000000);
        assertTrue(ship2.fliesApartFrom(ship3));

        ship2 = new Ship(100.0, 100.0, 0.0, 10.0, 0.0, 10.0, 100000000);
        ship3 = new Ship(120, 100.0, 0.0, -10.0, 0.0, 10.0, 100000000);
        assertTrue(ship2.fliesApartFrom(ship3));

        ship2 = new Ship(100.0, 100.0, 10.0, 0.0, 0.0, 10.0, 100000000);
        ship3 = new Ship(120, 100.0, 5.0, 0.0, 0.0, 10.0, 100000000);
        assertFalse(ship2.fliesApartFrom(ship3));
    }

    /**
     * A test suit for the terminate() method of the Entity class.
     */
    @Test
    public void terminateTest(){
        World thisWorld = ship1.getWorld();
        assertTrue(thisWorld.getAllEntities().contains(ship1));
        ship1.terminate();
        assertFalse(thisWorld.getAllEntities().contains(ship1));
    }

    /**
     * A test suit for the fitsInBoundaries() method of the Entity class.
     */
    @Test
    public void fitsInBoundariesTest() {
        assertTrue(ship5.fitsInBoundaries(world));
        ship5 = new Ship(970.1, 160.0, 10.0, 0.0, 0.0, 30.0, 100000000);
        assertTrue(ship5.fitsInBoundaries(world));
        ship5 = new Ship(970.4, 160.0, 10.0, 0.0, 0.0, 30.0, 100000000);
        assertFalse(ship5.fitsInBoundaries(world));
    }

    /**
     * A test suit for the noOverlapsInNewWorld() method of the Entity class.
     */
    @Test
    public void noOverlapsInNewWorldTest() {
        assertTrue(ship5.noOverlapsInNewWorld(world));
        ship5 = new Ship(160.0, 160.0, 10.0, 0.0, 0.0, 10.0, 100000000);
        assertFalse(ship5.noOverlapsInNewWorld(world));
    }



}
