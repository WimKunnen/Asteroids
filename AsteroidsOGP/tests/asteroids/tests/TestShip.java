package asteroids.tests;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static javafx.scene.input.KeyCode.T;
import static org.junit.Assert.*;
import asteroids.model.Ship;
import org.junit.Test;
import asteroids.model.Ship;
import asteroids.model.Vector;
import org.junit.*;



/**
 * A class collecting tests for the class of ships.
 * 
 * @author Maarten Doclo and Wim Kunnen
 *
 */

public class TestShip {

	private static final double EPSILON = 0.0001;
	
	private Ship ship1;
	private Ship ship2;
	private Ship ship3;
	private Ship ship4;
	
	@Before
	public void setUp() {
		ship2 = new Ship(0.0,0.0,10.0,10.0,30.0,0.0);
		ship3 = new Ship(100.0,0.0,0.0,0.0,30,0.0);
		ship4 = new Ship(30.0,0.0,10.0,0.0,10.0,0.0);
	}

	@Test
	public void setMaximumVelocity_Legal() {
		ship1 = new Ship(1.5,15.0,10.0,20.0,30.0, Math.PI);
		ship1.setMaximumVelocity(100000.0);
		assertEquals(100000.0,ship1.getMaximumVelocity(),EPSILON);
		assertEquals(1.5,ship1.getPosition().getX(),EPSILON);
		assertEquals(15.0,ship1.getPosition().getY(),EPSILON);
		assertEquals(10.0,ship1.getVelocity().getX(),EPSILON);
		assertEquals(20.0,ship1.getVelocity().getY(),EPSILON);
		assertEquals(30.0,ship1.getRadius(),EPSILON);
		assertEquals(Math.PI,ship1.getHeading(),EPSILON);
	}

	@Test
	public void isValidAngle_LegalCase() {
		ship1 = new Ship(1.5,15.0,10.0,20.0,30.0, Math.PI);
		ship1.setMaximumVelocity(100000.0);
		assertTrue(ship1.isValidAngle(Math.PI));
		assertTrue(ship1.isValidAngle(0));

	}
	
	@Test
	public void isValidAngle_IllegalCase() {
		ship1 = new Ship(1.5,15.0,10.0,20.0,30.0, Math.PI);
		ship1.setMaximumVelocity(100000.0);
		assertFalse(ship1.isValidAngle(-Math.PI));
		assertFalse(ship1.isValidAngle(3*Math.PI));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void radius_IllegalCase() throws Exception {
		ship1 = new Ship(1.5,15.0,10.0,20.0,30.0, Math.PI);
		ship1.setMaximumVelocity(100000.0);
		ship1 = new Ship(1.5,15.0,10.0,20.0,5.0,Math.PI);
				
	}
	
	@Test
	public void isValidRadiusTest() {
		ship1 = new Ship(1.5,15.0,10.0,20.0,30.0, Math.PI);
		ship1.setMaximumVelocity(100000.0);
		assertTrue(ship1.isValidRadius(10));
		assertTrue(ship1.isValidRadius(100));
		assertFalse(ship1.isValidRadius(5));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void move_IllegalCase() throws Exception {
		ship1 = new Ship(0.0,0.0,10.0,10.0,30.0,Math.PI);
		ship1.move(-5);
	}
	
	@Test 
	public void move_LegalCase() {
		ship2.move(10);
		assertEquals(100.0,ship2.getPosition().getX(),EPSILON);
		assertEquals(100.0,ship2.getPosition().getY(),EPSILON);
	}
	
	@Test
	public void testTurn(){
		ship2.turn(Math.PI);
		assertEquals(Math.PI,ship2.getHeading(),EPSILON);
		ship2.turn(Math.PI);
		assertEquals(0.0,ship2.getHeading(),EPSILON);
		ship2.turn(-Math.PI);
		assertEquals(Math.PI,ship2.getHeading(),EPSILON);
	}
	
	@Test
	public void testThrust(){
		ship2.thrust(10);
		assertEquals(20.0,ship2.getVelocity().getX(),EPSILON);
		assertEquals(10.0,ship2.getVelocity().getY(),EPSILON);
		
	}

	@Test
	public void testGetDistanceBetween() {
		assertEquals(40.0,ship2.getDistanceBetween(ship3),EPSILON);
	}
	
	@Test 
	public void testOverlap() {
		assertFalse(ship2.overlap(ship3));
		assertTrue(ship2.overlap(ship4));
	}
	
	@Test
	public void testWillCollide() {
		assertTrue(ship3.willCollide(ship4));
		assertFalse(ship2.willCollide(ship3));
	}
	
	@Test
	public void testGetCollisionTime() {
		assertEquals(Double.POSITIVE_INFINITY,ship2.getTimeToCollision(ship3),EPSILON);
		assertEquals(3.0,ship4.getTimeToCollision(ship3),EPSILON);
	}
	
	@Test
	public void testGetCollisionPosition() {
		assertNull(ship2.getCollisionPosition(ship3));
		assertEquals(70.0,ship3.getCollisionPosition(ship4).getX(), EPSILON);
		assertEquals(0.0, ship3.getCollisionPosition(ship4).getY(),EPSILON);
	}

}
