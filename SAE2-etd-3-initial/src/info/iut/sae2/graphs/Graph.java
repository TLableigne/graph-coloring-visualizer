package info.iut.sae2.graphs;

import java.util.ArrayList;
import java.util.HashMap;


import info.iut.sae2.algorithm.WelshAndPowell;
import info.iut.sae2.properties.ColorProperty;
import info.iut.sae2.properties.LayoutProperty;
import info.iut.sae2.properties.SizeProperty;

public class Graph implements IGraph {

    private ArrayList<INode> nodes;
    private ArrayList<IEdge> edges;
    private LayoutProperty layout;
    private SizeProperty sizes;
    private ColorProperty colors;

    /**
     * Constructor
     */
    public Graph(){
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();

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
    public Graph createGraph() {
        return null;
    }

    
    /**
     * Instanciates an IGraph.
     * This method is used to create a new copy graph of the same
     * specific type as the target object
     *
     * @return the new copy
     */
    public Graph copy() {
        return null;
    }

    /**
     * Adds a new empty node to the graph and returns it.
     *
     * @return the added node
     */
    public INode addNode() {
        INode newNode = new Node();
        this.nodes.add(newNode);
        return newNode;

    }

    /**
     * Adds a given node to the graph and returns it.
     *
     * @param n the node to add in the graph
     * @return the added node
     */
    public INode addNode(INode n) {
        if (this.nodes.add(n)) {
            return n;
        }
        else {
            return null;
        }
    }

    /**
     * Adds a given edge and returns an edge to the graph and returns it.
     *
     * @param e the edge to add
     * @return the added edge
     */
    public IEdge addEdge(IEdge e) {
        return null;
    }

    /**
     * Adds an edge between the two given nodes and returns it.
     *
     * @param src source of the edge to add
     * @param tgt target of the edge to add
     * @return the added edge
     */
    public IEdge addEdge(INode src, INode tgt) {
        Edge e = new Edge(src, tgt);
        if (this.edges.add(e)) {
            return e;
        }
        else {
            return null;
        }
    }

    /**
     * Deletes a given node from the graph
     *
     * @param n the node to be deleted
     */
    public void delNode(INode n) {
        
    }

    /**
     * Deletes a given edge from the graph
     *
     * @param e the edge to be deleted
     */
    public void delEdge(IEdge e) {
        
    }

    /**
     * Returns the number of nodes in the graph
     *
     * @return the number of nodes
     */
    public int numberOfNodes() {
        return this.nodes.size();
    }

    /**
     * Returns the number of edges in the graph
     *
     * @return the number of edges
     */
    public int numberOfEdges() {
        return this.edges.size();
    }

    /**
     * Returns the neighbors  (successors and predecessors) of a given node in the graph
     *
     * @param n the node whose neighborhood is queried
     * @return the neighbors of the node
     */
    public ArrayList<INode> getNeighbors(INode n) {
        return null;
    }

    /**
     * Returns the sucessors of a given node in the graph
     *
     * @param n the node whose successors are queried
     * @return the successors of the node
     */
    public ArrayList<INode> getSuccesors(INode n) {
        return null;
    }

    /**
     * Returns the predecessors of a given node in the graph
     *
     * @param n the node whose predecessors are queried
     * @return the predecessors of the node
     */
    public ArrayList<INode> getPredecessors(INode n) {
        return null;
    }

    /**
     * Returns the indicent edges of a given node
     *
     * @param n the node whose incident edges are queried
     * @return the incident edges of the node
     */
    public ArrayList<IEdge> getInOutEdges(INode n) {
        return null;
    }

    /**
     * Returns the in-going edges of a given node in the graph
     *
     * @param n the node whose in-going are queried
     * @return the in-going edges of the node
     */
    public ArrayList<IEdge> getInEdges(INode n) {
        return null;
    }

    /**
     * Returns the out-going edges of a given node in the graph
     *
     * @param n the node whose out-going are queried
     * @return the out-going edges of the node
     */
    public ArrayList<IEdge> getOutEdges(INode n) {
        return null;
    }

    /**
     * Returns the nodes of the graph
     *
     * @return the nodes of the graph
     */
    public ArrayList<INode> getNodes() {
        return this.nodes;
    }

    /**
     * Returns the edges of the graph
     *
     * @return the edges of the graph
     */
    public ArrayList<IEdge> getEdges() {
        return this.edges;
    }

    /**
     * Returns the source node of a given edge in the graph
     *
     * @param e the edge whose source is queried
     * @return the source of the edge
     */
    public INode source(IEdge e) {
        return e.source();
    }

    /**
     * Returns the target node of a given edge in the graph
     *
     * @param e the edge whose target is queried
     * @return the target of the edge
     */
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
    public int inDegree(INode n) {
        return 0;
    }

    /**
     * Returns the out-degree (number of successors) of a given node in the
     * graph
     *
     * @param n the node whose out-degree is queried
     * @return the out-degree of the node
     */
    public int outDegree(INode n) {
        return 0;
    }

    /**
     * Returns the degree (number of neighbors) of a given node in the graph
     *
     * @param n the node whose degree is queried
     * @return the degree of the node
     */
    public int degree(INode n) {
        return 0;
    }

    /**
     * Returns true if there exists an edge between the two given nodes,
     * considering the orientation of the edges or not.
     *
     * @param src the source node of the putative edge
     * @param tgt the target node of the putative edge
     * @param oriented if true, the function consider the edge as directed
     * (arc), and undirected otherwise
     * @return true if the Iedge exists, false otherwise
     */
    public boolean existEdge(INode src, INode tgt, boolean oriented) {
        return false;
    }

    /**
     * Returns an existing edge between the source and target nodes while
     * considering the orientation of not.
     *
     * @param src the source node of the putative edge
     * @param tgt the target node of the putative edge
     * @param oriented if true, the function consider the edge as directed
     * (arc), and undirected otherwise
     * @return the edge if the Iedge exists, null otherwise
     */
    public IEdge getEdge(INode src, INode tgt, boolean oriented) {
        return null;
    }

    /**
     * Returns the SizeProperty associated to the IGraph.
     *
     * @return The SizeProperty of the IGraph
     */
    public SizeProperty getSizes() {
        return this.sizes;
    }

    /**
     * Returns the size of a given node in the graph
     *
     * @param n the node of interest
     * @return the size of the node
     */
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
    public void setNodeSize(INode n, Size s) {
        this.sizes.setNodeValue(n, s);
    }

    /**
     * Set the width of a given edge in the graph
     *
     * @param e the edge of interest
     * @param width new width of the edge
     */
    public void setEdgeWidth(IEdge e, Double width) {
        this.sizes.setEdgeValue(e, width);
    }

    /**
     * Set the size of all nodes in the graph
     *
     * @param s new size of the nodes
     */
    public void setAllNodesSizes(Size s) {
        this.sizes.setAllNodesValues(s);
    }

    /**
     * Set the width of all edges in the graph
     *
     * @param width new width of the edges
     */
    public void setAllEdgesWidths(Double width) {
        this.sizes.setAllEdgesValues(width);
    }

    /**
     * Returns the LayoutProperty associated to the IGraph.
     *
     * @return The LayoutProperty of the IGraph
     */
    public LayoutProperty getLayout() {
        return this.layout;
    }

    /**
     * Returns the coordinates of a given node in the graph
     *
     * @param n the node of interest
     * @return the coordinates of the node
     */
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
    public void setEdgePosition(IEdge e, ArrayList<Coord> bends) {
        this.layout.setEdgeValue(e, bends);
    }

    /**
     * Set the coordinates of all nodes in the graph
     *
     * @param c new coordinates of the nodes
     */
    public void setAllNodesPositions(Coord c) {
        this.layout.setAllNodesValues(c);
    }

    /**
     * Set the coordinates of all edges (ie the coordinates of its bends) in the
     * graph
     *
     * @param bends the bends of the edges
     */
    public void setAllEdgesPositions(ArrayList<Coord> bends) {
        this.layout.setAllEdgesValues(bends);
    }

    /**
     * Returns the coordinates of the bouding box of the graph drawing. The
     * bouding box of a drawing of a graph is the minimum rectangle such that
     * all nodes and edges of the graph are inside the rectangle. Please remind
     * that the y-axis is oriented toward the bottom.
     *
     * @return an ArrayList containing the coordinates of the top-left corner of
     * the bonding box (at the first index in the ArrayList) and the coordinates
     * of the bottom-right corner of the bounding box (second index)
     *
     */
    public ArrayList<Coord> getBoundingBox() {

        // cas vide
        if (nodes == null || nodes.isEmpty()) {
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
                if (c.getX() < minX) 
                    minX = c.getX();
                if (c.getY() < minY) 
                    minY = c.getY();
                if (c.getX() > maxX) 
                    maxX = c.getX();
                if (c.getY() > maxY) 
                    maxY = c.getY();
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
    public ColorProperty getColor() {
        return this.colors;
    }

    /**
     * Returns the color of a given node in the graph.
     *
     * @param n the node of interest
     * @return the color of the node
     */
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
    public void setNodeColor(INode n, Color c) {
        this.colors.setNodeValue(n, c);
    }

    /**
     * Set the color of a given edge in the graph.
     *
     * @param e the edge of interest
     * @param c the bends of the edge
     */
    public void setEdgeColor(IEdge e, Color c) {
        this.colors.setEdgeValue(e, c);
    }

    /**
     * Set the color of all nodes in the graph.
     *
     * @param c the new color of the nodes
     */
    public void setAllNodesColors(Color c) {
        this.colors.setAllNodesValues(c);
    }

    /**
     * Set the color of all edges in the graph.
     *
     * @param c the new color of the edges
     */
    public void setAllEdgesColors(Color c) {
        this.colors.setAllEdgesValues(c);
    }

    /**
     * Color the nodes with Well & Powell algorithm.
     * GraphViewver demande l'action -> graph appelle welshAndPowell -> calcule et modifie les colorPorporerty -> GrapgCanvas lit les propriétés et dessine les bonnes couleurs
     */
    @Override
    public void welshAndPowell() {
        info.iut.sae2.algorithm.WelshAndPowell.execute(this);   // fait appel à la classe que l'on vient de créer en lui passant le graphe courant (this)

    }
    
    /**
     * Color the nodes with a 6 colors algorithm (only for planar graphs)
     */  
    public void sixColors() {
        
    }
    
    /**
     * Color the nodes with a 5 colors algorithm (only for planar graphs)
     */  
    public void fiveColors() {

    }

}