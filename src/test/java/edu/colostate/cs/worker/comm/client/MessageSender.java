package edu.colostate.cs.worker.comm.client;

import edu.colostate.cs.worker.comm.Node;
import edu.colostate.cs.worker.comm.exception.MessageProcessingException;
import edu.colostate.cs.worker.data.Message;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/30/14
 * Time: 9:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageSender implements Runnable {

    private ClientManager clientManager;
    private Message message;
    private Node targetNode;

    public MessageSender(ClientManager clientManager, Message message, Node targetNode) {
        this.clientManager = clientManager;
        this.message = message;
        this.targetNode = targetNode;
    }

    public void run() {
        while (true) {
            try {
                this.clientManager.sendEvent(this.message, this.targetNode);
            } catch (MessageProcessingException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

    }
}
