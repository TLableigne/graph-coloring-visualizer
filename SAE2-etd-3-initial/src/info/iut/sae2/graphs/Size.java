/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.graphs;

/**
 *
 * @author rbourqui
 */
public class Size {

    /**
     * Width and height of the Size
     */
    public double w, h;

    /**
     * Default constructor
     */
    public Size() {
        this(0., 0.);
    }

    /**
     * Constructor with a given width and height for the Size.
     *
     * @param w width of the Size
     * @param h height of the Size
     */
    public Size(double w, double h) {
        this.w = w;
        this.h = h;
    }

    /**
     * Copy constructor
     *
     * @param s the size to be copied
     */
    public Size(Size s) {
        this(s.w, s.h);
    }

    /**
     * Returns the height of the size
     *
     * @return the height of the size
     */
    public double getH() {
        return h;
    }

    /**
     * Returns the width of the size
     *
     * @return the width of the size
     */
    public double getW() {
        return w;
    }

    /**
     * Sets the width of the size
     *
     * @param w the new width of the size
     */
    public void setW(int w) {
        this.w = w;
    }

    /**
     * Sets the height of the size
     *
     * @param h the new height of the size
     */
    public void setH(int h) {
        this.h = h;
    }

    @Override
    public String toString() {
        return "(" + w + ", " + h + ")";
    }
}
