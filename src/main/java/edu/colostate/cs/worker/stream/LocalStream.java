package edu.colostate.cs.worker.stream;

import edu.colostate.cs.worker.comm.CommManager;
import edu.colostate.cs.worker.comm.Node;
import edu.colostate.cs.worker.comm.exception.MessageProcessingException;
import edu.colostate.cs.worker.data.Event;
import edu.colostate.cs.worker.data.Message;

import java.util.List;

/**
 * this class is used to route messages locally i.e using just java calls to other processes.
 *
 */
public class LocalStream extends AbstractStream {

    public LocalStream(String destProcessor, String srcProcessor, List<Node> nodes, CommManager commManager) {
        super(destProcessor, srcProcessor, nodes, commManager);
    }

    @Override
    public void emit(Event event) throws MessageProcessingException {
        // here we simply override this method simply to send messages via communication manager.
        Message message = new Message(this.destProcessor, this.srcProcessor, event);
        this.commManager.sendEvent(message);
    }

    @Override
    protected Node getNode(Event event) {
        return null;
    }

    public void nodeFailed(Node node) {

    }
}
