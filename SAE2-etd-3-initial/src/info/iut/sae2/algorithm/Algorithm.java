/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.IGraph;
import java.util.Map;

/**
 *
 * @author rbourqui
 */
public interface Algorithm<T> {
    
    /**
     * Compute the result of the algorithm and returns it.
     * @param g the graph on which the algorithm is applied
     * @param parameters map of parameters associating parameters names to parameters values
     * @return the result of the algorithm
     */
    public T apply(IGraph g, Map<String, Object> parameters);
}
