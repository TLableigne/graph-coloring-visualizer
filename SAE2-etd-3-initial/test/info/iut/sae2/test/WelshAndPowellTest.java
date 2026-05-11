package info.iut.sae2.test;

import info.iut.sae2.algorithm.WelshAndPowell;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.Graph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.properties.ColorProperty;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zakariasabri
 */
public class WelshAndPowellTest {
    @Test 
    public void testGrapheTriangle() { // Si les tests ne passent pas il faut importer les 2 librairies necessaires à partir du fichier lib du dépot (junit-4.13.2.jar et hamcrest-core-1.3.jar)
        // we create a new graph
        Graph g = new Graph();
        
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();

        // we make a triangle by reaching all the points
        g.addEdge(n1, n2);
        g.addEdge(n2, n3);
        g.addEdge(n1, n3);

        // on instancie et récupere le colorPropety
        WelshAndPowell wp = new WelshAndPowell();
        ColorProperty computedColors = wp.apply(g, null);

        // verifications 
        //on cherche les couleurs dans le computed colors
        Color c1 = computedColors.getNodeValue(n1);
        Color c2 = computedColors.getNodeValue(n2);
        Color c3 = computedColors.getNodeValue(n3);

        // we verify that we have the right colors 
        assertNotNull("Le noeud 1 devrait avoir une couleur", c1);
        assertNotNull("Le noeud 2 devrait avoir une couleur", c2);
        assertNotNull("Le noeud 3 devrait avoir une couleur", c3);

        // we verify that all the colors are different 
       // Si c1 est égal à c2 (true), assertFalse fera échouer le test.
        assertFalse("n1 et n2 sont reliés, ils ne doivent pas avoir la même couleur", c1.equals(c2));
        assertFalse("n1 et n3 sont reliés, ils ne doivent pas avoir la même couleur", c1.equals(c3));
        assertFalse("n2 et n3 sont reliés, ils ne doivent pas avoir la même couleur", c2.equals(c3));
    }
}
