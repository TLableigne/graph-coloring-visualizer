/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.graphs;

/**
 *
 * @author rbourqui
 */
public class Coord {

    /**
     * Abscisse and ordinate of the Coord
     */
    public double x, y;

    /**
     * Default constructor
     */
    public Coord() {
        this(0., 0.);
    }

    /**
     * Constructor with a given abscisse and ordinate for the Coord.
     *
     * @param x abscisse of the Coord
     * @param y ordinate of the Coord
     */
    public Coord(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy constructor
     *
     * @param c the coord to be copied
     */
    public Coord(Coord c) {
        this(c.x, c.y);
    }

    /**
     * Returns the abscisse of the Coord
     *
     * @return the abscisse of the Coord
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the ordinate of the Coord
     *
     * @return the ordinate of the Coord
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the abscisse of the coord
     *
     * @param x the new abscisse of the coord
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the ordinate of the coord
     *
     * @param y the new ordinate of the coord
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Returns the distance of the current Coord to a given Coord
     *
     * @param c the other coord
     * @return the distance to the given coord
     */
    public double dist(Coord c) {
        return Math.sqrt((x - c.x) * (x - c.x) + (y - c.y) * (y - c.y));
    }

    /**
     * Returns the norm of the Coord, i.e. the distance to the origine (0, 0) of
     * the coord
     *
     * @return the distance to the origine (0, 0)
     */
    public double norm() {
        return Math.sqrt((x * x) + (y * y));
    }

    /**
     * Multiply each of the abscisse and ordinate by the given scale value
     *
     * @param scalar the value used to multiply with abscisse and ordinate
     */
    public void mult(double scalar) {
        x *= scalar;
        y *= scalar;
    }

    /**
     * Move a coordinate by adding given values to its abscisse and ordinate
     *
     * @param x the value to add to the abscisse
     * @param y the value to add to the ordinate
     */
    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Move a coordinate by adding the coordinate of a given Coord to its
     * abscisse and ordinate
     *
     * @param c the Coord which coordinates are added to the current Coord
     */
    public void add(Coord c) {
        add(c.x, c.y);
    }

    /**
     * Returns a new Coord being the result of the addition of two given Coord
     *
     * @param c1 the first Coord to be added
     * @param c2 the second Coord to be added
     * @return the result of the addition of the Coord
     */
    public static Coord add(Coord c1, Coord c2) {
        return new Coord(c1.x + c2.x, c1.y + c2.y);
    }

    /**
     * Returns a new Coord being the difference of two given Coord
     *
     * @param c1 the first Coord
     * @param c2 the Coord to be substracted
     * @return the difference between the two Coord
     */
    public static Coord minus(Coord c1, Coord c2) {
        return new Coord(c1.x - c2.x, c1.y - c2.y);
    }

    /**
     * Returns a new Coord being the multiplication of a given Coord by a given
     * scalar value
     *
     * @param c1 the Coord to be multiplied
     * @param scalar the value used to multiply with abscisse and ordinate
     * @return the resulting Coord
     */
    public static Coord mult(Coord c1, double scalar) {
        return new Coord(scalar * c1.x, scalar * c1.y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
