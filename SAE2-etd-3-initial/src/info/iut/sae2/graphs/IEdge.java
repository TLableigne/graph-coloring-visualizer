/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package info.iut.sae2.graphs;

/**
 *
 * @author rbourqui
 */
public interface IEdge {
    /**
     * Returns the source of the edge
     * 
     * @return the source of the edge
     */
    public INode source();
    
    /**
     * Returns the target of the edge
     * 
     * @return the target of the edge
     */
    public INode target();
}
