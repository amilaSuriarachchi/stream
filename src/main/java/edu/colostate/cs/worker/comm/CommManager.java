package edu.colostate.cs.worker.comm;

import edu.colostate.cs.worker.comm.client.ClientManager;
import edu.colostate.cs.worker.comm.exception.MessageProcessingException;
import edu.colostate.cs.worker.comm.server.MessageListener;
import edu.colostate.cs.worker.comm.server.ServerManager;
import edu.colostate.cs.worker.data.Message;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/16/14
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommManager {

    private ClientManager clientManager;
    private ServerManager serverManager;

    public CommManager(int port, MessageListener messageListener) {
        this.clientManager = new ClientManager();
        this.serverManager = new ServerManager(port, messageListener);
    }

    public void start() {
        this.serverManager.start();
        this.clientManager.start();

    }

    public void sendEvent(Message message, Node targetNode) throws MessageProcessingException {

        this.clientManager.sendEvent(message, targetNode);

    }
}
