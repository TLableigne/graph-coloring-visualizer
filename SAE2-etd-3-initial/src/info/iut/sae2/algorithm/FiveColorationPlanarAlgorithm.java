package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.Graph;
import info.iut.sae2.graphs.IEdge;
import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.properties.ColorProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the 5-coloring algorithm for planar graphs.
 * This algorithm is an optimized recursive implementation based on the method proposed
 * by Chiba, Nishizeki, and Saito (1981).
 *
 * @author zakariasabri & tlableigne
 */
public class FiveColorationPlanarAlgorithm implements Algorithm<ColorProperty> {

    private final Color[] palette = {
        new Color(118, 183, 178, 255),
        new Color(89, 161, 79, 255),
        new Color(78, 121, 167, 255),
        new Color(242, 142, 43, 255),
        new Color(225, 87, 89, 255)
    };

    /**
     * this following method executes the recursive 5-coloring algorithm on a copy of the graph.
     *
     * @param graph the original planar graph
     * @param parameters parameters
     * @return a ColorProperty containing the mapping between original nodes and colors
     */
    @Override
    public ColorProperty apply(IGraph graph, Map<String, Object> parameters) {
        Map<INode, INode> origToCopy = new HashMap<>();
        Map<INode, INode> copyToOrig = new HashMap<>();
        Graph graphCopy = createGraphCopy(graph, origToCopy, copyToOrig);

        Map<INode, Integer> copyColorMap = new HashMap<>();
        recursiveColoring(graphCopy, copyColorMap);

        return buildResultProperty(copyColorMap, copyToOrig);
    }

    /**
     * method who creates a copy of the given graph.
     *
     * @param graph the graph to copy
     * @param origToCopy map to store original to copy references
     * @param copyToOrig map to store copy to original references
     * @return the copied graph structure
     */
    private Graph createGraphCopy(IGraph graph, Map<INode, INode> origToCopy, Map<INode, INode> copyToOrig) {
        Graph copy = new Graph();
        ArrayList<INode> nodes = graph.getNodes();
        int i = 0;
        while (i < nodes.size()) {
            INode node = nodes.get(i);
            INode newNode = copy.addNode();
            origToCopy.put(node, newNode);
            copyToOrig.put(newNode, node);
            i++;
        }

        ArrayList<IEdge> edges = graph.getEdges();
        int j = 0;
        while (j < edges.size()) {
            IEdge edge = edges.get(j);
            copy.addEdge(origToCopy.get(edge.source()), origToCopy.get(edge.target()));
            j++;
        }
        return copy;
    }

    /**
     * The core flattened recursive function that destructs and colors the graph dynamically.
     *
     * @param graph the copied graph being reduced during recursion
     * @param colorMap the map accumulating the assigned integer color indices
     */
    private void recursiveColoring(IGraph graph, Map<INode, Integer> colorMap) {
        if (graph.numberOfNodes() <= 5) {
            int colorIndex = 0;
            ArrayList<INode> nodes = graph.getNodes();
            int i = 0;
            while (i < nodes.size()) {
                colorMap.put(nodes.get(i), colorIndex);
                colorIndex++;
                i++;
            }
        } else {
            ArrayList<INode> nodesList = graph.getNodes();
            INode targetNode = null;
            boolean isDeg5 = false;
            
            int i = 0;
            while (i < nodesList.size() && targetNode == null) {
                INode node = nodesList.get(i);
                if (graph.degree(node) <= 4) {
                    targetNode = node;
                }
                i++;
            }

            if (targetNode == null) {
                int j = 0;
                while (j < nodesList.size() && targetNode == null) {
                    INode node = nodesList.get(j);
                    if (graph.degree(node) == 5) {
                        targetNode = node;
                        isDeg5 = true;
                    }
                    j++;
                }
            }

            if (targetNode != null) {
                ArrayList<INode> rawNeighbors = graph.getNeighbors(targetNode);
                int neighCount = rawNeighbors.size();
                INode[] neighborsArray = new INode[neighCount];
                int nIdx = 0;
                while (nIdx < neighCount) {
                    neighborsArray[nIdx] = rawNeighbors.get(nIdx);
                    nIdx++;
                }

                INode u = null;
                INode v = null;

                if (isDeg5) {
                    int id1 = 0;
                    while (id1 < neighCount && u == null) {
                        int id2 = id1 + 1;
                        while (id2 < neighCount && u == null) {
                            INode n1 = neighborsArray[id1];
                            INode n2 = neighborsArray[id2];
                            if (!graph.getNeighbors(n1).contains(n2)) {
                                u = n1;
                                v = n2;
                            }
                            id2++;
                        }
                        id1++;
                    }
                }

                INode[] vNeighborsArray = null;
                if (u != null && v != null) {
                    ArrayList<INode> rawVNeighbors = graph.getNeighbors(v);
                    vNeighborsArray = new INode[rawVNeighbors.size()];
                    int vIdx = 0;
                    while (vIdx < rawVNeighbors.size()) {
                        vNeighborsArray[vIdx] = rawVNeighbors.get(vIdx);
                        vIdx++;
                    }

                    graph.delNode(v);

                    int wIdx = 0;
                    while (wIdx < vNeighborsArray.length) {
                        INode w = vNeighborsArray[wIdx];
                        if (w != u && w != targetNode && !graph.getNeighbors(u).contains(w)) {
                            graph.addEdge(u, w);
                        }
                        wIdx++;
                    }
                }

                graph.delNode(targetNode);

                nodesList = null;
                rawNeighbors = null;
                
                recursiveColoring(graph, colorMap);

                if (u != null && v != null) {
                    colorMap.put(v, colorMap.get(u));
                }

                boolean[] usedColors = new boolean[6];
                int cIdx = 0;
                while (cIdx < neighCount) {
                    Integer col = colorMap.get(neighborsArray[cIdx]);
                    if (col != null && col < 6) {
                        usedColors[col] = true;
                    }
                    cIdx++;
                }

                int finalColor = 0;
                boolean foundColor = false;
                while (finalColor < 6 && !foundColor) {
                    if (!usedColors[finalColor]) {
                        foundColor = true;
                    } else {
                        finalColor++;
                    }
                }

                if (foundColor) {
                    colorMap.put(targetNode, finalColor);
                } else {
                    colorMap.put(targetNode, 0);
                }
            }
        }
    }

    /**
     * this method translate the internal mapped colors back to the original graph nodes and actual Color objects.
     *
     * @param copyColorMap the internal map of integer color indices assigned to the copied nodes
     * @param copyToOrig the reference map linking copied nodes back to original nodes
     * @return the final formatted ColorProperty
     */
    private ColorProperty buildResultProperty(Map<INode, Integer> copyColorMap, Map<INode, INode> copyToOrig) {
        ColorProperty resultProperty = new ColorProperty();
        for (Map.Entry<INode, Integer> entry : copyColorMap.entrySet()) {
            INode originalNode = copyToOrig.get(entry.getKey());
            Color finalColor = palette[entry.getValue() % palette.length];
            resultProperty.setNodeValue(originalNode, finalColor);
        }
        return resultProperty;
    }
}