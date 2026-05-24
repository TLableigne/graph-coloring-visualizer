package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.properties.ColorProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * Implementation of the 5-coloring algorithm for planar graphs.
 * @author zakariasabri
 */
public class FiveColoring implements Algorithm<ColorProperty> {

    private final Color[] palette = {
        new Color(255, 0, 0, 255),    // rouge 0
        new Color(0, 0, 255, 255),    // bleu 1
        new Color(0, 255, 0, 255),    // vert 2
        new Color(255, 255, 0, 255),  // jaune 3
        new Color(255, 165, 0, 255)   // orange 4
    };

    /**
     * Executes the 5-coloring algorithm on the targeted planar graph.
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
        
        Map<INode, Integer> colorIndices = assignColorIndicesWithKempeChains(graph, destructionStack);
        
        return createColorProperty(colorIndices);
    }

    /**
     * Builds the destruction stack by repeatedly removing nodes with a virtual degree of 5 or less.
     *
     * @param graph the graph structure to process
     * @return a Stack of nodes in destruction order, or null if the graph is not planar
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
                System.err.println("Erreur : Le graphe n'est pas planaire.");
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
     * Reconstructs the graph by assigning colors, using Kempe Chains to swap colors if necessary.
     *
     * @param graph the graph structure used to check neighbors and paths
     * @param destructionStack the stack containing nodes in the reverse order of coloring
     * @return a map associating each node with an integer color index
     */
    private Map<INode, Integer> assignColorIndicesWithKempeChains(IGraph graph, Stack<INode> destructionStack) {
        Map<INode, Integer> colorIndices = new HashMap<>();

        while (!destructionStack.isEmpty()) {
            INode currentNode = destructionStack.pop();
            ArrayList<INode> coloredNeighbors = new ArrayList<>();
            ArrayList<Integer> usedColors = new ArrayList<>();

            for (INode neighbor : graph.getNeighbors(currentNode)) {
                if (colorIndices.containsKey(neighbor)) {
                    coloredNeighbors.add(neighbor);
                    int col = colorIndices.get(neighbor);
                    if (!usedColors.contains(col)) {
                        usedColors.add(col);
                    }
                }
            }

            int chosenColorIndex = -1;

            // Option 1: A color is naturally available
            if (usedColors.size() < palette.length) {
                int c = 0;
                while (c < palette.length && chosenColorIndex == -1) {
                    if (!usedColors.contains(c)) {
                        chosenColorIndex = c;
                    }
                    c++;
                }
            } 
            // Option 2: All 5 colors are taken, we must find a Kempe Chain to swap
            else {
                boolean swapDone = false;
                int i = 0;
                
                while (i < coloredNeighbors.size() && !swapDone) {
                    int j = i + 1;
                    while (j < coloredNeighbors.size() && !swapDone) {
                        INode nA = coloredNeighbors.get(i);
                        INode nB = coloredNeighbors.get(j);
                        int cA = colorIndices.get(nA);
                        int cB = colorIndices.get(nB);

                        if (cA != cB) {
                            ArrayList<INode> component = new ArrayList<>();
                            Queue<INode> queue = new LinkedList<>();
                            queue.add(nA);
                            component.add(nA);

                            boolean pathFound = false;

                            // Breadth-First Search (BFS) for alternating color path
                            while (!queue.isEmpty() && !pathFound) {
                                INode currentBFS = queue.poll();
                                for (INode bfsNeighbor : graph.getNeighbors(currentBFS)) {
                                    if (bfsNeighbor.equals(nB)) {
                                        pathFound = true;
                                    } else if (colorIndices.containsKey(bfsNeighbor)) {
                                        int nColor = colorIndices.get(bfsNeighbor);
                                        if ((nColor == cA || nColor == cB) && !component.contains(bfsNeighbor)) {
                                            component.add(bfsNeighbor);
                                            queue.add(bfsNeighbor);
                                        }
                                    }
                                }
                            }

                            // If no path connects nA and nB, we can safely swap colors in the isolated component
                            if (!pathFound) {
                                for (INode swapNode : component) {
                                    if (colorIndices.get(swapNode) == cA) {
                                        colorIndices.put(swapNode, cB);
                                    } else {
                                        colorIndices.put(swapNode, cA);
                                    }
                                }
                                chosenColorIndex = cA;
                                swapDone = true;
                            }
                        }
                        j++;
                    }
                    i++;
                }
            }

            // Fallback safety (mathematically guaranteed not to happen on planar graphs if Kempe logic holds)
            if (chosenColorIndex != -1) {
                colorIndices.put(currentNode, chosenColorIndex);
            } else {
                colorIndices.put(currentNode, 0); 
            }
        }

        return colorIndices;
    }

    /**
     * Converts internal color indices into concrete Color objects from the 5-color palette.
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