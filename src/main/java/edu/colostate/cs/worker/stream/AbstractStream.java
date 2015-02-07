package edu.colostate.cs.worker.stream;

import edu.colostate.cs.worker.comm.CommManager;
import edu.colostate.cs.worker.comm.Node;
import edu.colostate.cs.worker.comm.client.FailureCallback;
import edu.colostate.cs.worker.comm.exception.MessageProcessingException;
import edu.colostate.cs.worker.config.Configurator;
import edu.colostate.cs.worker.data.Event;
import edu.colostate.cs.worker.data.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/11/14
 * Time: 12:04 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractStream implements Stream, FailureCallback {

    private Logger logger = Logger.getLogger(AbstractStream.class.getName());

    // this is the processing element which needs to send the event to next worker.
    protected String destProcessor;

    protected String srcProcessor;

    // this is the physical worker this stream has to send the message.
    protected List<Node> nodes;

    // communication manager for this worker. All underline communications should happen through this.
    protected CommManager commManager;

    private Map<Node, List<Message>> messagesToSend;

    private int messageBufferSize;

    protected AbstractStream(String destProcessor,
                             String srcProcessor,
                             List<Node> nodes,
                             CommManager commManager) {
        this.destProcessor = destProcessor;
        this.srcProcessor = srcProcessor;
        this.nodes = nodes;
        this.commManager = commManager;
        this.commManager.addClientConnections(nodes);
        this.commManager.registerFailureCallback(this);
        this.messagesToSend = new HashMap<Node, List<Message>>();
        this.messageBufferSize =  Configurator.getInstance().getMessageBufferSize();
        for (Node node : this.nodes) {
            this.messagesToSend.put(node, new ArrayList<Message>(this.messageBufferSize));
        }

    }

    public void emit(Event event) throws MessageProcessingException {
        Message message = new Message(this.destProcessor, this.srcProcessor, event);
        Node nextNode = getNode(event);
        List<Message> sendMessages = getMessagesToSend(message, nextNode);
        if (sendMessages != null){
            this.commManager.sendEvents(sendMessages, nextNode);
        }

    }

    private synchronized List<Message> getMessagesToSend(Message message, Node node) {
        List<Message> messagesToSend = null;
        this.messagesToSend.get(node).add(message);
        if (this.messagesToSend.get(node).size() == this.messageBufferSize){
            messagesToSend = this.messagesToSend.get(node);
            this.messagesToSend.put(node, new ArrayList<Message>(this.messageBufferSize));
        }
        return messagesToSend;
    }

    public void emit(List<Event> events) throws MessageProcessingException {

        Map<Node, List<Message>> nodeMessageMap = new HashMap<Node, List<Message>>();
        // populate this for all nodes
        synchronized (this) {
            for (int i = 0; i < this.nodes.size(); i++) {
                nodeMessageMap.put(this.nodes.get(i), new ArrayList<Message>());
            }
        }

        Message message = null;
        Node nodeToSend = null;

        for (Event event : events) {
            message = new Message(this.destProcessor, this.srcProcessor, event);
            nodeToSend = getNode(event);
            if (nodeToSend != null) {
                nodeMessageMap.get(nodeToSend).add(message);
            }

        }

        for (Map.Entry<Node, List<Message>> entry : nodeMessageMap.entrySet()) {
            try {
                this.commManager.sendEvents(entry.getValue(), entry.getKey());
            } catch (MessageProcessingException e) {
                this.logger.log(Level.SEVERE, "Can not send the message to " + entry.getKey().getIpAddress() + " " + e.getMessage());
            }
        }
    }

    protected abstract Node getNode(Event event);


}
