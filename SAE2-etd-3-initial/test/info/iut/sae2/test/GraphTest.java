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
import org.junit.Test;

/**
 * Tests for the Graph class.
 */
public class GraphTest {

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

}
