/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.viewer;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author rbourqui
 */
public class NodeView {
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */ 
    int radius;
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    Color color;
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    int x;
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    int y;
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    NodeView (int x, int y, int radius, Color color){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    void updateRadius(int radius) {
        this.radius = radius;
    }
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    void updatePosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    void updateColor(Color c){
        this.color = c;
    }
        
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    void draw(Graphics2D img){
        img.setColor(this.color);
        img.fillOval(this.x, this.y, this.radius*2, this.radius*2);
    }
    
}
