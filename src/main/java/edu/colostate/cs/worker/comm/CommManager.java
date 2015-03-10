package edu.colostate.cs.worker.comm;

import edu.colostate.cs.worker.WorkerContainer;
import edu.colostate.cs.worker.comm.client.ClientManager;
import edu.colostate.cs.worker.comm.client.FailureCallback;
import edu.colostate.cs.worker.comm.exception.MessageProcessingException;
import edu.colostate.cs.worker.comm.server.MessageListener;
import edu.colostate.cs.worker.comm.server.ServerManager;
import edu.colostate.cs.worker.data.Message;

import java.util.List;

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

    public CommManager(int port, WorkerContainer workerContainer) {
        this.clientManager = new ClientManager();
        this.serverManager = new ServerManager(port, workerContainer);
    }

    public void start() {
        this.serverManager.start();
        this.clientManager.start();

    }

    public void sendEvents(List<Message> messages, Node targetNode) throws MessageProcessingException {
        this.clientManager.sendEvents(messages, targetNode);
    }

    public void addClientConnections(List<Node> targetNodes) {
        this.clientManager.addClientConnections(targetNodes);
    }

    public void registerFailureCallback(FailureCallback failureCallback) {
        this.clientManager.registerFailureCallback(failureCallback);
    }

    public void sendEvent(Message message){
        this.serverManager.onEvent(message);
    }
}
