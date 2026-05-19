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
    public void testTriangleGraph() { // If the tests do not pass, the user should manualy import the two tests libraries from the lib folder of this repository (junit-4.13.2.jar and hamcrest-core-1.3.jar)
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

        assertNotNull("Node 1 should have a color", c1);
        assertNotNull("Node 2 should have a color", c2);
        assertNotNull("Node 3 should have a color", c3);
        
        assertFalse("n1 and n2 are connected, they must not share the same color", 
            c1.getR() == c2.getR() && c1.getG() == c2.getG() && c1.getB() == c2.getB());
            
        assertFalse("n1 and n3 are connected, they must not share the same color", 
            c1.getR() == c3.getR() && c1.getG() == c3.getG() && c1.getB() == c3.getB());
            
        assertFalse("n2 and n3 are connected, they must not share the same color", 
            c2.getR() == c3.getR() && c2.getG() == c3.getG() && c2.getB() == c3.getB());
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
        
        assertTrue("All disconnected nodes should share the exact same first color", 
            (c1.getR() == c2.getR() && c1.getG() == c2.getG() && c1.getB() == c2.getB()) && 
            (c2.getR() == c3.getR() && c2.getG() == c3.getG() && c2.getB() == c3.getB()));
    
    }
    
    /**
     * Tests a line graph (Path graph) to check color reusability.
     * Graph: n1 - n2 - n3 - n4
     * n1 and n3 can share a color. n2 and n4 can share a color.
     */
    @Test
    public void testLineGraphReusability() {
        Graph g = new Graph();
        
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();
        INode n4 = g.addNode();

        g.addEdge(n1, n2);
        g.addEdge(n2, n3);
        g.addEdge(n3, n4);

        WelshAndPowell wp = new WelshAndPowell();
        ColorProperty computedColors = wp.apply(g, null);

        Color c1 = computedColors.getNodeValue(n1);
        Color c2 = computedColors.getNodeValue(n2);
        Color c3 = computedColors.getNodeValue(n3);
        Color c4 = computedColors.getNodeValue(n4);

        assertFalse("n1 and n2 must have different colors", 
            c1.getR() == c2.getR() && c1.getG() == c2.getG() && c1.getB() == c2.getB());
            
        assertFalse("n2 and n3 must have different colors", 
            c2.getR() == c3.getR() && c2.getG() == c3.getG() && c2.getB() == c3.getB());
            
        assertFalse("n3 and n4 must have different colors", 
            c3.getR() == c4.getR() && c3.getG() == c4.getG() && c3.getB() == c4.getB());
    }
    
    
    /**
     * Tests an empty graph to ensure the algorithm does not crash.
     * It should simply return an empty ColorProperty without throwing exceptions.
     */
    @Test
    public void testEmptyGraph() {
        Graph g = new Graph();
        WelshAndPowell wp = new WelshAndPowell();
        
        try {
            ColorProperty computedColors = wp.apply(g, null);
            assertNotNull("The algorithm should return a valid ColorProperty even for an empty graph", computedColors);
        } catch (Exception e) {
            fail("The algorithm threw an exception on an empty graph: " + e.getMessage());
        }
    }
    
    
    /**
     * Tests a Star Graph.
     * One central node is connected to multiple surrounding nodes.
     * The central node should get one color, and all disconnected outer nodes should share a second color.
     */
    @Test
    public void testStarGraph() {
        Graph g = new Graph();
        INode center = g.addNode();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();

        // connect center to all outer nodes
        g.addEdge(center, n1);
        g.addEdge(center, n2);
        g.addEdge(center, n3);

        WelshAndPowell wp = new WelshAndPowell();
        
        ColorProperty computedColors = wp.apply(g, null);

        Color cCenter = computedColors.getNodeValue(center);
        Color c1 = computedColors.getNodeValue(n1);
        Color c2 = computedColors.getNodeValue(n2);
        Color c3 = computedColors.getNodeValue(n3);

        // the center must be different from all outer nodes
        assertFalse("Center must differ from n1", 
            cCenter.getR() == c1.getR() && cCenter.getG() == c1.getG() && cCenter.getB() == c1.getB());
        assertFalse("Center must differ from n2", 
            cCenter.getR() == c2.getR() && cCenter.getG() == c2.getG() && cCenter.getB() == c2.getB());
        assertFalse("Center must differ from n3", 
            cCenter.getR() == c3.getR() && cCenter.getG() == c3.getG() && cCenter.getB() == c3.getB());

        // outer nodes are not connected to each other, they should all share the same color
        assertTrue("All outer nodes should share the same color", 
            (c1.getR() == c2.getR() && c1.getG() == c2.getG() && c1.getB() == c2.getB()) &&
            (c2.getR() == c3.getR() && c2.getG() == c3.getG() && c3.getB() == c3.getB()));
    }
    
}
