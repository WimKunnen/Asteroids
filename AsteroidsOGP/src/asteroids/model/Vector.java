package asteroids.model;

/**
 * A class of two dimensional vectors.
 *
 * @invar   The size of a vector will always be nonnegative.
 *          | vectorLength >= 0
 *
 * @author Wim Kunnen and Maarten Doclo.
 */
public class Vector {

    /**
     * Initializes a new Vector.
     *
     * @param   x
     *          The x component of the vector.
     *
     * @param   y
     *          The y component of the vector.
     */
    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Default initializer for the Vector class.
     */
    public Vector(){
        this.x = 0;
        this.y = 0;
    }

    public final double x;

    public final double y;

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    /**
     * Returns the scalar product of two vectors.
     *
     */
    public double scalarProduct(Vector other){
        return this.getX() * other.getX() + this.getY() * other.getY();
    }

    /**
     * Returns the square of the length of the vector.
     */
    public double vectorLengthSquared(){
        return this.getX() * this.getX() + this.getY() * this.getY();
    }

    /**
     * Returns the length of the vector.
     */
    public double vectorLength(){
        return Math.sqrt(this.vectorLengthSquared());
    }

    /**
     * Returns the sum of two vectors.
     *
     */
    public Vector sum(Vector other){
        return new Vector(this.getX() + other.getX(), this.getY() + other.getY());
    }

    /**
     * Returns a vector of length one in the direction of the original vector.
     * If the original vector's length is zero the original vector is returned.
     *
     */
    public Vector normalize() {
        if(this.vectorLengthSquared() > 0)
            return new Vector(this.getX() / this.vectorLength(), this.getY() / this.vectorLength());
        else
            return this;
    }

    /**
     * Rescales a vector with the given factor if factor is greater than zero.
     * Else, the original Vector is returned.
     *
     * if (this.vectorLengthSquared >= 0)
     *  then new == Vector(this.getX() * factor, this.getY() * factor)
     * else
     *  new == this
     */
    public Vector resizeVector(double factor){

        if(this.vectorLengthSquared() >= 0)
            return new Vector(this.getX() * factor, this.getY() * factor);
        else
            return this;
    }
}
