/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.graphs;

/**
 *
 * @author rbourqui
 */
public class Color {
    
    public int r, g, b, a;
    
    /**
     * Default constructor.
     */
    public Color(){
        this(0,0,0,0);
    }
    
    /**
     * Copy constructor.
     *
     * @param c the Color to be copied
     */
    public Color(Color c){
        this(c.r, c.g, c.b, c.a);
    }
    
    /**
     * Constructor with given red, green, blue and alpha channels.
     *
     * @param r red channel of the Color
     * @param g green channel of the Color
     * @param b blue channel of the Color
     * @param a alpha channel of the Color
     */
    public Color(int r, int g, int b, int a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    
    /**
     * Returns the red channel of the Color.
     * 
     * @return the red channnel
     */
    public int getR(){
        return this.r;
    }
    
    /**
     * Returns the green channel of the Color.
     * 
     * @return the green channnel
     */
    public int getG(){
        return this.g;
    }
    
    /**
     * Returns the blue channel of the Color.
     * 
     * @return the blue channnel
     */
    public int getB(){
        return this.b;
    }
    
    /**
     * Returns the alpha channel of the Color.
     * 
     * @return the alpha channnel
     */
    public int getA(){
        return this.a;
    }
    
    /**
     * Set the red channel of the Color to the given value.
     * 
     * @param r the red channnel
     */
    public void setR(int r){
        this.r = r;
    }
    
    /**
     * Set the green channel of the Color to the given value.
     * 
     * @param g the green channnel
     */
    public void setG(int g){
        this.g = b;
    }
    
    /**
     * Set the blue channel of the Color to the given value.
     * 
     * @param b the blue channnel
     */
    public void setB(int b){
        this.b = b;
    }
    
    /**
     * Set the alpha channel of the Color to the given value.
     * 
     * @param a the alpha channnel
     */
    public void setA(int a){
        this.a = a;
    }
    
    /**
     * Returns a new Color result of a linear interpolation between the start and end colors given the current step and the number of steps.
     * 
     * @param start start color of the interpolation
     * @param end end color of the interpolation
     * @param step current step
     * @param nbSteps total number of steps
     * @return the interpolated color
     */
    public static Color interpolate(Color start, Color end, int step, int nbSteps){
        return new Color(start.r + (end.r-start.r)*step / nbSteps, start.g + (end.g-start.g)*step / nbSteps, start.b + (end.b-start.b)*step / nbSteps, start.a + (end.a-start.a)*step / nbSteps);
    }
    
    @Override 
    public String toString(){
        return "("+ r + ", " + g + ", " + b + ", " + a + ")";
    }
}
