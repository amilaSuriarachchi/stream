package edu.colostate.cs.worker.deploy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 5/18/14
 * Time: 10:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class StreamDBO {

    private String processor;
    private List<NodeDBO> nodes = new ArrayList<NodeDBO>();

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public List<NodeDBO> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeDBO> nodes) {
        this.nodes = nodes;
    }
}
