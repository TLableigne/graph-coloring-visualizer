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
 *
 * @author zakariasabri
 */
public class WelshAndPowell implements Algorithm<ColorProperty>{
    
    /**
     * This method
     * @param graph
     * @param parameters
     * @return 
     */
    @Override
    public ColorProperty apply(IGraph graph, Map<String, Object> parameters) {
        // we get the nodes and sort them
        ArrayList<INode> nodes = new ArrayList<>(graph.getNodes());
        nodes.sort((n1, n2) -> Integer.compare(graph.degree(n2), graph.degree(n1)));

        // temporary dictionnary for the colors indices
        Map<INode, Integer> colorIndices = new HashMap<>();

        for (INode currentNode : nodes) {
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

        // and finaly we return the colorProperty generated
        return createColorProperty(colorIndices);
    }
    
    

    /**
     * private method to transform our integers in a ColorProperty object
     * @param colorIndices
     * @return a color proprety
     */
    private ColorProperty createColorProperty(Map<INode, Integer> colorIndices) {
        ColorProperty resultProperty = new ColorProperty();
        // on défini une palette de couleur de base
        Color[] palette = {
            new Color(255, 0, 0, 255),    // rouge 0
            new Color(0, 0, 255, 255),    // bleu 1
            new Color(0, 255, 0,255),    // vert 2
            new Color(255, 255, 0, 255),  // jaune 3
            new Color(255, 165, 0, 255),  // orange 4
            new Color(128, 0, 128, 255),  // violet 5
            new Color(0, 255, 255, 255),  // cyan 6
            new Color(255, 192, 203, 255) // rose 7
        };

        for (Map.Entry<INode, Integer> entry : colorIndices.entrySet()) {
            INode node = entry.getKey();
            int index = entry.getValue();
            
            Color actualColor = palette[index % palette.length];
            
            // we stock the color in the ColorProperty
            resultProperty.setNodeValue(node, actualColor);
        }
        
        return resultProperty;
    }


}
