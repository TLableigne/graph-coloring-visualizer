/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.viewer;

import info.iut.sae2.graphs.Coord;
import java.awt.Color;
import java.awt.GradientPaint;

import java.awt.Graphics2D;
import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author rbourqui
 */
public class EdgeView {

    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    int[] polyX;
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    int[] polyY;
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    int[] smoothedX;
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    int[] smoothedY;

    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    boolean smoothed;
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    Color color;

    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    EdgeView(int[] x, int[] y, Color color, boolean smoothed) {
        this.polyX = Arrays.copyOf(x, x.length);
        this.polyY = Arrays.copyOf(y, y.length);
        this.color = color;
        this.smoothed = smoothed;
        this.smoothPolyline();
    }
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    private Coord bezierCurve(double t) {
        if (t == 0.) {
            return new Coord(this.polyX[0], this.polyY[0]);
        } else if (t == 1.) {
            return new Coord(this.polyX[this.polyX.length - 1], this.polyY[this.polyY.length - 1]);
        }
        double s = (1.0 - t);
        double r = (double) this.polyX.length;
        double curCoeff = 1.0;
        double t2 = 1.0;
        Coord bezierPoint = new Coord();
        for (int i = 0; i < this.polyX.length; ++i) {     
            bezierPoint.add(this.polyX[i] * curCoeff * t2 * pow(s, (double) this.polyX.length - 1 - i), this.polyY[i] * curCoeff * t2 * pow(s, (double) this.polyY.length - 1 - i));
            double c = (double) (i + 1);
            curCoeff *= (r - c) / c;
            t2 *= t;
        }
        return bezierPoint;
    }
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    private void smoothPolyline() {
        ArrayList<Coord> newBends = new ArrayList<>();
        if (polyX.length > 2) {
            int nPoints = 4 * this.polyX.length;
            for (int i = 0; i < nPoints; ++i) {
                Coord point = bezierCurve((double) i / nPoints);
                newBends.add(point);
            }
        } else {
            newBends.add(new Coord(polyX[0], polyY[0]));
            newBends.add(new Coord(polyX[1], polyY[1]));
        }
        this.smoothedX = new int[newBends.size()];
        this.smoothedY = new int[newBends.size()];

        for (int i = 0; i < this.smoothedX.length; ++i) {
            this.smoothedX[i] = (int) newBends.get(i).getX();
            this.smoothedY[i] = (int) newBends.get(i).getY();
        }
    }

    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    void draw(Graphics2D img) {
        int[] curX;
        int[] curY;
        if (!this.smoothed) {
            curX = this.polyX;
            curY = this.polyY;
        } else {
            curX = smoothedX;
            curY = smoothedY;
        }
        img.setColor(this.color); 
        img.drawPolyline(curX, curY, curX.length);
    }
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    void updatePosition(int[] x, int[] y) {
        this.polyX = Arrays.copyOf(x, x.length);
        this.polyY = Arrays.copyOf(y, y.length);
        this.smoothPolyline();
    }
 
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    void updateTransparency(int transparency){
        this.color = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), transparency);
    }
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
    */
    void updateColor(Color c){
        this.color = c;
    }   
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */void updateSmoothed(boolean smoothed){
        this.smoothed = smoothed;
    }
}
