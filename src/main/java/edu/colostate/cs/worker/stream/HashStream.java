package edu.colostate.cs.worker.stream;

import edu.colostate.cs.worker.comm.CommManager;
import edu.colostate.cs.worker.comm.Node;
import edu.colostate.cs.worker.data.Event;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/2/15
 * Time: 9:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class HashStream extends AbstractStream {

    public HashStream(String destProcessor, String srcProcessor, List<Node> nodes, CommManager commManager) {
        super(destProcessor, srcProcessor, nodes, commManager);
    }

    @Override
    protected Node getNode(Event event) {
        int index = event.getKey().hashCode() % this.nodes.size();
        return this.nodes.get(index);
    }

    public void nodeFailed(Node node) {
        this.nodes.remove(node);
    }
}
