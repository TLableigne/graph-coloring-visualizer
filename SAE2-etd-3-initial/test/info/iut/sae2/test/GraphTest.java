package info.iut.sae2.test;

import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.Coord;
import info.iut.sae2.graphs.Graph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.graphs.IEdge;
import info.iut.sae2.graphs.Node;
import info.iut.sae2.graphs.Size;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 * Test class for the Graph implementation.
 *
 * This class contains unit tests that check the main behaviour of the Graph
 * class.
 */
public class GraphTest {

    /**
     * Tests node and edge creation.
     *
     * This test verifies that nodes and edges are correctly added to the graph
     * and that the created edge links the expected source and target nodes.
     */
    @Test
    public void testAddNodesAndEdges() {
        Graph g = new Graph();
        assertEquals(0, g.numberOfNodes());

        INode n1 = g.addNode();
        assertNotNull(n1);
        assertEquals(1, g.numberOfNodes());

        INode n2 = new Node();
        g.addNode(n2);
        assertEquals(2, g.numberOfNodes());

        g.addEdge(n1, n2);
        assertEquals(1, g.numberOfEdges());

        ArrayList<IEdge> edges = g.getEdges();
        assertEquals(1, edges.size());
        IEdge e = edges.get(0);
        assertEquals(n1, e.source());
        assertEquals(n2, e.target());
    }

    /**
     * Tests neighbours and degree on a simple graph.
     *
     * This test checks that getNeighbors returns the correct adjacent nodes
     * and that degree returns the expected number of neighbours.
     */
    @Test
    public void testNeighborsAndDegree() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();

        g.addEdge(n1, n2);
        g.addEdge(n2, n3);

        ArrayList<INode> neighbors = g.getNeighbors(n2);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(n1));
        assertTrue(neighbors.contains(n3));

        assertEquals(2, g.degree(n2));
    }

    /**
     * Tests default node size and size setters.
     *
     * This test verifies that a new node has the default size and that the
     * size setters correctly update one node or all nodes.
     */
    @Test
    public void testSizeDefaultsAndSetters() {
        Graph g = new Graph();
        INode n = g.addNode();

        Size defaultSize = g.getNodeSize(n);
        assertEquals(2.0, defaultSize.getW(), 0.001);
        assertEquals(2.0, defaultSize.getH(), 0.001);

        Size s = new Size(5, 7);
        g.setNodeSize(n, s);
        assertEquals(5.0, g.getNodeSize(n).getW(), 0.001);
        assertEquals(7.0, g.getNodeSize(n).getH(), 0.001);

        Size s2 = new Size(3, 4);
        g.setAllNodesSizes(s2);
        assertEquals(3.0, g.getNodeSize(n).getW(), 0.001);
        assertEquals(4.0, g.getNodeSize(n).getH(), 0.001);
    }

    /**
     * Tests default edge width and width setters.
     *
     * This test checks that a new edge has the default width and that the
     * width setters correctly update one edge or all edges.
     */
    @Test
    public void testEdgeWidthDefaultsAndSetters() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        IEdge e = g.addEdge(n1, n2);

        assertEquals(1.0, g.getEdgeWidth(e), 0.001);

        g.setEdgeWidth(e, 2.5);
        assertEquals(2.5, g.getEdgeWidth(e), 0.001);

        g.setAllEdgesWidths(4.0);
        assertEquals(4.0, g.getEdgeWidth(e), 0.001);
    }

    /**
     * Tests node positions and the graph bounding box.
     *
     * This test verifies that the positions set on nodes are correctly stored
     * and that the bounding box matches the graph layout.
     */
    @Test
    public void testPositionsAndBoundingBox() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();

        g.setNodePosition(n1, new Coord(10, 20));
        g.setNodePosition(n2, new Coord(30, 40));

        Coord p1 = g.getNodePosition(n1);
        assertEquals(10.0, p1.getX(), 0.001);
        assertEquals(20.0, p1.getY(), 0.001);

        ArrayList<Coord> bbox = g.getBoundingBox();
        Coord topLeft = bbox.get(0);
        Coord bottomRight = bbox.get(1);

        assertEquals(0.0, topLeft.getX(), 0.001);
        assertEquals(10.0, topLeft.getY(), 0.001);
        assertEquals(40.0, bottomRight.getX(), 0.001);
        assertEquals(50.0, bottomRight.getY(), 0.001);
    }

    /**
     * Tests default node colors and Welsh and Powell integration.
     *
     * This test checks that nodes have a default color and that the Welsh and
     * Powell algorithm assigns valid different colors on a triangle graph.
     */
    @Test
    public void testColorDefaultsAndWelshAndPowell() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();

        g.addEdge(n1, n2);
        g.addEdge(n2, n3);
        g.addEdge(n1, n3);

        Color cDefault = g.getNodeColor(n1);
        assertNotNull(cDefault);

        g.welshAndPowell();

        Color c1 = g.getNodeColor(n1);
        Color c2 = g.getNodeColor(n2);
        Color c3 = g.getNodeColor(n3);

        assertNotNull(c1);
        assertNotNull(c2);
        assertNotNull(c3);

        assertFalse(c1.equals(c2));
        assertFalse(c1.equals(c3));
        assertFalse(c2.equals(c3));
    }

    /**
     * Tests deletion of nodes and edges.
     *
     * This test verifies that deleting an edge removes only that edge and that
     * deleting a node also removes its incident edges.
     */
    @Test
    public void testDelNodeAndDelEdge() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();
        IEdge e1 = g.addEdge(n1, n2);
        IEdge e2 = g.addEdge(n2, n3);

        assertEquals(3, g.numberOfNodes());
        assertEquals(2, g.numberOfEdges());

        g.delEdge(e2);
        assertEquals(3, g.numberOfNodes());
        assertEquals(1, g.numberOfEdges());
        assertFalse(g.getEdges().contains(e2));

        g.delNode(n1);
        assertEquals(2, g.numberOfNodes());
        assertEquals(0, g.numberOfEdges());
        assertFalse(g.getNodes().contains(n1));
        assertFalse(g.getEdges().contains(e1));
    }

    /**
     * Tests successors and predecessors.
     *
     * This test checks that the graph correctly returns the outgoing and
     * incoming neighbours of a node.
     */
    @Test
    public void testSuccessorsAndPredecessors() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();

        g.addEdge(n1, n2);
        g.addEdge(n2, n3);
        g.addEdge(n1, n3);

        ArrayList<INode> succ = g.getSuccesors(n1);
        assertEquals(2, succ.size());
        assertTrue(succ.contains(n2));
        assertTrue(succ.contains(n3));

        ArrayList<INode> pred = g.getPredecessors(n3);
        assertEquals(2, pred.size());
        assertTrue(pred.contains(n1));
        assertTrue(pred.contains(n2));
    }

    /**
     * Tests edge query methods.
     *
     * This test verifies that the methods returning incoming, outgoing and
     * incident edges give the expected results.
     */
    @Test
    public void testEdgeQueries() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();

        IEdge e1 = g.addEdge(n1, n2);
        IEdge e2 = g.addEdge(n2, n3);
        IEdge e3 = g.addEdge(n1, n3);

        ArrayList<IEdge> inOut = g.getInOutEdges(n2);
        assertEquals(2, inOut.size());
        assertTrue(inOut.contains(e1));
        assertTrue(inOut.contains(e2));

        ArrayList<IEdge> inEdges = g.getInEdges(n3);
        assertEquals(2, inEdges.size());
        assertTrue(inEdges.contains(e2));
        assertTrue(inEdges.contains(e3));

        ArrayList<IEdge> outEdges = g.getOutEdges(n1);
        assertEquals(2, outEdges.size());
        assertTrue(outEdges.contains(e1));
        assertTrue(outEdges.contains(e3));
    }

    /**
     * Tests various implemented methods from the graph interface.
     *
     * This test checks that helper methods behave correctly, including graph
     * creation, copy, edge existence and edge retrieval.
     */
    @Test
    public void testImplementedMethods() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();

        assertNotNull(g.createGraph());
        assertNotNull(g.copy());

        assertNull(g.addEdge(null));

        assertEquals(0, g.inDegree(n1));
        assertEquals(0, g.outDegree(n2));

        assertFalse(g.existEdge(n1, n2, false));
        assertNull(g.getEdge(n1, n2, false));

        IEdge e = g.addEdge(n1, n2);
        assertNotNull(e);
        assertTrue(g.existEdge(n1, n2, false));
        assertNotNull(g.getEdge(n1, n2, false));
    }

    /**
     * Tests layout properties for nodes and edges.
     *
     * This test verifies that node positions and edge bend points are stored
     * and updated correctly.
     */
    @Test
    public void testLayoutProperties() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        IEdge e = g.addEdge(n1, n2);

        ArrayList<Coord> bends = new ArrayList<>();
        bends.add(new Coord(10, 10));
        g.setEdgePosition(e, bends);
        assertEquals(1, g.getEdgePosition(e).size());

        g.setAllNodesPositions(new Coord(5, 5));
        assertEquals(5.0, g.getNodePosition(n1).getX(), 0.001);
        assertEquals(5.0, g.getNodePosition(n2).getY(), 0.001);

        ArrayList<Coord> newBends = new ArrayList<>();
        newBends.add(new Coord(20, 20));
        g.setAllEdgesPositions(newBends);
        assertEquals(20.0, g.getEdgePosition(e).get(0).getX(), 0.001);
        assertNotNull(g.getLayout());
    }

    /**
     * Tests the integration of the 6-color algorithm.
     *
     * This test checks that sixColors assigns a color to each node of a small
     * graph.
     */
    @Test
    public void testSixColors() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();

        g.addEdge(n1, n2);
        g.addEdge(n2, n3);
        g.addEdge(n1, n3);

        g.sixColors();

        assertNotNull(g.getNodeColor(n1));
        assertNotNull(g.getNodeColor(n2));
        assertNotNull(g.getNodeColor(n3));
    }

    /**
     * Tests the integration of the 5-color algorithm.
     *
     * This test checks that fiveColors assigns a color to each node of a small
     * graph.
     */
    @Test
    public void testFiveColors() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();

        g.addEdge(n1, n2);
        g.addEdge(n2, n3);
        g.addEdge(n1, n3);

        g.fiveColors();

        assertNotNull(g.getNodeColor(n1));
        assertNotNull(g.getNodeColor(n2));
        assertNotNull(g.getNodeColor(n3));
    }
}