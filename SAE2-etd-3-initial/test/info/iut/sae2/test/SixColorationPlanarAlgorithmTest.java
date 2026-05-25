package info.iut.sae2.test;

import info.iut.sae2.algorithm.SixColorationPlanarAlgorithm;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.Graph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.properties.ColorProperty;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
* Tests for the SixColoration Algorithm
* @author zakariasabri
*/
public class SixColorationPlanarAlgorithmTest {

    /**
    * Tests the algorithm on an empty graph.

    * Expected result: the algorithm must return a non-null ColorProperty
    * object even if the graph contains no node.
    */
    @Test
    public void testEmptyGraph() {
        Graph g = new Graph();
        SixColorationPlanarAlgorithm sc = new SixColorationPlanarAlgorithm();
        ColorProperty colors = sc.apply(g, null);
        assertNotNull(colors);
    }

    /**
    * Tests the algorithm on a disconnected graph made of two isolated nodes.

    * Expected result: each node must receive a color, even if there is no edge
    * in the graph.
    */
    @Test
    public void testDisconnectedGraph() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();

        SixColorationPlanarAlgorithm sc = new SixColorationPlanarAlgorithm();
        ColorProperty colors = sc.apply(g, null);

        assertNotNull(colors.getNodeValue(n1));
        assertNotNull(colors.getNodeValue(n2));
    }

    /**
    * Tests the algorithm on a triangle graph.

    * This graph contains three nodes that are all adjacent to each other.

    * Expected result: the three nodes must all receive non-null colors, and no
    * two adjacent nodes may share the same color.
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

        SixColorationPlanarAlgorithm sc = new SixColorationPlanarAlgorithm();
        ColorProperty colors = sc.apply(g, null);

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
    * Tests the algorithm on the complete graph K4.

    * In this graph, each node is adjacent to every other node.

    * Expected result: the four nodes must receive four distinct colors,
    * because every pair of nodes is adjacent.
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

        SixColorationPlanarAlgorithm sc = new SixColorationPlanarAlgorithm();
        ColorProperty colors = sc.apply(g, null);

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
    * Tests the algorithm on a planar graph to verify the number of used colors.

    * Expected result: every node must receive a color and the total number of
    * distinct colors used by the algorithm must not exceed 6.
    */
    @Test
    public void testPlanarGraphColorCount() {
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

        SixColorationPlanarAlgorithm sc = new SixColorationPlanarAlgorithm();
        ColorProperty colors = sc.apply(g, null);

        ArrayList<Color> usedColors = new ArrayList<>();

        for (INode n : g.getNodes()) {
            Color c = colors.getNodeValue(n);
            assertNotNull(c);
            if (!usedColors.contains(c)) {
                usedColors.add(c);
            }
        }

        assertTrue(usedColors.size() <= 6);
    }

    /**
    * Tests that the algorithm does not modify the original graph structure.

    * Expected result: after the execution of the algorithm, the original graph
    * must keep the same number of nodes, the same number of edges and the same
    * neighborhoods.
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

        SixColorationPlanarAlgorithm sc = new SixColorationPlanarAlgorithm();
        ColorProperty colors = sc.apply(g, null);

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

        SixColorationPlanarAlgorithm sc = new SixColorationPlanarAlgorithm();
        ColorProperty colors = sc.apply(g, null);

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