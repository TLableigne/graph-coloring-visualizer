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
 * Test class for the Graph core data structure.
 * Verifies all graph manipulation and querying functionalities.
 */
public class GraphTest {

    /**
     * Tests the addition of nodes (via instance or automatic creation) and edges.
     * Verifies that counters are properly incremented and edge source/target links are correct.
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
     * Tests retrieving the neighbors of a given node and calculating its total degree.
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
     * Tests the default dimensions of nodes and the ability to modify them
     * individually (setNodeSize) or globally (setAllNodesSizes).
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
     * Tests the default thickness of edges and its individual or global modification.
     */
    @Test
    public void testEdgeWidthDefaultsAndSetters() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        IEdge e = g.addEdge(n1, n2);
        double number = 1.0;

        assertEquals(number, g.getEdgeWidth(e), 0.001);

        g.setEdgeWidth(e, 2.5);
        assertEquals(2.5, g.getEdgeWidth(e), 0.001);

        g.setAllEdgesWidths(4.0);
        assertEquals(4.0, g.getEdgeWidth(e), 0.001);
    }

    /**
     * Tests the spatial positioning of nodes via coordinates and validates 
     * the correct calculation of the graph's bounding box.
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
     * Tests the assignment of a default color upon creation and validates
     * the behavior of the Welsh & Powell coloration shortcut.
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
     * Tests the removal of edges (delEdge) and nodes (delNode) in the graph.
     * Also ensures that deleting a node removes all its attached edges.
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
     * Tests retrieving predecessor and successor nodes to evaluate directed graph traversal.
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
     * Tests conditionally retrieving incoming, outgoing, or both types of edges for a specific node.
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
     * Verifies that methods left unimplemented return a neutral/null state 
     * to avoid unexpected exceptions in the existing logic.
     */
    @Test
    public void testUnimplementedMethods() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        assertNull(g.createGraph());
        assertNull(g.copy());
        assertNull(g.addEdge(null));
        assertEquals(0, g.inDegree(n1));
        assertEquals(0, g.outDegree(n2));
        assertFalse(g.existEdge(n1, n2, false));
        assertNull(g.getEdge(n1, n2, false));
    }

    /**
     * Tests advanced layout properties, such as positioning edges using multiple bend points.
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
     * Tests the execution and valid color assignment via the 6-color planar utility method.
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
     * Tests the execution and valid color assignment via the 5-color planar utility method.
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