package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.properties.ColorProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Implementation of the 6-coloring algorithm.
 * This 6 coloration algorithm uses a vertex reduction (destruction) phase using a stack,
 * followed by a color assignment (reconstruction) phase.
 ** @author zakariasabri & tlableigne
 */
public class SixColoring implements Algorithm<ColorProperty> {

    private final Color[] palette = {
            new Color(78, 121, 167, 255),  
            new Color(242, 142, 43, 255),  
            new Color(225, 87, 89, 255),  
            new Color(118, 183, 178, 255), 
            new Color(89, 161, 79, 255),   
            new Color(237, 201, 72, 255),
            new Color(176, 122, 161, 255), 
            new Color(255, 157, 167, 255) 
        };

    /**
     * This method executes the 6 coloring algorithm on the targeted planar graph.
     *
     * @param graph the graph structure to be processed
     * @param parameters optional execution parameters (not used in this implementation)
     * @return a populated ColorProperty containing the mapping between nodes and colors
     */
    @Override
    public ColorProperty apply(IGraph graph, Map<String, Object> parameters) {
        Stack<INode> destructionStack = buildDestructionStack(graph);
        
        if (destructionStack == null) {
            return new ColorProperty();
        }
        
        Map<INode, Integer> colorIndices = assignColorIndices(graph, destructionStack);
        
        return createColorProperty(colorIndices);
    }

    /**
     * Builds the destruction stack by repeatedly removing nodes with a virtual degree of 5 or less.
     *
     * @param graph the graph structure to process
     * @return a Stack of nodes in destruction order, or null if the graph is determined to be non-planar
     */
    private Stack<INode> buildDestructionStack(IGraph graph) {
        Stack<INode> stack = new Stack<>();
        ArrayList<INode> activeNodes = new ArrayList<>(graph.getNodes());
        Map<INode, Integer> virtualDegrees = new HashMap<>();

        for (INode node : activeNodes) {
            virtualDegrees.put(node, graph.degree(node));
        }

        boolean isPlanar = true;

        while (!activeNodes.isEmpty() && isPlanar) {
            INode nodeToRemove = null;
            int i = 0;

            while (i < activeNodes.size() && nodeToRemove == null) {
                INode currentNode = activeNodes.get(i);
                if (virtualDegrees.get(currentNode) <= 5) {
                    nodeToRemove = currentNode;
                }
                i++;
            }

            if (nodeToRemove == null) {
                System.err.println("Error : the graph isn't planar.");
                isPlanar = false;
            } else {
                activeNodes.remove(nodeToRemove);
                stack.push(nodeToRemove);

                for (INode neighbor : graph.getNeighbors(nodeToRemove)) {
                    if (activeNodes.contains(neighbor)) {
                        int currentDegree = virtualDegrees.get(neighbor);
                        virtualDegrees.put(neighbor, currentDegree - 1);
                    }
                }
            }
        }

        if (!isPlanar) {
            return null;
        }

        return stack;
    }

    /**
     * Reconstructs the graph by popping nodes from the stack and greedily assigning color indices.
     *
     * @param graph the graph structure used to check neighbors
     * @param destructionStack the stack containing nodes in the reverse order of coloring
     * @return a map associating each node with an integer color index
     */
    private Map<INode, Integer> assignColorIndices(IGraph graph, Stack<INode> destructionStack) {
        Map<INode, Integer> colorIndices = new HashMap<>();

        while (!destructionStack.isEmpty()) {
            INode currentNode = destructionStack.pop();
            ArrayList<Integer> usedColors = new ArrayList<>();

            for (INode neighbor : graph.getNeighbors(currentNode)) {
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
     * Converts internal color indices into concrete Color objects from the palette.
     *
     * @param colorIndices map containing the assigned integer index for each node
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