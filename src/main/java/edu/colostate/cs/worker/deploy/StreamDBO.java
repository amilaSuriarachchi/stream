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
    /**
     * target destProcessor to send the message. Always one stream has one target destProcessor
     */
    private String processor;
    /**
     * Type of the stream. This can be a key stream or a random stream
     */
    private String type;
    /**
     * Node set of which the processors has deployed.
     */
    private List<NodeDBO> nodes = new ArrayList<NodeDBO>();

    public void addNode(String ip, int port){
        this.nodes.add(new NodeDBO(ip, port));
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<NodeDBO> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeDBO> nodes) {
        this.nodes = nodes;
    }
}
