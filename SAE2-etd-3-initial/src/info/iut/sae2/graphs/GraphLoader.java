/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.graphs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 *
 * @author rbourqui, zsabri, tlableigne
 */
public class GraphLoader {

    /**
     * Load and return a IGraph from the two specified files.
     *
     * @param nodeFileName path to the file containing nodes informations
     * @param edgeFileName path to the file containing edges informations
     * @return the graph containing the nodes and edges of the specified files
     */
    public static IGraph loadFromFile(String nodeFileName, String edgeFileName) {
        if (nodeFileName == null) {
            return null;
        }
        IGraph g = new Graph();
        HashMap<Integer, INode> nodeMap = loadNodesFromFile(g, nodeFileName);

        if (edgeFileName != null) {
            loadEdgesFromFile(g, nodeMap, edgeFileName);
        }
        return g;
    }

    /**
     * Load and add the nodes described in the specified file.
     *
     * @param g            the graph where the nodes are added
     * @param nodeFileName path to the file containing edges informations
     * @return a map associating the node ids in the file and the graph nodes
     */
    private static HashMap<Integer, INode> loadNodesFromFile(IGraph g, String nodeFileName) {
        Path pathToFile = Paths.get(nodeFileName);
        HashMap<Integer, INode> nodeMap = new HashMap<>();

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(";");
                if (attributes.length != 2) {
                    System.err.println("Error while loading nodes : " + attributes.length + " column(s)");
                    continue;
                }
                int id = Integer.parseInt(attributes[0]);
                String[] coords = attributes[1].split(" ");
                if (coords.length != 2) {
                    System.err.println("Error while loading nodes : coordinates have " + coords.length + " dimensions");
                    continue;
                }
                double x = Double.parseDouble(coords[0]);
                double y = Double.parseDouble(coords[1]);

                INode n = g.addNode();
                g.setNodePosition(n, new Coord(x, y));
                nodeMap.put(id, n);
                line = br.readLine();
            }

        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        return nodeMap;
    }

    /*
     * Load and add the edges described in the specified file.
     *
     * @param g the graph where the edges are added
     * 
     * @param nodeMap the map associating nodes ids in the file and nodes in the
     * graph
     * 
     * @param edgeFileName path to the file containing edges informations
     */
    private static void loadEdgesFromFile(IGraph g, HashMap<Integer, INode> nodeMap, String edgeFileName) {
            String sep = ";";
            try { BufferedReader br = new BufferedReader(new FileReader(edgeFileName));
            String line;
            while ((line = br.readLine()) != null) {
            
            String[] parts = line.split(sep);
            int src = Integer.parseInt(parts[0]);
            int tgt = Integer.parseInt(parts[1]);
            g.addEdge(nodeMap.get(src), nodeMap.get(tgt));    // we don't need a for loop, we add our edge one single time
            
        }
     } catch (IOException e) {
        // Gestion de l'erreur si le fichier est introuvable ou illisible
        System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
    } catch (NumberFormatException e) {
        // Gestion de l'erreur si le fichier contient des lettres au lieu de nombres
        System.err.println("Erreur de format de nombre dans le fichier : " + e.getMessage());
    }

    
}
    }
