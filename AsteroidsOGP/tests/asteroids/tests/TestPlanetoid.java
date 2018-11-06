package asteroids.tests;

import asteroids.model.Planetoid;
import asteroids.model.World;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * @author WimKunnen and Maarten Doclo
 */
public class TestPlanetoid {
    private static final double EPSILON = 0.0001;
    private Planetoid planetoid;

    @Before
    public void setUp(){
        planetoid = new Planetoid(100,100,10,0,50,0);
    }
    @Test
    public void testDecrementRadiusDistance(){
        planetoid.decrementRadiusDistance(20000);
        assertEquals(50-20000*0.000001, planetoid.getRadius(),EPSILON);

    }
    @Test
    public void testDecrementRadiusTimeDifferece(){
        planetoid.decrementRadius(100);
        assertEquals(50-0.000001*100*10, planetoid.getRadius(),EPSILON);
    }
    @Test
    public void testTerminate(){
        World world = new World(1000,1000);
        world.addEntity(planetoid);
        planetoid.terminate();
        assertEquals(2,world.getAllEntities().size());
        assertEquals(2, world.getAllAsteroids().size());
        assertEquals(0, world.getAllPlanetoids().size());

    }
}
