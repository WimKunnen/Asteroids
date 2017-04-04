package asteroids.tests;

import asteroids.model.Ship;
import org.junit.Before;
import org.junit.Test;
import asteroids.model.Bullet;

import static org.junit.Assert.*;


/**
 * A class containing test suits for the class of bullets.
 *
 * @version 2.0
 *
 * @author Maarten Doclo and Wim Kunnen
 */
public class TestBullet {

    private static final double EPSILON = 0.0001;
    private Bullet bullet1;
    private Bullet bullet2;
    private Ship ship1;

    /**
     * Set up two bullets and a ship for testing.
     */
    @Before
    public void setUp(){
        bullet1 = new Bullet(3, 4, 10, 0, 5);
        bullet2 = new Bullet(3, 20, 0, 10, 4);
        bullet2.riseNbOfBounces();
        ship1 = new Ship(1.5,15.0,10.0,20.0, Math.PI,30,100000000);
        bullet1.setSource(ship1);
    }

    /**
     * A test suit for the getMinimumRadius() method of the Bullet class.
     */
    @Test
    public void testMinimumRadius(){
        assertEquals(1, bullet1.getMinimumRadius(), EPSILON);
        assertEquals(1, bullet2.getMinimumRadius(), EPSILON);
    }

    /**
     * A test suit for the getRadius() method of the Bullet class.
     */
    @Test
    public void testGetRadius(){
        assertEquals(5, bullet1.getRadius(), EPSILON);
        assertEquals(4, bullet2.getRadius(), EPSILON);
    }

    /**
     * A test suit for the getMaxNbBounces() method of the Bullet class.
     */
    @Test
    public void testGetMaxNbBounces(){
        assertEquals(3, bullet1.getMaxNbBounces(), EPSILON);
    }

    /**
     * A test suit for the getNbOfBounces() method of the Bullet class.
     */
    @Test
    public void testGetNbBounces(){
        assertEquals(0, bullet1.getNbOfBounces(), EPSILON);
        assertEquals(1, bullet2.getNbOfBounces(), EPSILON);
    }

    /**
     * A test suit for the getSource() method of the Bullet class.
     */
    @Test
    public void testSource(){
        assertTrue(bullet1.getSource() == ship1);
        assertFalse(bullet2.getSource() == ship1);
    }

    /**
     * A test suit for the hasBeenOutOfShip() and switchBeenOutOfShip method of the Bullet class.
     */
    @Test
    public void testHasBeenOutOfShip(){
        assertTrue(bullet1.hasBeenOutOfShip());
        bullet1.switchBeenOutOfShip(false);
        assertFalse(bullet1.hasBeenOutOfShip());
    }
}