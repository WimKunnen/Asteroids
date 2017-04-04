package asteroids.tests;


import static org.junit.Assert.*;

import asteroids.model.Bullet;
import asteroids.model.World;
import org.junit.Test;
import org.junit.*;

import asteroids.model.Ship;

import java.util.Collection;
import java.util.HashSet;


/**
 * A class containing test suits for the class of ships.
 *
 * @version 2.0
 *
 * @author Maarten Doclo and Wim Kunnen
 */

public class TestShip {

	private static final double EPSILON = 0.0001;

	private Ship ship1;
	private Ship ship2;
	private Ship ship3;
	private Ship ship4;
	private Ship ship5;
	private Collection<Bullet> bullets = new HashSet<>();

	/**
	 * A set up method which initializes 4 ships and set a maximum velocity to the first two ships.
	 */
	@Before
	public void setUp() {
		ship1 = new Ship(1.5,15.0,10.0,20.0,Math.PI, 30.0,1E19);
		ship1.setMaximumVelocity(100000.0);
		ship2 = new Ship(0.0,0.0,10.0,10.0,0.0,30.0,1E19);
		ship2.setMaximumVelocity(300010);
		ship3 = new Ship(100.0,0.0,0.0,0.0,0.0,30.0,1E19);
		ship4 = new Ship(30.0,0.0,10.0,0.0,0.0,10.0,1E19);
		ship5 = new Ship(200.0,200.0,10.0,0.0,0.0,30.0,1E19);
		for (int i = 0; i < 10; i++){
			ship1.reload(new Bullet(0,0,0,0,3));
		}
		for (int i = 0; i < 10; i++){
			bullets.add(new Bullet(0,0,0,0,3));
		}
		for (int i = 0; i < 10; i++){
			ship5.reload(new Bullet(0,0,0,0,3));
		}
	}

	/**
	 * A method which tests whether or not the ships are correctly initialized.
	 * It does so by testing the getMaximumVelocity(), getPosition(), getVelocity(), getRadius() and getHeading()
	 * methods defined in the Ship class.
	 * The test suit involves the Ship objects ship1, ship2, ship3 and ship4, which were defined in the setUp method.
	 */
	@Test
	public void testInitializeShip() {
		// Maximum velocity.
		assertEquals(100000.0,ship1.getMaximumVelocity(),EPSILON);
		assertEquals(300000.0, ship2.getMaximumVelocity(),EPSILON);

		// Ship 1
		assertEquals(1.5,ship1.getPosition().getX(),EPSILON);
		assertEquals(15.0,ship1.getPosition().getY(),EPSILON);
		assertEquals(10.0,ship1.getVelocity().getX(),EPSILON);
		assertEquals(20.0,ship1.getVelocity().getY(),EPSILON);
		assertEquals(30.0,ship1.getRadius(),EPSILON);
		assertEquals(Math.PI,ship1.getHeading(),EPSILON);

		// Ship 2
		assertEquals(0.0,ship2.getPosition().getX(),EPSILON);
		assertEquals(0.0,ship2.getPosition().getY(),EPSILON);
		assertEquals(10.0,ship2.getVelocity().getX(),EPSILON);
		assertEquals(10.0,ship2.getVelocity().getY(),EPSILON);
		assertEquals(30.0,ship2.getRadius(),EPSILON);
		assertEquals(0.0,ship2.getHeading(),EPSILON);

		// Ship 3
		assertEquals(100.0,ship3.getPosition().getX(),EPSILON);
		assertEquals(0.0,ship3.getPosition().getY(),EPSILON);
		assertEquals(0.0,ship3.getVelocity().getX(),EPSILON);
		assertEquals(0.0,ship3.getVelocity().getY(),EPSILON);
		assertEquals(30.0,ship3.getRadius(),EPSILON);
		assertEquals(0.0,ship3.getHeading(),EPSILON);

		// Ship 4
		assertEquals(30.0,ship4.getPosition().getX(),EPSILON);
		assertEquals(0.0,ship4.getPosition().getY(),EPSILON);
		assertEquals(10.0,ship4.getVelocity().getX(),EPSILON);
		assertEquals(0.0,ship4.getVelocity().getY(),EPSILON);
		assertEquals(10.0,ship4.getRadius(),EPSILON);
		assertEquals(0.0,ship4.getHeading(),EPSILON);
	}

	/**
	 * A test suit which tests the isValidAngle() method from the Ship class.
	 * It involves the Ship ship1 and an angle.
	 * The test only tests legal outcomes of the isValidAngle() method.
	 */
	@Test
	public void isValidAngle_LegalCase() {
		assertTrue(ship1.isValidAngle(Math.PI));
		assertTrue(ship1.isValidAngle(0));

	}

	/**
	 * A test suit which tests the isValidAngle() method from the Ship class.
	 * It involves the Ship ship1 and an angle.
	 * The test only tests illegal outcomes of the isValidAngle() method.
	 */
	@Test
	public void isValidAngle_IllegalCase() {
		assertFalse(ship1.isValidAngle(-Math.PI));
		assertFalse(ship1.isValidAngle(3*Math.PI));
	}

	/**
	 * A test suit which test whether or not the initializer of the Ship class throws the correct exception if the ship
	 * is initialized with an illegal radius.
	 *
	 * @throws 	Exception
	 * 			An exception is thrown if the ship is initialized with an illegal radius.
	 * 			If the test succeeds, the exception is thrown.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void radius_IllegalCase() throws Exception {
		ship1 = new Ship(1.5,15.0,10.0,20.0,5.0,Math.PI,100000000);

	}

	/**
	 * A test suit which test whether or not the initializer of the Ship class throws the correct exception if the ship
	 * is initialized with a radius of NaN.
	 *
	 * @throws 	Exception
	 * 			An exception is thrown if the ship is initialized with a radius of NaN.
	 * 			If the test succeeds, the exception is thrown.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void radiusNaN() throws Exception {
		ship1 = new Ship(1.5,15.0,10.0,20.0,Double.NaN,Math.PI,100000000);

	}

	/**
	 * A test suit which tests the isValidRadius() method from the Ship class.
	 * It involves the Ship ship1 and a radius.
	 */
	@Test
	public void isValidRadiusTest() {
		assertTrue(ship1.isValidRadius(10));
		assertTrue(ship1.isValidRadius(100));
		assertFalse(ship1.isValidRadius(5));
		assertFalse(ship1.isValidRadius(Math.sqrt(-1)));
	}

	/**
	 * A test suit which tests if a move is illegal.
	 * A move is illegal is the timeDifference argument is less then zero.
	 * | timeDifference < 0.
	 *
	 * @throws 	Exception
	 * 			An exception is thrown if the time difference is less than zero.
	 * 			| timeDifference < 0
	 */
	@Test(expected = IllegalArgumentException.class)
	public void move_IllegalCase() throws Exception {
		ship1.move(-5);
	}

	/**
	 * A test suit which tests the move() method from the Ship class.
	 * It involves the Ship ship2 and a time difference.
	 * The test only tests legal outcomes of the move() method.
	 */
	@Test
	public void move_LegalCase() {
		ship2.move(10);
		assertEquals(100.0,ship2.getPosition().getX(),EPSILON);
		assertEquals(100.0,ship2.getPosition().getY(),EPSILON);
	}

	/**
	 * A test suit which tests the turn() method from the Ship class.
	 * It involves the Ship ship2 and an angle.
	 */
	@Test
	public void testTurn(){
		ship2.turn(Math.PI);
		assertEquals(Math.PI,ship2.getHeading(),EPSILON);
		ship2.turn(Math.PI);
		assertEquals(0.0,ship2.getHeading(),EPSILON);
		ship2.turn(-Math.PI);
		assertEquals(Math.PI,ship2.getHeading(),EPSILON);
	}

	/**
	 * A test suit which tests the thrust() method from the Ship class.
	 * It involves the Ship ship1 and the size of an added velocity.
	 */
	@Test
	public void testThrust(){
		ship2.thrust(1);
		assertEquals(120.0,ship2.getVelocity().getX(),EPSILON);
		assertEquals(10.0,ship2.getVelocity().getY(),EPSILON);
		ship2.thrust(-1);
		assertEquals(10.0,ship2.getVelocity().getX(),EPSILON);
		assertEquals(10.0,ship2.getVelocity().getY(),EPSILON);

	}

	/**
	 * A test suit which tests the getDistanceBetween() method from the Ship class.
	 * It involves the Ships ship1 and ship2.
	 */
	@Test
	public void testGetDistanceBetween() {
		assertEquals(0.0,ship2.getDistanceBetween(ship2),EPSILON);
		assertEquals(40.0,ship2.getDistanceBetween(ship3),EPSILON);

	}
	/**
	 * A test suit which tests the overlap() method from the Ship class.
	 * It involves the Ships ship1 and ship2.
	 */
	@Test
	public void testOverlap() {
		assertFalse(ship2.overlap(ship3));
		assertTrue(ship2.overlap(ship4));
		assertTrue(ship2.overlap(ship2));
	}

	/**
	 * A test suit which tests the willCollide() method from the Ship class.
	 * It involves the Ships ship1 and ship2.
	 */
	@Test
	public void testWillCollide() {
		assertTrue(ship3.willCollide(ship4));
		assertFalse(ship2.willCollide(ship3));
	}

	/**
	 * A test suit which tests the getTimeToCollision() method from the Ship class.
	 * It involves the Ships ship1 and ship2.
	 */
	@Test
	public void testGetCollisionTime() {
		assertEquals(Double.POSITIVE_INFINITY,ship2.getTimeToCollision(ship3),EPSILON);
		assertEquals(3.0,ship4.getTimeToCollision(ship3),EPSILON);
	}

	/**
	 * A test suit which tests the getCollisionPosition() method from the Ship class.
	 * It involves the Ships ship1 and ship2.
	 */
	@Test
	public void testGetCollisionPosition() {
		assertNull(ship2.getCollisionPosition(ship3));
		assertEquals(70.0,ship3.getCollisionPosition(ship4).getX(), EPSILON);
		assertEquals(0.0, ship3.getCollisionPosition(ship4).getY(),EPSILON);
	}

	/**
	 * A test suit which tests the reload() method for a single bullet from the Ship class.
	 * It involves the Ship ship1.
	 */
	@Test
	public void testReloadOne(){
		assertEquals(10, ship1.getBullets().size(),EPSILON);
		assertTrue(ship1.getTotalMass() > 1E19);
		Bullet bullet = ship1.getRandomBulletOnShip();
		assertTrue(ship1.getPosition() == bullet.getPosition());
		assertTrue(ship1.getVelocity() == bullet.getVelocity());
		assertTrue(bullet.getWorld() == null);
		assertFalse(bullet.hasBeenOutOfShip());
		assertTrue(bullet.getSource() == ship1);
	}

	/**
	 * A test suit which tests the reload() method for multiple bullets from the Ship class.
	 * It involves the Ships ship2.
	 */
	@Test
	public void testReloadMultiple(){
		ship2.reload(bullets);
		assertEquals(10, ship2.getBullets().size(),EPSILON);
		assertTrue(ship2.getTotalMass() > 1E19);
		Bullet bullet = ship2.getRandomBulletOnShip();
		assertTrue(ship2.getPosition() == bullet.getPosition());
		assertTrue(ship2.getVelocity() == bullet.getVelocity());
		assertTrue(bullet.getWorld() == null);
		assertFalse(bullet.hasBeenOutOfShip());
		assertTrue(bullet.getSource() == ship2);
	}

	/**
	 * A test suit which tests the getAcceleration() method from the Ship class.
	 * It involves the Ships ship1 and ship2.
	 */
	@Test
	public void testAcceleration(){
		assertEquals(1.1E2, ship2.getAcceleration(), EPSILON);
		assertTrue(ship1.getAcceleration() < 1.1E2);
	}

	/**
	 * A test suit which tests the getThrusterState() method from the Ship class.
	 * It involves the Ships ship1.
	 */
	@Test
	public void testThruster(){
		assertFalse(ship1.getThrusterState());
		ship1.thrustOn();
		assertTrue(ship1.getThrusterState());
		ship1.thrustOff();
		assertFalse(ship1.getThrusterState());
	}

	/**
	 * A test suit which tests the getTotalMass() method from the Ship class.
	 * It involves the Ships ship1 and ship2.
	 */
	@Test
	public void testMass(){
		assertEquals(1E19, ship2.getTotalMass(),EPSILON);
		assertTrue(1E19 < ship1.getTotalMass());
	}

	/**
	 * A test suit which tests the fire() method from the Ship class.
	 * It involves the Ships ship5.
	 */
	@Test
	public void testFire(){
		World world = new World(800,800);
		world.addEntity(ship5);
		assertEquals(10, ship5.getBullets().size(),EPSILON);
		ship5.fire();
		assertEquals(2, world.getAllEntities().size(),EPSILON);
		assertEquals(9, ship5.getBullets().size(),EPSILON);
	}
}
