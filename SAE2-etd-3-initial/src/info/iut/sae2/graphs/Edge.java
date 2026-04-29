
package info.iut.sae2.graphs;

import java.util.ArrayList;
import java.util.HashMap;

public class Edge implements IEdge{

    private HashMap<INode, INode> listeNoeux;
    
    public Edge (INode src, INode tgt) {
        this.listeNoeux.put(src, tgt);
    }
    /**
     * Returns the source of the edge
     * 
     * @return the source of the edge
     */
    public INode source() {
        return null;
    }
    
    /**
     * Returns the target of the edge
     * 
     * @return the target of the edge
     */
    public INode target() {
        return null;
    }
    
}
