package info.iut.sae2.graphs;

import java.util.ArrayList;
import java.util.HashMap;

import info.iut.sae2.algorithm.WelshAndPowell;
import info.iut.sae2.algorithm.SixColorationPlanarAlgorithm;
import info.iut.sae2.algorithm.FiveColorationPlanarAlgorithm;
import info.iut.sae2.properties.ColorProperty;
import info.iut.sae2.properties.LayoutProperty;
import info.iut.sae2.properties.SizeProperty;

/**
 * 
 * @author tlableigne, zakariasabri
 */
public class Graph implements IGraph {

    private final ArrayList<INode> nodes;
    private final ArrayList<IEdge> edges;
    private final LayoutProperty layout;
    private final SizeProperty sizes;
    private final ColorProperty colors;
    private final HashMap<INode, ArrayList<INode>> adjacencyList;

    /**
     * Constructor who initializes a new empty Graph.
     */
    public Graph() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.adjacencyList = new HashMap<>();
        this.layout = new LayoutProperty();
        this.sizes = new SizeProperty();
        this.colors = new ColorProperty();
    }

    /**
     * Instanciates an IGraph.
     * This method is used to create a new empty graph of the same
     * specific type as the target object
     *
     * @return the new IGraph
     */
    @Override
    public Graph createGraph() {
        return new Graph();
    }

    /**
     * Instanciates an IGraph.
     * This method is used to create a new copy graph of the same
     * specific type as the target object
     *
     * @return the new copy
     */
    @Override
    public Graph copy() {
        Graph copy = new Graph();
        HashMap<INode, INode> nodeMap = new HashMap<>();

        for (INode node : this.nodes) {
            INode newNode = copy.addNode();
            nodeMap.put(node, newNode);

            copy.setNodePosition(newNode, this.getNodePosition(node));
            copy.setNodeSize(newNode, this.getNodeSize(node));
            copy.setNodeColor(newNode, this.getNodeColor(node));
        }

        for (IEdge edge : this.edges) {
            INode newSrc = nodeMap.get(edge.source());
            INode newTgt = nodeMap.get(edge.target());
            IEdge newEdge = copy.addEdge(newSrc, newTgt);

            if (newEdge != null) {
                copy.setEdgePosition(newEdge, this.getEdgePosition(edge));
                copy.setEdgeWidth(newEdge, this.getEdgeWidth(edge));
                copy.setEdgeColor(newEdge, this.getEdgeColor(edge));
            }
        }

        return copy;
    }

    /**
     * Method who adds a new empty node to the graph and initializes its adjacency list.
     *
     * @return the added node
     */
    @Override
    public INode addNode() {
        INode newNode = new Node();
        this.nodes.add(newNode);
        this.adjacencyList.put(newNode, new ArrayList<>());
        return newNode;
    }

    /**
     * Adds a given node to the graph and initializes its adjacency list if not already present.
     *
     * @param n the node to add in the graph
     * @return the added node, or null if the node is already in the graph
     */
    @Override
    public INode addNode(INode n) {
        if (n == null) {
            return null;
        }

        if (!this.nodes.contains(n)) {
            this.nodes.add(n);
            this.adjacencyList.put(n, new ArrayList<>());
            return n;
        }
        return null;
    }

    /**
     * Adds a given edge to the graph and returns it.
     *
     * @param e the edge to add
     * @return the added edge, or null if the edge could not be added
     */
    @Override
    public IEdge addEdge(IEdge e) {
        if (e == null) {
            return null;
        }
        return this.addEdge(e.source(), e.target());
    }

    /**
     * This method adds an edge between two given nodes and updates their adjacency lists.
     *
     * @param src source of the edge to add
     * @param tgt target of the edge to add
     * @return the added edge, or null if the edge could not be added
     */
    @Override
    public IEdge addEdge(INode src, INode tgt) {
        if (src == null || tgt == null) {
            return null;
        }

        if (!this.nodes.contains(src) || !this.nodes.contains(tgt)) {
            return null;
        }

        if (this.existEdge(src, tgt, false)) {
            return null;
        }

        IEdge e = new Edge(src, tgt);
        this.edges.add(e);
        this.adjacencyList.get(src).add(tgt);
        this.adjacencyList.get(tgt).add(src);
        return e;
    }

    /**
     * This method deletes a given node from the graph, removes it from adjacency lists,
     * and deletes all incident edges.
     *
     * @param n the node to be deleted
     */
    @Override
    public void delNode(INode n) {
        if (n != null) {
            this.nodes.remove(n);
            this.adjacencyList.remove(n);

            for (ArrayList<INode> neighbors : this.adjacencyList.values()) {
                neighbors.remove(n);
            }

            ArrayList<IEdge> edgesToDelete = new ArrayList<>();
            for (IEdge e : this.edges) {
                if (e.source() == n || e.target() == n) {
                    edgesToDelete.add(e);
                }
            }
            this.edges.removeAll(edgesToDelete);
        }
    }

    /**
     * This method deletes a given edge from the graph and updates the adjacency lists.
     *
     * @param e the edge to be deleted
     */
    @Override
    public void delEdge(IEdge e) {
        if (e != null) {
            this.edges.remove(e);

            INode src = e.source();
            INode tgt = e.target();

            if (this.adjacencyList.containsKey(src)) {
                this.adjacencyList.get(src).remove(tgt);
            }
            if (this.adjacencyList.containsKey(tgt)) {
                this.adjacencyList.get(tgt).remove(src);
            }
        }
    }

    /**
     * Returns the number of nodes in the graph
     *
     * @return the number of nodes
     */
    @Override
    public int numberOfNodes() {
        return this.nodes.size();
    }

    /**
     * Returns the number of edges in the graph
     *
     * @return the number of edges
     */
    @Override
    public int numberOfEdges() {
        return this.edges.size();
    }

    /**
     * Returns the neighbors (successors and predecessors) of a given node in the graph.
     * Provides O(1) time complexity access via the adjacency list.
     *
     * @param n the node whose neighborhood is queried
     * @return an ArrayList containing the neighbors of the node
     */
    @Override
    public ArrayList<INode> getNeighbors(INode n) {
        return this.adjacencyList.getOrDefault(n, new ArrayList<>());
    }

    /**
     * This method returns the successors of a given node in the graph (targets of outgoing edges).
     *
     * @param n the node whose successors are queried
     * @return the successors of the node
     */
    @Override
    public ArrayList<INode> getSuccesors(INode n) {
        ArrayList<INode> successors = new ArrayList<>();
        for (IEdge e : this.edges) {
            if (e.source() == n) {
                successors.add(e.target());
            }
        }
        return successors;
    }

    /**
     * Returns the predecessors of a given node in the graph (sources of incoming edges).
     *
     * @param n the node whose predecessors are queried
     * @return the predecessors of the node
     */
    @Override
    public ArrayList<INode> getPredecessors(INode n) {
        ArrayList<INode> predecessors = new ArrayList<>();
        for (IEdge e : this.edges) {
            if (e.target() == n) {
                predecessors.add(e.source());
            }
        }
        return predecessors;
    }

    /**
     * This method returns the incident edges of a given node (all connected edges, incoming or outgoing).
     *
     * @param n the node whose incident edges are queried
     * @return the incident edges of the node
     */
    @Override
    public ArrayList<IEdge> getInOutEdges(INode n) {
        ArrayList<IEdge> incidentEdges = new ArrayList<>();
        for (IEdge e : this.edges) {
            if (e.source() == n || e.target() == n) {
                incidentEdges.add(e);
            }
        }
        return incidentEdges;
    }

    /**
     * Method who returns the in-going edges of a given node in the graph.
     *
     * @param n the node whose in-going edges are queried
     * @return the in-going edges of the node
     */
    @Override
    public ArrayList<IEdge> getInEdges(INode n) {
        ArrayList<IEdge> inEdges = new ArrayList<>();
        for (IEdge e : this.edges) {
            if (e.target() == n) {
                inEdges.add(e);
            }
        }
        return inEdges;
    }

    /**
     * Returns the out-going edges of a given node in the graph.
     *
     * @param n the node whose out-going edges are queried
     * @return the out-going edges of the node
     */
    @Override
    public ArrayList<IEdge> getOutEdges(INode n) {
        ArrayList<IEdge> outEdges = new ArrayList<>();
        for (IEdge e : this.edges) {
            if (e.source() == n) {
                outEdges.add(e);
            }
        }
        return outEdges;
    }

    /**
     * Returns the nodes of the graph
     *
     * @return the nodes of the graph
     */
    @Override
    public ArrayList<INode> getNodes() {
        return this.nodes;
    }

    /**
     * Returns the edges of the graph
     *
     * @return the edges of the graph
     */
    @Override
    public ArrayList<IEdge> getEdges() {
        return this.edges;
    }

    /**
     * Returns the source node of a given edge in the graph
     *
     * @param e the edge whose source is queried
     * @return the source of the edge
     */
    @Override
    public INode source(IEdge e) {
        return e.source();
    }

    /**
     * Returns the target node of a given edge in the graph
     *
     * @param e the edge whose target is queried
     * @return the target of the edge
     */
    @Override
    public INode target(IEdge e) {
        return e.target();
    }

    /**
     * Returns the in-degree (number of predecessors) of a given node in the
     * graph
     *
     * @param n the node whose in-degree is queried
     * @return the in-degree of the node
     */
    @Override
    public int inDegree(INode n) {
        return this.getPredecessors(n).size();
    }

    /**
     * Returns the out-degree (number of successors) of a given node in the
     * graph
     *
     * @param n the node whose out-degree is queried
     * @return the out-degree of the node
     */
    @Override
    public int outDegree(INode n) {
        return this.getSuccesors(n).size();
    }

    /**
     * Returns the degree (number of neighbors) of a given node in the graph
     *
     * @param n the node whose degree is queried
     * @return the degree of the node
     */
    @Override
    public int degree(INode n) {
        return getNeighbors(n).size();
    }

    /**
     * Returns true if there exists an edge between the two given nodes,
     * considering the orientation of the edges or not.
     *
     * @param src the source node of the putative edge
     * @param tgt the target node of the putative edge
     * @param oriented if true, the function considers the edge as directed
     * (arc), and undirected otherwise
     * @return true if the edge exists, false otherwise
     */
    @Override
    public boolean existEdge(INode src, INode tgt, boolean oriented) {
        return this.getEdge(src, tgt, oriented) != null;
    }

    /**
     * Returns an existing edge between the source and target nodes while
     * considering the orientation or not.
     *
     * @param src the source node of the putative edge
     * @param tgt the target node of the putative edge
     * @param oriented if true, the function considers the edge as directed
     * (arc), and undirected otherwise
     * @return the edge if the edge exists, null otherwise
     */
    @Override
    public IEdge getEdge(INode src, INode tgt, boolean oriented) {
        for (IEdge e : this.edges) {
            if (oriented) {
                if (e.source() == src && e.target() == tgt) {
                    return e;
                }
            } else {
                if ((e.source() == src && e.target() == tgt)
                        || (e.source() == tgt && e.target() == src)) {
                    return e;
                }
            }
        }
        return null;
    }

    /**
     * Returns the SizeProperty associated to the IGraph.
     *
     * @return The SizeProperty of the IGraph
     */
    @Override
    public SizeProperty getSizes() {
        return this.sizes;
    }

    /**
     * Returns the size of a given node in the graph
     *
     * @param n the node of interest
     * @return the size of the node
     */
    @Override
    public Size getNodeSize(INode n) {
        Size s = this.sizes.getNodeValue(n);
        if (s != null) {
            return s;
        } else {
            return SizeProperty.DEFAULT_NODE_SIZE;
        }
    }

    /**
     * Returns the width of a given edge in the graph
     *
     * @param e the edge of interest
     * @return the width of the edge
     */
    @Override
    public Double getEdgeWidth(IEdge e) {
        Double w = this.sizes.getEdgeValue(e);
        if (w != null) {
            return w;
        } else {
            return SizeProperty.DEFAULT_EDGE_WIDTH;
        }
    }

    /**
     * Set the size of a given node in the graph
     *
     * @param n the node of interest
     * @param s new size of the node
     */
    @Override
    public void setNodeSize(INode n, Size s) {
        this.sizes.setNodeValue(n, s);
    }

    /**
     * Set the width of a given edge in the graph
     *
     * @param e the edge of interest
     * @param width new width of the edge
     */
    @Override
    public void setEdgeWidth(IEdge e, Double width) {
        this.sizes.setEdgeValue(e, width);
    }

    /**
     * Set the size of all nodes in the graph
     *
     * @param s new size of the nodes
     */
    @Override
    public void setAllNodesSizes(Size s) {
        for (INode n : this.nodes) {
            this.setNodeSize(n, s);
        }
    }

    /**
     * Set the width of all edges in the graph
     *
     * @param width new width of the edges
     */
    @Override
    public void setAllEdgesWidths(Double width) {
        for (IEdge e : this.edges) {
            this.setEdgeWidth(e, width);
        }
    }

    /**
     * Returns the LayoutProperty associated to the IGraph.
     *
     * @return The LayoutProperty of the IGraph
     */
    @Override
    public LayoutProperty getLayout() {
        return this.layout;
    }

    /**
     * Returns the coordinates of a given node in the graph
     *
     * @param n the node of interest
     * @return the coordinates of the node
     */
    @Override
    public Coord getNodePosition(INode n) {
        Coord c = this.layout.getNodeValue(n);
        if (c != null) {
            return c;
        } else {
            return LayoutProperty.DEFAULT_NODE_POS;
        }
    }

    /**
     * Returns the coordinates of a given edge (ie the coordinates of its bends)
     * in the graph
     *
     * @param e the edge of interest
     * @return the coordinates of the bends of the edge
     */
    @Override
    public ArrayList<Coord> getEdgePosition(IEdge e) {
        ArrayList<Coord> pos = this.layout.getEdgeValue(e);
        if (pos != null) {
            return pos;
        } else {
            return LayoutProperty.DEFAULT_EDGE_POS;
        }
    }

    /**
     * Set the coordinates of a given node in the graph
     *
     * @param n the node of interest
     * @param c new coordinates of the node
     */
    @Override
    public void setNodePosition(INode n, Coord c) {
        this.layout.setNodeValue(n, c);
    }

    /**
     * Set the coordinates of a given edge (ie the coordinates of its bends) in
     * the graph
     *
     * @param e the edge of interest
     * @param bends the bends of the edge
     */
    @Override
    public void setEdgePosition(IEdge e, ArrayList<Coord> bends) {
        this.layout.setEdgeValue(e, bends);
    }

    /**
     * Set the coordinates of all nodes in the graph
     *
     * @param c new coordinates of the nodes
     */
    @Override
    public void setAllNodesPositions(Coord c) {
        for (INode n : this.nodes) {
            this.setNodePosition(n, c);
        }
    }

    /**
     * Set the coordinates of all edges (ie the coordinates of its bends) in the
     * graph
     *
     * @param bends the bends of the edges
     */
    @Override
    public void setAllEdgesPositions(ArrayList<Coord> bends) {
        for (IEdge e : this.edges) {
            this.setEdgePosition(e, bends);
        }
    }

    /**
     * Returns the coordinates of the bounding box of the graph drawing. The
     * bounding box of a drawing of a graph is the minimum rectangle such that
     * all nodes and edges of the graph are inside the rectangle. Please remind
     * that the y-axis is oriented toward the bottom.
     *
     * @return an ArrayList containing the coordinates of the top-left corner of
     * the bounding box (at the first index in the ArrayList) and the coordinates
     * of the bottom-right corner of the bounding box (second index)
     *
     */
    @Override
    public ArrayList<Coord> getBoundingBox() {
        if (nodes.isEmpty()) {
            ArrayList<Coord> boundingBox = new ArrayList<>();
            boundingBox.add(new Coord(0, 0));
            boundingBox.add(new Coord(1, 1));
            return boundingBox;
        }

        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE;
        double maxY = -Double.MAX_VALUE;

        for (INode node : nodes) {
            Coord c = getNodePosition(node);
            if (c != null) {
                if (c.getX() < minX) {
                    minX = c.getX();
                }
                if (c.getY() < minY) {
                    minY = c.getY();
                }
                if (c.getX() > maxX) {
                    maxX = c.getX();
                }
                if (c.getY() > maxY) {
                    maxY = c.getY();
                }
            }
        }

        ArrayList<Coord> boundingBox = new ArrayList<>();
        boundingBox.add(new Coord(minX - 10, minY - 10));
        boundingBox.add(new Coord(maxX + 10, maxY + 10));
        return boundingBox;
    }

    /**
     * Returns the ColorProperty associated to the IGraph.
     *
     * @return The ColorProperty of the IGraph
     */
    @Override
    public ColorProperty getColor() {
        return this.colors;
    }

    /**
     * Returns the color of a given node in the graph.
     *
     * @param n the node of interest
     * @return the color of the node
     */
    @Override
    public Color getNodeColor(INode n) {
        Color c = this.colors.getNodeValue(n);
        if (c != null) {
            return c;
        } else {
            return ColorProperty.DEFAULT_NODE_COL;
        }
    }

    /**
     * Returns the color of a given edge in the graph.
     *
     * @param e the edge of interest
     * @return the color of the edge
     */
    @Override
    public Color getEdgeColor(IEdge e) {
        Color c = this.colors.getEdgeValue(e);
        if (c != null) {
            return c;
        } else {
            return ColorProperty.DEFAULT_EDGE_COL;
        }
    }

    /**
     * Set the color of a given node in the graph.
     *
     * @param n the node of interest
     * @param c new color of the node
     */
    @Override
    public void setNodeColor(INode n, Color c) {
        this.colors.setNodeValue(n, c);
    }

    /**
     * Set the color of a given edge in the graph.
     *
     * @param e the edge of interest
     * @param c the new color of the edge
     */
    @Override
    public void setEdgeColor(IEdge e, Color c) {
        this.colors.setEdgeValue(e, c);
    }

    /**
     * Set the color of all nodes in the graph.
     *
     * @param c the new color of the nodes
     */
    @Override
    public void setAllNodesColors(Color c) {
        for (INode n : this.nodes) {
            this.setNodeColor(n, c);
        }
    }

    /**
     * Set the color of all edges in the graph.
     *
     * @param c the new color of the edges
     */
    @Override
    public void setAllEdgesColors(Color c) {
        for (IEdge e : this.edges) {
            this.setEdgeColor(e, c);
        }
    }

    /**
     * Colors the nodes with the Welsh and Powell algorithm.
     */
    @Override
    public void welshAndPowell() {
        WelshAndPowell wp = new WelshAndPowell();
        ColorProperty computedColors = wp.apply(this, null);

        for (INode node : this.getNodes()) {
            Color newColor = computedColors.getNodeValue(node);
            if (newColor != null) {
                this.setNodeColor(node, newColor);
            }
        }
    }

    /**
     * Colors the nodes with a 6 colors algorithm (only for planar graphs).
     */
    @Override
    public void sixColors() {
        SixColorationPlanarAlgorithm sc = new SixColorationPlanarAlgorithm();
        ColorProperty results = sc.apply(this, null);

        for (INode n : this.getNodes()) {
            Color color = results.getNodeValue(n);
            if (color != null) {
                this.setNodeColor(n, color);
            }
        }
    }

    /**
     * Colors the nodes with a 5 colors algorithm (only for planar graphs).
     */
    @Override
    public void fiveColors() {
        FiveColorationPlanarAlgorithm fc = new FiveColorationPlanarAlgorithm();
        ColorProperty results = fc.apply(this, null);

        for (INode n : this.getNodes()) {
            Color color = results.getNodeValue(n);
            if (color != null) {
                this.setNodeColor(n, color);
            }
        }
    }
}