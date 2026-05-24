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
import java.util.Stack;

/**
 * Implementation of the 6-coloring algorithm for planar graphs. 
 * The following algorithm uses a physical vertex reduction phase on a graph copy, followed by a greedy
 * color assignment phase.
 *
 * @author zakariasabri & tlableigne
 */
public class SixColorationPlanarAlgorithm implements Algorithm<ColorProperty> {

    private final Color[] palette = {
        new Color(89, 161, 79, 255),
        new Color(176, 122, 161, 255),
        new Color(78, 121, 167, 255),
        new Color(255, 157, 167, 255),
        new Color(237, 201, 72, 255),
        new Color(242, 142, 43, 255),
        new Color(118, 183, 178, 255),
        new Color(225, 87, 89, 255)
    };

    /**
     * Executes the 6-coloring algorithm on the targeted planar graph. Works on
     * a deep copy of the graph to avoid mutating the original structure.
     *
     * @param graph the original planar graph
     * @param parameters optional execution parameters
     * @return a populated ColorProperty containing the mapping between nodes
     * and colors
     */
    @Override
    public ColorProperty apply(IGraph graph, Map<String, Object> parameters) {
        Map<INode, INode> origToCopy = new HashMap<>();
        Map<INode, INode> copyToOrig = new HashMap<>();
        Graph graphCopy = createGraphCopy(graph, origToCopy, copyToOrig);

        Stack<INode> destructionStack = buildDestructionStack(graphCopy, copyToOrig);

        if (destructionStack == null) {
            return new ColorProperty();
        }

        Map<INode, Integer> colorIndices = assignColorIndices(graph, destructionStack);

        return createColorProperty(colorIndices);
    }

    /**
     * Creates a deep copy of the given graph.
     *
     * @param graph the graph to copy
     * @param origToCopy map to store original to copy references
     * @param copyToOrig map to store copy to original references
     * @return the copied graph
     */
    private Graph createGraphCopy(IGraph graph, Map<INode, INode> origToCopy, Map<INode, INode> copyToOrig) {
        Graph copy = new Graph();
        for (INode node : graph.getNodes()) {
            INode newNode = copy.addNode();
            origToCopy.put(node, newNode);
            copyToOrig.put(newNode, node);
        }

        for (IEdge edge : graph.getEdges()) {
            copy.addEdge(origToCopy.get(edge.source()), origToCopy.get(edge.target()));
        }
        return copy;
    }

    /**
     * Builds the destruction stack by repeatedly physically removing nodes with
     * a degree of 5 or less.
     *
     * @param copyGraph the physical copy of the graph to destruct
     * @param copyToOrig the reference map to push original nodes to the stack
     * @return a Stack of original nodes in destruction order, or null if the
     * graph is not planar
     */
    private Stack<INode> buildDestructionStack(IGraph copyGraph, Map<INode, INode> copyToOrig) {
        Stack<INode> stack = new Stack<>();

        while (copyGraph.numberOfNodes() > 0) {
            INode nodeToRemove = null;
            ArrayList<INode> nodesList = copyGraph.getNodes();
            int i = 0;

            while (i < nodesList.size() && nodeToRemove == null) {
                INode node = nodesList.get(i);
                if (copyGraph.degree(node) <= 5) {
                    nodeToRemove = node;
                }
                i++;
            }

            if (nodeToRemove == null) {
                System.err.println("Error : the graph is not planar.");
                return null;
            }

            stack.push(copyToOrig.get(nodeToRemove));
            copyGraph.delNode(nodeToRemove);
        }

        return stack;
    }

    /**
     * Reconstructs the graph by popping nodes from the stack and greedily
     * assigning color indices.
     *
     * @param originalGraph the untouched original graph used to check neighbors
     * @param destructionStack the stack containing original nodes in the
     * reverse order of coloring
     * @return a map associating each node with an integer color index
     */
    private Map<INode, Integer> assignColorIndices(IGraph originalGraph, Stack<INode> destructionStack) {
        Map<INode, Integer> colorIndices = new HashMap<>();

        while (!destructionStack.isEmpty()) {
            INode currentNode = destructionStack.pop();
            ArrayList<Integer> usedColors = new ArrayList<>();

            for (INode neighbor : originalGraph.getNeighbors(currentNode)) {
                if (colorIndices.containsKey(neighbor)) {
                    usedColors.add(colorIndices.get(neighbor));
                }
            }

            int chosenColorIndex = 0;
            while (usedColors.contains(chosenColorIndex)) {
                chosenColorIndex++;
            }

            colorIndices.put(currentNode, chosenColorIndex);
        }

        return colorIndices;
    }

    /**
     * Converts internal color indices into concrete Color objects from the
     * palette.
     *
     * @param colorIndices map containing the assigned integer index for each
     * original node
     * @return the final ColorProperty object mapping nodes to colors
     */
    private ColorProperty createColorProperty(Map<INode, Integer> colorIndices) {
        ColorProperty resultProperty = new ColorProperty();

        for (Map.Entry<INode, Integer> entry : colorIndices.entrySet()) {
            INode node = entry.getKey();
            int index = entry.getValue();

            Color finalColor = palette[index % palette.length];
            resultProperty.setNodeValue(node, finalColor);
        }

        return resultProperty;
    }
}
