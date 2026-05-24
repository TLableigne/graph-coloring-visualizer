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
     * This method sorts the graph nodes in descending order of their degree using Java's native
     * sort. It avoids performance delays (because in some graphs we have a lot of nodes to sort).
     *
     * @param graph the graph containing the nodes to sort
     * @return a new ArrayList of nodes sorted by descending degree
     */
    private ArrayList<INode> sortNodesByDegreeDesc(IGraph graph) {
        ArrayList<INode> nodes = new ArrayList<>(graph.getNodes());
        nodes.sort((n1, n2) -> Integer.compare(graph.degree(n2), graph.degree(n1)));
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
            new Color(78, 121, 167, 255),  
            new Color(242, 142, 43, 255),  
            new Color(225, 87, 89, 255),  
            new Color(118, 183, 178, 255), 
            new Color(89, 161, 79, 255),   
            new Color(237, 201, 72, 255),
            new Color(176, 122, 161, 255), 
            new Color(255, 157, 167, 255) 
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