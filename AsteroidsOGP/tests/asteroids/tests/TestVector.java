package asteroids.tests;

import static org.junit.Assert.*;
import asteroids.model.Vector;
import org.junit.Test;

/**
 * A class containing test suits for the Vector class.
 *
 * @author   WimKunnen and Maarten Doclo.
 *
 * @version  1.0
 */
public class TestVector {

    private static Vector u = new Vector();
    private static Vector v = new Vector(3,4);
    private static Vector x = new Vector(2,3);
    private static Vector h = new Vector(1,2);
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

    @Test
    public void testGetValues(){
        assertArrayEquals(array1, v.getValues(), EPSILON);
        assertArrayEquals(array2, x.getValues(), EPSILON);
    }
}
