package info.iut.sae2;

import info.iut.sae2.viewer.GraphViewer;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 *
 * @author rbourqui
 */
public class Main {
    
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    public static void main(String[] args) throws Exception {
        // Apply a look'n feel
        UIManager.setLookAndFeel(new NimbusLookAndFeel());

        GraphViewer myWindow = new GraphViewer("S2.02 Exploration Algorithmes, SABRI Zakaria LABLEIGNE Thomas");
        myWindow.setVisible(true);
    }
}
