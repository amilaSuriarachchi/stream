package edu.colostate.cs.worker.comm.relay;

import edu.colostate.cs.worker.comm.Node;
import edu.colostate.cs.worker.comm.client.ClientManager;
import edu.colostate.cs.worker.comm.exception.MessageProcessingException;
import edu.colostate.cs.worker.comm.server.MessageListener;
import edu.colostate.cs.worker.data.Message;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 4/1/14
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class RelayMessageListener implements MessageListener {

    private ClientManager clientManager;
    private Node targetNode;

    public RelayMessageListener(ClientManager clientManager, Node targetNode) {
        this.clientManager = clientManager;
        this.targetNode = targetNode;
    }

    public void onMessage(Message message) {
//        try {
//            this.clientManager.sendEvent(message, targetNode);
//        } catch (MessageProcessingException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
    }
}
