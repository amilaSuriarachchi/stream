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
 * Key stream distribute messages according to the key. It guarantee that
 * events with same key always goes to same machine
 */
public class KeyStream extends AbstractStream {

    // this map keeps a stream key and a node map.
    private Map<String, Node> keyMap;

    // simply we use a round robin algorithm to allocate the keys to the nodes.
    private int nextNodeToAssign;

    public KeyStream(String destProcessor, String srcProcessor, List<Node> nodes, CommManager commManager) {
        super(destProcessor, srcProcessor, nodes, commManager);
        this.keyMap = new HashMap<String, Node>();
        this.nextNodeToAssign = 0;

    }

    @Override
    protected Node getNode(Event event) {

        if (!this.keyMap.containsKey(event.getKey())) {
            synchronized (this.keyMap) {
                if (!this.keyMap.containsKey(event.getKey())) {
                    this.keyMap.put(event.getKey(), this.nodes.get(this.nextNodeToAssign));
                    this.nextNodeToAssign = (this.nextNodeToAssign + 1) % this.nodes.size();
                }
            }
        }
        return this.keyMap.get(event.getKey());
    }
}
