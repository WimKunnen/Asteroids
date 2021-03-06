package asteroids.tests;

import static org.junit.Assert.*;
import asteroids.model.Vector;
import org.junit.Test;

/**
 * A class containing test suits for the Vector class.
 *
 * @author   WimKunnen and Maarten Doclo.
 *
 * @version  3.0
 */
public class TestVector {

    private static Vector u = new Vector();
    private static Vector v = new Vector(3,4);
    private static Vector x = new Vector(2,3);
    private static Vector h = new Vector(1,2);
    private static Vector w = new Vector(3,4);
    private static Vector y = new Vector(-3,-4);

    private static final double EPSILON = 0.0001;
    private static double[] array1 = new double[]{3,4};
    private static double[] array2 = new double[]{2,3};

    /**
     * A method which tests the getX() method from the Vector class.
     * The tests are run with the previously defined vector objects u and v.
     */
    @Test
    public final void testGetX(){
        assertTrue(0 == u.getX());
        assertTrue(3 == v.getX());
    }

    /**
     * A method which tests the getY() method from the Vector class.
     * The tests are run with the previously defined vector objects u and v.
     */
    @Test
    public void testGetY(){
        assertTrue(0 == u.getY());
        assertTrue(4 == v.getY());
    }

    /**
     * A method which tests the vectorLength() method from the Vector class.
     * The tests are run with the previously defined vector objects u and v.
     */
    @Test
    public void testVectorLength(){
        assertTrue(0 == u.vectorLength());
        assertTrue(5 ==  v.vectorLength());
    }

    /**
     * A method which tests the vectorLengthSquared() method from the Vector class.
     * The tests are run with the previously defined vector objects u and v.
     */
    @Test
    public void testVectorLengthSquared(){
        assertTrue(0 == u.vectorLengthSquared());
        assertTrue(25 ==  v.vectorLengthSquared());
    }

    /**
     * A method which tests the sum() method from the Vector class.
     * The tests are run with the previously defined vector objects u, v and h.
     */
    @Test
    public void testSum(){
        assertTrue(3 == u.sum(v).getX());
        assertTrue(4 == u.sum(v).getY());
        assertTrue(4 == h.sum(v).getX());
        assertTrue(6 == h.sum(v).getY());
    }

    /**
     * A method which tests the normalize() method from the Vector class.
     * The tests are run with the previously defined vector objects u and v.
     */
    @Test
    public void testNormalize(){
        assertTrue(0 == u.normalize().vectorLengthSquared());
        assertTrue(1 == v.normalize().vectorLengthSquared());
    }

    /**
     * A method which tests the resizeVector () method from the Vector class.
     * The tests are run with the previously defined vector objects u, v and x.
     */
    @Test
    public void testResize(){
        assertTrue(0 == u.normalize().resizeVector(5).vectorLengthSquared());
        assertTrue(25 == v.normalize().resizeVector(5).vectorLengthSquared());
        assertTrue(0 == x.normalize().resizeVector(0).vectorLengthSquared());
    }

    /**
     * A method which tests the scalarProduct() method from the Vector class.
     * The tests are run with the previously defined vector objects u and h.
     */
    @Test
    public void testScalarProduct(){
        assertTrue(0 == u.scalarProduct(v));
        assertTrue(11 == h.scalarProduct(v));
    }

    /**
     * A method which tests the getValues() method from the Vector class.
     * The tests are run with the previously defined vector objects v and x and the arrays array1 and array2.
     */
    @Test
    public void testGetValues(){
        assertArrayEquals(array1, v.getValues(), EPSILON);
        assertArrayEquals(array2, x.getValues(), EPSILON);
    }

    /**
     * A method which tests the equals() method from the Vector class.
     * The tests are run with the previously defined vector objects v, w, u and h.
     */
    @Test
    public void testEquals(){
        assertTrue(v.equals(w));
        assertTrue(v.equals(v));
        assertFalse(u.equals(h));
    }

    /**
     * A test suit which tests the negate() method from the Vector class.
     * The tests are run with the previously defined vector objects v, y and u.
     */
    @Test
    public void testNegate(){
        assertTrue(v.negate().equals(y));
        assertFalse(u.negate().equals(v));
    }

    /**
     * A test suit which tests the hashCode() method from the Vector class.
     * The tests are run with the previously defined vector objects v, w and h.
     */
    @Test
    public void testHashCode(){
        assertEquals(v.hashCode(), 7, EPSILON);
        assertEquals(v.hashCode(), w.hashCode(), EPSILON);
        assertFalse(v.hashCode() == h.hashCode());
    }

    /**
     * A test suit which tests the angleBetween() method from the Vector class.
     * The tests are run with the previously defined vector objects v, x, h and y.
     */
    @Test
    public void testAngleBetween(){
        assertEquals( 0.0555, v.angleBetween(x), EPSILON);
        assertEquals( 0.1243, h.angleBetween(x), EPSILON);
        assertEquals(0, w.angleBetween(v), EPSILON);
        assertEquals(Math.PI, v.angleBetween(y), EPSILON);
    }
}
