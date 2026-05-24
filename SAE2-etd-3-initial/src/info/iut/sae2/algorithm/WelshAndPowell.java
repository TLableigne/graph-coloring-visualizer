package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.properties.ColorProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of the Welsh and Powell graph coloring algorithm.
 *
 * @author zakariasabri & tlableigne
 */
public class WelshAndPowell implements Algorithm<ColorProperty> {

    /**
     * This method executes the Welsh and Powell coloring algorithm on the graph
     * asked by the user.
     *
     * @param graph the graph structure to be processed
     * @param parameters optional execution parameters (not used in this
     * implementation)
     * @return a populated ColorProperty containing the mapping between nodes
     * and colors
     */
    @Override
    public ColorProperty apply(IGraph graph, Map<String, Object> parameters) {
        ArrayList<INode> sortedNodes = sortNodesByDegreeDesc(graph);
        Map<INode, Integer> colorIndices = assignColorIndices(graph, sortedNodes);

        return createColorProperty(colorIndices);
    }

    /**
     * This method sorts the graph nodes in descending order of their degree using a manual
     * selection sort. It use a precomputed map of degrees to avoid
     * performance delays (because in some graphs we have a lot of nodes to sort).
     *
     * @param graph the graph containing the nodes to sort
     * @return a new ArrayList of nodes sorted by descending degree
     */
    private ArrayList<INode> sortNodesByDegreeDesc(IGraph graph) {
        ArrayList<INode> nodes = new ArrayList<>(graph.getNodes());

        Map<INode, Integer> nodeDegrees = new HashMap<>();
        for (INode node : nodes) {
            nodeDegrees.put(node, graph.degree(node));
        }

        for (int i = 0; i < nodes.size() - 1; i++) {
            int maxIndex = i;

            for (int j = i + 1; j < nodes.size(); j++) {
                if (nodeDegrees.get(nodes.get(j)) > nodeDegrees.get(nodes.get(maxIndex))) {
                    maxIndex = j;
                }
            }

            INode temp = nodes.get(maxIndex);
            nodes.set(maxIndex, nodes.get(i));
            nodes.set(i, temp);
        }

        return nodes;
    }

    /**
     * This method assign an integer color index to each node based on the Welsh
     * and Powell greedy strategy.
     *
     * @param graph the graph structure used to check neighborhoods
     * @param sortedNodes the list of nodes ordered by degree
     * @return a map associating each node with an integer representing his
     * color index
     */
    private Map<INode, Integer> assignColorIndices(IGraph graph, ArrayList<INode> sortedNodes) {
        Map<INode, Integer> colorIndices = new HashMap<>();

        for (INode currentNode : sortedNodes) {
            Set<Integer> usedColors = new HashSet<>();

            for (INode neighbor : graph.getNeighbors(currentNode)) {
                if (colorIndices.containsKey(neighbor)) {
                    usedColors.add(colorIndices.get(neighbor));
                }
            }

            int colorIndex = 0;
            while (usedColors.contains(colorIndex)) {
                colorIndex++;
            }

            colorIndices.put(currentNode, colorIndex);
        }

        return colorIndices;
    }

    /**
     * This method converts internal color indices into Color objects inside a
     * ColorProperty mapping.
     *
     * @param colorIndices map containing the assigned integer index for each
     * node
     * @return the final ColorProperty object expected by the application
     * architecture
     */
    private ColorProperty createColorProperty(Map<INode, Integer> colorIndices) {
        ColorProperty resultProperty = new ColorProperty();

        Color[] palette = {
            new Color(255, 0, 0, 255), // red 0
            new Color(0, 0, 255, 255), // blue 1
            new Color(0, 255, 0, 255), // green 2
            new Color(255, 255, 0, 255), // yellow 3
            new Color(255, 165, 0, 255), // orange 4
            new Color(128, 0, 128, 255), // purple 5
            new Color(0, 255, 255, 255), // cyan 6
            new Color(255, 192, 203, 255) // rose 7
        };

        for (Map.Entry<INode, Integer> entry : colorIndices.entrySet()) {
            INode node = entry.getKey();
            int index = entry.getValue();

            Color actualColor = palette[index % palette.length];
            resultProperty.setNodeValue(node, actualColor);
        }

        return resultProperty;
    }
}
