package edu.colostate.cs.worker.stream;

import edu.colostate.cs.worker.comm.CommManager;
import edu.colostate.cs.worker.comm.Node;
import edu.colostate.cs.worker.comm.exception.MessageProcessingException;
import edu.colostate.cs.worker.data.Event;
import edu.colostate.cs.worker.data.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/11/14
 * Time: 12:04 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractStream implements Stream {

    // this is the processing element which needs to send the event to next worker.
    protected String processor;

    // this is the physical worker this stream has to send the message.
    protected List<Node> nodes;

    // communication manager for this worker. All underline communications should happen through this.
    protected CommManager commManager;

    protected AbstractStream(String processor, List<Node> nodes, CommManager commManager) {
        this.processor = processor;
        this.nodes = nodes;
        this.commManager = commManager;
    }

    public void emit(Event event) throws MessageProcessingException {
        Message message = new Message(this.processor, event);
        this.commManager.sendEvent(message, getNode(event));
    }

    public void emit(List<Event> events) throws MessageProcessingException {

        Map<Node, List<Message>> nodeMessageMap = new HashMap<Node, List<Message>>();
        // populate this for all nodes
        for (Node node : this.nodes){
            nodeMessageMap.put(node, new ArrayList<Message>());
        }
        Message message = null;
        Node nodeToSend = null;

        for (Event event : events){
            message = new Message(this.processor, event);
            nodeToSend = getNode(event);
            nodeMessageMap.get(nodeToSend).add(message);
        }

        for (Node node : this.nodes){
            this.commManager.sendEvents(nodeMessageMap.get(node), node);
        }
    }

    protected abstract Node getNode(Event event);
}
