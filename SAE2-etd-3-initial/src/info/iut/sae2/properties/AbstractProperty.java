/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.properties;

import info.iut.sae2.graphs.IEdge;
import info.iut.sae2.graphs.INode;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rbourqui
 */
public abstract class AbstractProperty<T, U> implements IProperty<T, U> {

    /**
     * Map use to store the mapping between nodes and values
     */
    Map<INode, T> nodesValues;
    
    /**
     * Map use to store the mapping between edges and values
     */
    Map<IEdge, U> edgesValues;

    /**
     * Default constructor ofg AbstractProperty
     */
    public AbstractProperty() {
        nodesValues = new HashMap<>();
        edgesValues = new HashMap<>();
    }

    @Override
    public T getNodeValue(INode n) {
        return nodesValues.get(n);
    }

    @Override
    public U getEdgeValue(IEdge e) {
        return edgesValues.get(e);
    }

    @Override
    public abstract void setNodeValue(INode n, T val);

    @Override
    public abstract void setEdgeValue(IEdge e, U val);

    @Override
    public void setAllNodesValues(T val) {
        for (INode n : nodesValues.keySet()) {
            setNodeValue(n, val);
        }
    }

    @Override
    public void setAllEdgesValues(U val) {
        for (IEdge e : edgesValues.keySet()) {
            setEdgeValue(e, val);
        }
    }

    @Override
    public void delNode(INode n) {
        assert nodesValues.containsKey(n);
        nodesValues.remove(n);
    }

    @Override
    public void delEdge(IEdge e) {
        assert edgesValues.containsKey(e);
        edgesValues.remove(e);
    }

}
