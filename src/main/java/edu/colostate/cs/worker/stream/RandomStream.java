package edu.colostate.cs.worker.stream;

import edu.colostate.cs.worker.comm.CommManager;
import edu.colostate.cs.worker.comm.Node;
import edu.colostate.cs.worker.data.Event;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/11/14
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class RandomStream extends AbstractStream {

    private int nextNodeToAssign;

    protected RandomStream(String destProcessor, String srcProcessor, List<Node> nodes, CommManager commManager) {
        super(destProcessor, srcProcessor, nodes, commManager);
        this.nextNodeToAssign = 0;
    }

    @Override
    protected synchronized Node getNode(Event event) {
        if (this.nodes.size() > 0) {
            Node nextNode = this.nodes.get(this.nextNodeToAssign);
            this.nextNodeToAssign = (this.nextNodeToAssign + 1) % this.nodes.size();
            return nextNode;
        } else {
            return null;
        }
    }

    public synchronized void nodeFailed(Node node) {
        this.nodes.remove(node);
        if (this.nodes.size() > 0) {
            this.nextNodeToAssign = this.nextNodeToAssign % this.nodes.size();
        }
    }
}
