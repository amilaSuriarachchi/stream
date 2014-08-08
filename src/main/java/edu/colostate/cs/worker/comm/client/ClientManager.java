package edu.colostate.cs.worker.comm.client;

import edu.colostate.cs.worker.comm.Node;
import edu.colostate.cs.worker.comm.exception.MessageProcessingException;
import edu.colostate.cs.worker.data.Message;

import java.io.DataOutput;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/21/14
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientManager {

    private Map<Node, ClientConnection> nodeToConnectionMap;
    private ClientIOReactor clientIOReactor;

    public ClientManager() {
        this.nodeToConnectionMap = new ConcurrentHashMap<Node, ClientConnection>();
    }

    public void start(){
        //start the client io reactor.
        this.clientIOReactor = new ClientIOReactor();
        Thread clientThread = new Thread(this.clientIOReactor);
        clientThread.start();
    }

    public void sendEvent(Message message, Node targetNode) throws MessageProcessingException {

        if (!this.nodeToConnectionMap.containsKey(targetNode)) {
            synchronized (this.nodeToConnectionMap) {
                if (!this.nodeToConnectionMap.containsKey(targetNode)) {
                    ClientConnection clientConnection = new ClientConnection(targetNode);
                    this.clientIOReactor.add(clientConnection);
                    this.nodeToConnectionMap.put(targetNode, clientConnection);
                }
            }
        }

        ClientConnection clientConnection = this.nodeToConnectionMap.get(targetNode);
        DataOutput dataOutput = clientConnection.getDataOutput();
        message.serialize(dataOutput);
        clientConnection.releaseDataOutput(dataOutput);
    }
}
