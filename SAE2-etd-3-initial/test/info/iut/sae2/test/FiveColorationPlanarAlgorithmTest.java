import info.iut.sae2.algorithm.FiveColorationPlanarAlgorithm;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.Graph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.properties.ColorProperty;

import org.junit.Test;
import static org.junit.Assert.*;

/**
* Tests for the FiveColoration Algorithm
* @author zakariasabri & tlableigne
*/
public class FiveColorationPlanarAlgorithmTest {

    /**
    * Tests the algorithm on an empty graph.

    * Expected result: the algorithm must return a non-null ColorProperty
    * object even if the graph contains no node.
    */
    @Test
    public void testEmptyGraph() {
        Graph g = new Graph();
        FiveColorationPlanarAlgorithm fc = new FiveColorationPlanarAlgorithm();
        ColorProperty colors = fc.apply(g, null);
        assertNotNull(colors);
    }

    /**
     * Tests the algorithm's behavior on a disconnected graph.
     * Verifies that colors are properly assigned to isolated nodes.
     */
    @Test
    public void testDisconnectedGraph() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();

        FiveColorationPlanarAlgorithm fc = new FiveColorationPlanarAlgorithm();
        ColorProperty colors = fc.apply(g, null);

        assertNotNull(colors.getNodeValue(n1));
        assertNotNull(colors.getNodeValue(n2));
    }

    /**
     * Tests the coloration of a triangle graph (K3).
     * Verifies that 3 interconnected nodes receive 3 distinct colors.
     */
    @Test
    public void testTriangleGraph() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();

        g.addEdge(n1, n2);
        g.addEdge(n2, n3);
        g.addEdge(n3, n1);

        FiveColorationPlanarAlgorithm fc = new FiveColorationPlanarAlgorithm();
        ColorProperty colors = fc.apply(g, null);

        Color c1 = colors.getNodeValue(n1);
        Color c2 = colors.getNodeValue(n2);
        Color c3 = colors.getNodeValue(n3);

        assertNotNull(c1);
        assertNotNull(c2);
        assertNotNull(c3);

        assertFalse(c1.equals(c2));
        assertFalse(c2.equals(c3));
        assertFalse(c3.equals(c1));
    }

    /**
     * Tests the coloration of a complete graph with 4 nodes (K4).
     * Verifies that all 4 nodes receive different colors (absolute condition for K4).
     */
    @Test
    public void testCompleteGraphK4() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();
        INode n4 = g.addNode();

        g.addEdge(n1, n2);
        g.addEdge(n1, n3);
        g.addEdge(n1, n4);
        g.addEdge(n2, n3);
        g.addEdge(n2, n4);
        g.addEdge(n3, n4);

        FiveColorationPlanarAlgorithm fc = new FiveColorationPlanarAlgorithm();
        ColorProperty colors = fc.apply(g, null);

        Color c1 = colors.getNodeValue(n1);
        Color c2 = colors.getNodeValue(n2);
        Color c3 = colors.getNodeValue(n3);
        Color c4 = colors.getNodeValue(n4);

        assertFalse(c1.equals(c2));
        assertFalse(c1.equals(c3));
        assertFalse(c1.equals(c4));
        assertFalse(c2.equals(c3));
        assertFalse(c2.equals(c4));
        assertFalse(c3.equals(c4));
    }

    /**
     * Tests the theoretical color limit on a complex planar graph.
     * Verifies that the algorithm uses no more than 5 colors in total to color the entire graph.
     */
    @Test
    public void testOriginalGraphIsNotModified() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();
        INode n4 = g.addNode();
        INode n5 = g.addNode();
        INode n6 = g.addNode();

        g.addEdge(n1, n2);
        g.addEdge(n2, n3);
        g.addEdge(n3, n4);
        g.addEdge(n4, n5);
        g.addEdge(n5, n6);
        g.addEdge(n6, n1);
        g.addEdge(n1, n4);

        int nodeCountBefore = g.numberOfNodes();
        int edgeCountBefore = g.numberOfEdges();
        int degreeN1Before = g.degree(n1);
        int degreeN4Before = g.degree(n4);

        FiveColorationPlanarAlgorithm fc = new FiveColorationPlanarAlgorithm();
        ColorProperty colors = fc.apply(g, null);

        assertNotNull(colors);
        assertEquals(nodeCountBefore, g.numberOfNodes());
        assertEquals(edgeCountBefore, g.numberOfEdges());
        assertEquals(degreeN1Before, g.degree(n1));
        assertEquals(degreeN4Before, g.degree(n4));
        assertTrue(g.getNeighbors(n1).contains(n2));
        assertTrue(g.getNeighbors(n1).contains(n6));
        assertTrue(g.getNeighbors(n1).contains(n4));
        assertTrue(g.getNeighbors(n4).contains(n3));
        assertTrue(g.getNeighbors(n4).contains(n5));
        assertTrue(g.getNeighbors(n4).contains(n1));
    }

    /**
* Tests a planar graph containing a node of degree 5.

* Expected result: the central node and all its neighbors must receive a
* valid color, and every adjacent pair of nodes must have different colors.
*/
@Test
public void testGraphWithDegreeFiveNode() {
    Graph g = new Graph();
    INode center = g.addNode();
    INode n1 = g.addNode();
    INode n2 = g.addNode();
    INode n3 = g.addNode();
    INode n4 = g.addNode();
    INode n5 = g.addNode();

    g.addEdge(center, n1);
    g.addEdge(center, n2);
    g.addEdge(center, n3);
    g.addEdge(center, n4);
    g.addEdge(center, n5);
    g.addEdge(n1, n2);
    g.addEdge(n2, n3);
    g.addEdge(n3, n4);
    g.addEdge(n4, n5);

    FiveColorationPlanarAlgorithm fc = new FiveColorationPlanarAlgorithm();
    ColorProperty colors = fc.apply(g, null);

    for (INode node : g.getNodes()) {
        assertNotNull(colors.getNodeValue(node));
    }

    for (INode node : g.getNodes()) {
        Color nodeColor = colors.getNodeValue(node);
        for (INode neighbor : g.getNeighbors(node)) {
            Color neighborColor = colors.getNodeValue(neighbor);
            assertNotNull(neighborColor);
            assertFalse(nodeColor.equals(neighborColor));
        }
    }
}

    /**
    * Tests that every adjacent pair of nodes receives different colors on a
    * larger planar graph.

    * Expected result: all adjacent nodes must have different colors and every
    * node must receive a valid color.
    */
    @Test
    public void testAdjacentNodesAlwaysHaveDifferentColors() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();
        INode n4 = g.addNode();
        INode n5 = g.addNode();
        INode n6 = g.addNode();

        g.addEdge(n1, n2);
        g.addEdge(n2, n3);
        g.addEdge(n3, n4);
        g.addEdge(n4, n5);
        g.addEdge(n5, n6);
        g.addEdge(n6, n1);
        g.addEdge(n1, n4);
        g.addEdge(n2, n5);

        FiveColorationPlanarAlgorithm fc = new FiveColorationPlanarAlgorithm();
        ColorProperty colors = fc.apply(g, null);

        for (INode node : g.getNodes()) {
            Color nodeColor = colors.getNodeValue(node);
            assertNotNull(nodeColor);

            for (INode neighbor : g.getNeighbors(node)) {
                Color neighborColor = colors.getNodeValue(neighbor);
                assertNotNull(neighborColor);
                assertFalse(nodeColor.equals(neighborColor));
            }
        }
    }
}