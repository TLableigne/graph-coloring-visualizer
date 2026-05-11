package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.properties.ColorProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SixColoring implements Algorithm<ColorProperty> {

    private final Color[] palette = {
        new Color(255, 0, 0, 255),
        new Color(0, 0, 255, 255),
        new Color(0, 255, 0, 255),
        new Color(255, 255, 0, 255),
        new Color(255, 165, 0, 255),
        new Color(128, 0, 128, 255)
    };

    @Override
    public ColorProperty apply(IGraph graph, Map<String, Object> parameters) {
        
        Stack<INode> destructionStack = new Stack<>();
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
                destructionStack.push(nodeToRemove);

                for (INode neighbor : graph.getNeighbors(nodeToRemove)) {
                    if (activeNodes.contains(neighbor)) {
                        int currentDegree = virtualDegrees.get(neighbor);
                        virtualDegrees.put(neighbor, currentDegree - 1);
                    }
                }
            }
        }

        if (!isPlanar) {
            return new ColorProperty();
        }

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

        ColorProperty resultProperty = new ColorProperty();

        for (Map.Entry<INode, Integer> entry : colorIndices.entrySet()) {
            INode node = entry.getKey();
            int colorIndex = entry.getValue();
            Color finalColor = palette[colorIndex % palette.length];
            resultProperty.setNodeValue(node, finalColor);
        }

        return resultProperty;
    }
}