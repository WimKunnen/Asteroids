package asteroids.tests;

import static org.junit.Assert.*;
import asteroids.model.Vector;
import org.junit.Test;

/**
 * @author WimKunnen and Maarten Doclo.
 */
public class TestVector {

    private static Vector u = new Vector();
    private static Vector v = new Vector(3,4);

    @Test
    public final void testGetX(){
        assertTrue(0 == u.getX());
        assertTrue(3 == v.getX());
    }

    @Test
    public void testGetY(){
        assertTrue(0 == u.getY());
        assertTrue(4 == v.getY());
    }

    @Test
    public void testVectorLength(){
        assertTrue(0 == u.vectorLength());
        assertTrue(5 ==  v.vectorLength());
    }

    @Test
    public void testVectorLengthSquared(){
        assertTrue(0 == u.vectorLengthSquared());
        assertTrue(25 ==  v.vectorLengthSquared());
    }

    @Test
    public void testSum(){
        assertTrue(3 == u.sum(v).getX());
        assertTrue(4 ==  u.sum(v).getY());

        Vector h = new Vector(1,2);
        assertTrue(4 == h.sum(v).getX());
        assertTrue(6 == h.sum(v).getY());
    }

    @Test
    public void testNormalize(){
        assertTrue(0 == u.normalize().vectorLengthSquared());
        assertTrue(1 == v.normalize().vectorLengthSquared());
    }

    @Test
    public void testResize(){
        Vector h = new Vector(2,3);

        assertTrue(0 == u.normalize().resizeVector(5).vectorLengthSquared());
        assertTrue(25 == v.normalize().resizeVector(5).vectorLengthSquared());
        assertTrue(0 == h.normalize().resizeVector(0).vectorLengthSquared());
    }

    @Test
    public void testScalarProduct(){
        assertTrue(0 == u.scalarProduct(v));

        Vector h = new Vector(1,2);
        assertTrue(11 == h.scalarProduct(v));
    }
}
