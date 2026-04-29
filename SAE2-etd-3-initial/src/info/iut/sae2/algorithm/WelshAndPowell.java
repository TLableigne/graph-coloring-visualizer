package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.graphs.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WelshAndPowell {
    
public static void execute(IGraph graph) {
        // je récupère mes noeuds 
        ArrayList<INode> nodes = graph.getNodes(); //

        // on trie les noeuds par ordre décroissant, du degré le plus grand au plus petit
        nodes.sort((n1, n2) -> Integer.compare(graph.degree(n2), graph.degree(n1)));

        // dictionnaire pour stoquer temporairement l’induce de couleur de chaque noeud
        Map<INode, Integer> colorIndices = new HashMap<>();

        // boucle principale, on affecte chaque couleur à un noeud
        for (INode currentNode : nodes) {
            
            // on récuprére les couleurs déjà utilisées par les voisins directs 
            Set<Integer> usedColors = new HashSet<>();
            ArrayList<INode> neighbors = graph.getNeighbors(currentNode);
            
            for (INode neighbor : neighbors) {
                if (colorIndices.containsKey(neighbor)) {
                    usedColors.add(colorIndices.get(neighbor));
                }
            }

            // on trouve la plus petite couleur
            int colorIndex = 0;
            while (usedColors.contains(colorIndex)) {
                colorIndex++;
            }

            // on assigne cet index au noeud
            colorIndices.put(currentNode, colorIndex);
        }

        // on convertit les entiers
        applyColorsToGraph(graph, colorIndices);
    }

    /**
     * méthode pour transformer nos entiers en objet Color
     */
    public static void applyColorsToGraph(IGraph graph, Map<INode, Integer> colorIndices) {
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

        // on applique ensuite la couleur sur le graphe en utilisant les propriétés igraph.setNodeColor
        for (Map.Entry<INode, Integer> entry : colorIndices.entrySet()) {
            INode node = entry.getKey();
            int index = entry.getValue();
            
            // si le graphe nécessite plus de couleurs que la palette on boucle sur la palette avec un modulo
            Color actualColor = palette[index % palette.length];
            
            // Maj de la couleur dans le graphe
            graph.setNodeColor(node, actualColor);
        }
    }


}
