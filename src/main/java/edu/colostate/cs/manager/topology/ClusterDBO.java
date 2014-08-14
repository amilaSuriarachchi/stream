package edu.colostate.cs.manager.topology;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/13/14
 * Time: 2:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClusterDBO {

    private String name;
    private List<NodeDBO> nodes;
    private int nextNode = 0;

    public NodeDBO getNextNode() {
        NodeDBO nextNode = this.nodes.get(this.nextNode);
        this.nextNode = (this.nextNode + 1) % this.nodes.size();
        return nextNode;
    }

    public int getSize(){
        return this.nodes.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NodeDBO> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeDBO> nodes) {
        this.nodes = nodes;
    }
}
