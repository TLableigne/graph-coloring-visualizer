/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.properties;

import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.IEdge;
import info.iut.sae2.graphs.INode;
import java.util.Map.Entry;

/**
 *
 * @author rbourqui
 */
public class ColorProperty extends AbstractProperty<Color, Color>{

    final public static Color DEFAULT_NODE_COL = new Color(255,0,0,255);
    
    final public static Color DEFAULT_EDGE_COL = new Color(0,0,0,60);

    /**
     * Default constructor of ColorProperty
     */
    public ColorProperty() { }

    /** 
     * Copy constructor. Each value of the given ColorProperty are copied before being associated to the nodes and edges.
     * 
     * @param color the ColorProperty to be copied
     */
    public ColorProperty(ColorProperty color) {
        for (Entry e : color.nodesValues.entrySet()) {
            nodesValues.put((INode) e.getKey(), new Color((Color) e.getValue()));
        }
        for (Entry e : color.edgesValues.entrySet()) {
            edgesValues.put((IEdge) e.getKey(), new Color((Color) e.getValue()));
        }
    }

    @Override
    public void setNodeValue(INode n, Color col) {
        nodesValues.put(n, new Color(col));
    }

    @Override
    public void setEdgeValue(IEdge e, Color col) {
        edgesValues.put(e, new Color(col));
    }
    
}
