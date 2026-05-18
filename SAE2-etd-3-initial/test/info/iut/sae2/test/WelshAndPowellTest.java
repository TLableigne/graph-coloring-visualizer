package info.iut.sae2.test;

import info.iut.sae2.algorithm.WelshAndPowell;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.Graph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.properties.ColorProperty;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *Test class for the Welsh and Powell Algorithm
 * @author zakariasabri
 */
public class WelshAndPowellTest {
    
    /**
     * Tests a simple graph triangle
     * All three nodes are connected to each other, so they must have a different color
     */
    @Test 
    public void testGrapheTriangle() { // If the tests do not pass, the user should manualy import the two tests libraries from the lib folder of this repository (junit-4.13.2.jar and hamcrest-core-1.3.jar)
        Graph g = new Graph();
        
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();

        g.addEdge(n1, n2);
        g.addEdge(n2, n3);
        g.addEdge(n1, n3);

        WelshAndPowell wp = new WelshAndPowell();
        ColorProperty computedColors = wp.apply(g, null);

        Color c1 = computedColors.getNodeValue(n1);
        Color c2 = computedColors.getNodeValue(n2);
        Color c3 = computedColors.getNodeValue(n3);

        assertNotNull("Le noeud 1 devrait avoir une couleur", c1);
        assertNotNull("Le noeud 2 devrait avoir une couleur", c2);
        assertNotNull("Le noeud 3 devrait avoir une couleur", c3);

        
        assertFalse("n1 et n2 sont reliés, ils ne doivent pas avoir la même couleur", c1.equals(c2));
        assertFalse("n1 et n3 sont reliés, ils ne doivent pas avoir la même couleur", c1.equals(c3));
        assertFalse("n2 et n3 sont reliés, ils ne doivent pas avoir la même couleur", c2.equals(c3));
    }
    
    
    /***
     * Tests a disconnected graph
     * The nodes with no edges should idealy be assigned to the same first available color.
     */
    @Test
    public void testDisconnectedGraph() {
        Graph g = new Graph();
        
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();

        WelshAndPowell wp = new WelshAndPowell();
        ColorProperty computedColors = wp.apply(g, null);

        Color c1 = computedColors.getNodeValue(n1);
        Color c2 = computedColors.getNodeValue(n2);
        Color c3 = computedColors.getNodeValue(n3);

        assertNotNull("Node 1 should have a color", c1);
        assertFalse("All disconnected nodes should share the exact same first color", c1.equals(c2) && c2.equals(c3));
    }
}
