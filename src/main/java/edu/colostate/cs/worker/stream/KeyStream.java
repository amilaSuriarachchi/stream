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
 * Date: 3/16/14
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class KeyStream implements Stream {

    // this is the processing element which needs to send the event to next worker.
    private String processor;

    // this is the physical worker this stream has to send the message.
    private List<Node> nodes;

    // communication manager for this worker. All underline communications should happen through this.
    private CommManager commManager;

    // this map keeps a stream key and a node map.
    private Map<String, Node> keyMap;

    // simply we use a round robin algorithm to allocate the keys to the nodes.
    private int nextNodeToAssign;

    public KeyStream() {
        this.keyMap = new HashMap<String, Node>();
        this.nextNodeToAssign = 0;
    }

    public KeyStream(String processor, List<Node> nodes, CommManager commManager) {
        this();
        this.processor = processor;
        this.nodes = nodes;
        this.commManager = commManager;
    }

    public void emit(Event event) throws MessageProcessingException {
        Message message = new Message(this.processor, event);
        if (!this.keyMap.containsKey(event.getKey())) {
            synchronized (this.keyMap) {
                if (!this.keyMap.containsKey(event.getKey())) {
                    this.keyMap.put(event.getKey(), this.nodes.get(this.nextNodeToAssign));
                    this.nextNodeToAssign = (this.nextNodeToAssign + 1) % this.nodes.size();
                }
            }
        }
        this.commManager.sendEvent(message, this.keyMap.get(event.getKey()));
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

            if (!this.keyMap.containsKey(event.getKey())) {
                synchronized (this.keyMap) {
                    if (!this.keyMap.containsKey(event.getKey())) {
                        this.keyMap.put(event.getKey(), this.nodes.get(this.nextNodeToAssign));
                        this.nextNodeToAssign = (this.nextNodeToAssign + 1) % this.nodes.size();
                    }
                }
            }
            nodeToSend = this.keyMap.get(event.getKey());
            nodeMessageMap.get(nodeToSend).add(message);
        }

        for (Node node : this.nodes){
            this.commManager.sendEvents(nodeMessageMap.get(node), node);
        }
    }

}
