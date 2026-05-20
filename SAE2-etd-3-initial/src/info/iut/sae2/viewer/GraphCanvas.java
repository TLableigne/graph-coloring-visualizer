package info.iut.sae2.viewer;

import info.iut.sae2.graphs.Coord;
import info.iut.sae2.graphs.IEdge;
import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.graphs.Size;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import static java.lang.Math.max;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author rbourqui
 */
public class GraphCanvas extends Canvas {

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    IGraph graph;

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    Map<INode, NodeView> nodeMap;

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    Map<IEdge, EdgeView> edgeMap;

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    boolean smoothEdge = true;

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    int nodeRadius = 5;

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    int edgeTransparency = 60;

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    static final int MARGIN = 20;

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    static final Color NODE_COLOR = Color.RED;

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    static Color EDGE_COLOR = new Color(120, 120, 120, 60);

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    GraphCanvas() {
        graph = null;
        nodeMap = new HashMap<>();
        edgeMap = new HashMap<>();
    }

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    private void initNodeMap() {
        if (graph == null) {
            return;
        }
        ArrayList<Coord> bb = graph.getBoundingBox();
        for (INode n : graph.getNodes()) {
            Coord cDataSpace = graph.getNodePosition(n);
            Coord cScreenSpace = graphPositionToViewPosition(cDataSpace, bb);
            Size sDataSpace = graph.getNodeSize(n);
            Size sScreenSpace = graphSizeToViewSize(sDataSpace, bb);
            int radius = (int) (sScreenSpace.getW() / 2);

            cScreenSpace.setX( cScreenSpace.getX() - radius);
            cScreenSpace.setY( cScreenSpace.getY() - radius);
            info.iut.sae2.graphs.Color c = graph.getNodeColor(n);
            NodeView nv = new NodeView((int) cScreenSpace.getX(), (int) cScreenSpace.getY(), radius, new Color(c.getR(), c.getG(),c.getB(), c.getA()));
            nodeMap.put(n, nv);
        }
    }

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    private void initEdgeMap() {
        ArrayList<Coord> bb = graph.getBoundingBox();

        for (IEdge e : graph.getEdges()) {
            int[] x;
            int[] y;
            ArrayList<Coord> bends = graph.getEdgePosition(e);
            x = new int[bends.size() + 2];
            y = new int[bends.size() + 2];
            int nbPoints = 0;

            INode nSrc = graph.source(e);
            Coord src = graphPositionToViewPosition(graph.getNodePosition(nSrc), bb);

            x[0] = (int) (src.getX());
            y[0] = (int) (src.getY());
            nbPoints++;
            for (Coord cur : bends) {
                cur = graphPositionToViewPosition(cur, bb);
                x[nbPoints] = (int) cur.getX();
                y[nbPoints] = (int) cur.getY();
                nbPoints++;
            }
            INode nTgt = graph.target(e);
            Coord tgt = graphPositionToViewPosition(graph.getNodePosition(nTgt), bb);

            x[nbPoints] = (int) (tgt.getX());
            y[nbPoints] = (int) (tgt.getY());

            info.iut.sae2.graphs.Color c = graph.getEdgeColor(e);
            EdgeView ev = new EdgeView(x, y, new Color(c.getR(), c.getG(),c.getB(), c.getA()), smoothEdge);
            edgeMap.put(e, ev);
        }
    }

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    private void initMaps() {
        nodeMap = new HashMap<>();
        edgeMap = new HashMap<>();
        initNodeMap();
        initEdgeMap();
    }

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    @Override
    public void paint(Graphics g) {
        Image img = createImage(getWidth(), getHeight());
        Graphics2D imgG = (Graphics2D) img.getGraphics();
        imgG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        setBackground(Color.WHITE);

        for (EdgeView ev : edgeMap.values()) {
            ev.draw(imgG);
        }

        for (NodeView nv : nodeMap.values()) {
            nv.draw(imgG);
        }

        g.drawImage(img, 0, 0, null);
    }

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    Coord graphPositionToViewPosition(Coord c, ArrayList<Coord> boundingBox) {
        double bbWidth = (boundingBox.get(1).getX() - boundingBox.get(0).getX());
        double bbHeight = (boundingBox.get(1).getY() - boundingBox.get(0).getY());

        int width = getWidth() - 2 * MARGIN;
        int height = getHeight() - 2 * MARGIN;

        // scale so as to maximize the drawing
        double distX, distY;
        double ratio = max(bbWidth / width, bbHeight / height);
        if (bbWidth > 0) {
            distX = (c.getX() - boundingBox.get(0).getX());
        } else {
            distX = 0.5;
        }
        if (bbHeight > 0) {
            distY = (c.getY() - boundingBox.get(0).getY());
        } else {
            distY = 0.5;
        }

        int x = (int) (distX / ratio) + MARGIN;
        int y = (int) (distY / ratio) + MARGIN;

        // translate to center the drawing
        if (bbHeight / ratio < height) {
            y += (height - bbHeight / ratio) / 2;
        }
        if (bbWidth / ratio < width) {
            x += (width - bbWidth / ratio) / 2;
        }

        return new Coord(x, y);

    }

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    Size graphSizeToViewSize(Size s, ArrayList<Coord> boundingBox) {
        double bbWidth = (boundingBox.get(1).getX() - boundingBox.get(0).getX());
        double bbHeight = (boundingBox.get(1).getY() - boundingBox.get(0).getY());

        int width = getWidth() - 2 * MARGIN;
        int height = getHeight() - 2 * MARGIN;

        double ratio = max(bbWidth / width, bbHeight / height);

        int w = (int) (s.getW() / ratio);
        int h = (int) (s.getH() / ratio);

        return new Size(w, h);

    }

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    void setGraph(IGraph g) {
        graph = g;
        if (graph != null) {
            initMaps();
        }
    }

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    void setRadius(int radius) {
        nodeRadius = radius;
        initNodeMap();
        initEdgeMap();
    }

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    void setEdgeTransparency(int alpha) {
        for(IEdge e : graph.getEdges()){
            info.iut.sae2.graphs.Color c = graph.getEdgeColor(e);
            c.setA(alpha);
        }
        updateEdgeColors();
    }

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    private void updateEdgeColors(){
           for(Entry e : edgeMap.entrySet()){
            IEdge edge = (IEdge) e.getKey();
            EdgeView ev = (EdgeView) e.getValue();
            info.iut.sae2.graphs.Color c = graph.getEdgeColor(edge);
            ev.updateColor(new Color(c.getR(), c.getG(), c.getB(), c.getA()));
        }
    }
    
    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    private void updateNodeColors(){
        for(Entry e : nodeMap.entrySet()){
            INode n = (INode) e.getKey();
            NodeView nv = (NodeView) e.getValue();
            info.iut.sae2.graphs.Color c = graph.getNodeColor(n);
            nv.updateColor(new Color(c.getR(), c.getG(), c.getB(), c.getA()));
        }
    }
    
    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    void updateColors(){
        updateNodeColors();
        updateEdgeColors();
    }
    
    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    void setSmooth(boolean smooth) {
        smoothEdge = smooth;

        for (EdgeView ev : edgeMap.values()) {
            ev.updateSmoothed(smooth);
        }
    }
}
