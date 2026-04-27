/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.properties;

import info.iut.sae2.graphs.IEdge;
import info.iut.sae2.graphs.INode;

/**
 *
 * @author rbourqui
 */
public interface IProperty<T, U> {

    /**
     * Return the value associated to a given node
     *
     * @param n the node of interest
     * @return the value associated to the node of interest
     */
    public T getNodeValue(INode n);

    /**
     * Return the value associated to a given edge
     *
     * @param e the edge of interest
     * @return the value associated to the node of interest
     */
    public U getEdgeValue(IEdge e);

    /**
     * Set the value associated to a given node
     *
     * @param n the node of interest
     * @param val the value to associate to the node of interest
     */
    public void setNodeValue(INode n, T val);

    /**
     * Set the value associated to a given edge
     *
     * @param e the edge of interest
     * @param val the value to associate to the edge of interest
     */
    public void setEdgeValue(IEdge e, U val);

    /**
     * Set the value associated to all the nodes
     *
     * @param val the value to associate to the nodes
     */
    public void setAllNodesValues(T val);

      /**
     * Set the value associated to all the edges
     *
     * @param val the value to associate to the edges
     */
    public void setAllEdgesValues(U val);

    /**
     * Remove a given node from the property
     * 
     * @param n the node to be removed
     */
    public void delNode(INode n);

    /**
     * Remove a given edge from the property
     * 
     * @param e the edge to be removed
     */
    public void delEdge(IEdge e);
}
