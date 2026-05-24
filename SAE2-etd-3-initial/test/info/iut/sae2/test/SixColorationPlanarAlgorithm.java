package info.iut.sae2.test;

import info.iut.sae2.algorithm.SixColorationPlanarAlgorithm;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.Graph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.properties.ColorProperty;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class SixColorationPlanarAlgorithm {

    @Test
    public void testEmptyGraph() {
        Graph g = new Graph();
        SixColoring sc = new SixColoring();
        ColorProperty colors = sc.apply(g, null);
        assertNotNull(colors);
    }

    @Test
    public void testDisconnectedGraph() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();

        SixColoring sc = new SixColoring();
        ColorProperty colors = sc.apply(g, null);

        assertNotNull(colors.getNodeValue(n1));
        assertNotNull(colors.getNodeValue(n2));
    }

    @Test
    public void testTriangleGraph() {
        Graph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();

        g.addEdge(n1, n2);
        g.addEdge(n2, n3);
        g.addEdge(n3, n1);

        SixColoring sc = new SixColoring();
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

        SixColoring sc = new SixColoring();
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

        SixColoring sc = new SixColoring();
        ColorProperty colors = sc.apply(g, null);

        ArrayList<Color> usedColors = new ArrayList<>();
        for (INode n : g.getNodes()) {
            Color c = colors.getNodeValue(n);
            if (!usedColors.contains(c)) {
                usedColors.add(c);
            }
        }
        assertTrue("Max 6 colors should be used", usedColors.size() <= 6);
    }
}