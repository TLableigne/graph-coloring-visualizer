
package info.iut.sae2.graphs;



public class Edge implements IEdge{

    private INode source;
    private INode target;
    
    public Edge (INode src, INode tgt) {
        this.source = src;
        this.target = tgt;
    }
    /**
     * Returns the source of the edge
     * 
     * @return the source of the edge
     */
    @Override
    public INode source() {
        return this.source;
    }
    
    /**
     * Returns the target of the edge
     * 
     * @return the target of the edge
     */
    @Override
    public INode target() {
        return this.target;
    }
    
}
