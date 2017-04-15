package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Value;

/**
 * A class of two dimensional vectors used to create an adaptation of the Atari game 'Asteroids'.
 *
 * @invar   The size of a vector will always be nonnegative.
 *          | vectorLength >= 0
 *
 * @invar   The x component will never change.
 *          | new.getX() == this.geX()
 *
 * @invar   The y component will never change.
 *          | new.getY() == this.geY()
 *
 * @author  Wim Kunnen and Maarten Doclo.
 *
 * @version 2.0
 */
@Value
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

    /**
     * Variable registering the x component of this vector.
     */
    private final double x;

    /**
     * Variable registering the y component of this vector.
     */
    private final double y;

    /**
     * Returns the x component of the vector.
     */
    @Basic @Immutable
    public double getX(){
        return this.x;
    }

    /**
     * Returns the y component of the vector.
     */
    @Basic @Immutable
    public double getY(){
        return this.y;
    }

    /**
     * Returns the scalar product of two vectors.
     *
     * @param   other
     *          The other vector which will be used to calculate a scalar product of two vectors.
     */
    public double scalarProduct(Vector other){
        return this.getX() * other.getX() + this.getY() * other.getY();
    }

    /**
     * Returns the square of the length of the vector.
     *
     * @see implementation
     */
    public double vectorLengthSquared(){
        return this.getX() * this.getX() + this.getY() * this.getY();
    }

    /**
     * Returns the length of the vector.
     *
     * @see implementation
     */
    public double vectorLength(){
        return Math.sqrt(this.vectorLengthSquared());
    }

    /**
     * Returns the sum of two vectors.
     * | return new Vector(this.getX() + other.getX(), this.getY() + other.getY())
     *
     * @param   other
     *          The other vector which will be used to calculate the sum of two vectors.
     */
    public Vector sum(Vector other){
        return new Vector(this.getX() + other.getX(), this.getY() + other.getY());
    }

    /**
     * Returns a vector of length one in the direction of the original vector.
     * If the original vector's length is zero the original vector is returned.
     *
     * |if vectorLengthSquared > 0 then
     * |     return new Vector(this.getX() / this.vectorLength(), this.getY() / this.vectorLength())
     * |else
     * |     return this
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
     * @param factor
     *        The factor with which the vector needs to be multiplied.
     *
     * |if (this.vectorLengthSquared >= 0)
     * |    then new == Vector(this.getX() * factor, this.getY() * factor)
     * |else
     * |    new == this
     */
    public Vector resizeVector(double factor){

        if(this.vectorLengthSquared() >= 0)
            return new Vector(this.getX() * factor, this.getY() * factor);
        else
            return this;
    }

    /**
     * Returns the x and y values as an array of doubles.
     */
    public double[] getValues(){
        return new double[]{this.getX(),this.getY()};
    }

    /**
     * Returns a new vector with its values negated.
     *
     * @return  result == new Vector(-this.getX, -this.getY)
     */
    public Vector negate() {
        return new Vector(-this.getX(),-this.getY());
    }

    /**
     * Returns the angle between two vectors.
     *
     * @param   other
     *          The other vector with which an angle will be calculated.
     *
     * @return  result == acos(this.scalarProduct(other))
     */
    public double angleBetween(Vector other){
        Vector normalizedThis = this.normalize();
        Vector normalizedOther = other.normalize();
        double dotProduct = normalizedThis.scalarProduct(normalizedOther);
        return Math.acos(dotProduct);
    }

    /**
     * Returns true if and only if the values of the two vectors are equal to each other
     *
     * @param   other
     *          The other vector which will be checked to be equal to the original vector
     */
    @Override
    public boolean equals(Object other){
        if (other == null)
            return false;
        if (this.getClass() != other.getClass())
            return false;
        Vector otherVector = (Vector) other;
        return (this.getX()== otherVector.getX() && this.getY() == otherVector.getY());
    }

    /**
     * A method which calculates the hash code for the vector based on the x and y values.
     *
     * @return  result == getX + getY
     */
    @Override
    public int hashCode(){
        double hashCode = Math.floor(this.getX() + this.getY());
        return (int) hashCode;
    }
}

