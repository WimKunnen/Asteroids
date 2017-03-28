package asteroids.tests;

import asteroids.model.*;
import org.junit.Before;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.*;
import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;

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
     * A set up method which initializes 4 ships and set a maximum velocity to the first two ships.
     */
    @Before
    public void setUp() {
        ship1 = new Ship(150.0, 150.0, 10.0, 20.0, 0.0, 10.0, 100000000);
        ship1.setMaximumVelocity(100000.0);
        ship2 = new Ship(100.0, 100.0, 10.0, 0.0, 0.0, 10.0, 100000000);
        ship2.setMaximumVelocity(300010);
        ship3 = new Ship(100.0, 0.0, 0.0, 0.0, 0.0, 30.0, 100000000);
        ship4 = new Ship(30.0, 0.0, 10.0, 0.0, 0.0, 30.0, 100000000);


        bullet1 = new Bullet(150.0,150.0,0.0,0.0,10.0);
        ship1.reload(bullet1);
        world = new World(1000,1000);

        world.addEntity(ship1);
        world.addEntity(bullet1);

        world.addEntity(ship2);

        world.evolve(1.0);


    }

    @Test
    public void mapTest(){
        assertEquals(bullet1, world.getEntityAt(position));
        assertEquals(2,world.entityPositionMap.size());
    }

    @Test
    public void positionUpdateMapTest(){
        assertEquals(position2,ship2.getPosition());
        assertEquals(ship2,world.getEntityAt(position2));
    }
}
