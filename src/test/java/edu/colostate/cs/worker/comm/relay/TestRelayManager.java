package edu.colostate.cs.worker.comm.relay;

import edu.colostate.cs.worker.comm.Node;
import edu.colostate.cs.worker.comm.client.ClientManager;
import edu.colostate.cs.worker.comm.server.ServerManager;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 4/1/14
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestRelayManager {

    public static void main(String[] args) {

        ClientManager clientManager = new ClientManager();
        clientManager.start();

        Node targetNode = new Node(Integer.parseInt(args[1]), args[2]);
        RelayMessageListener messageListener = new RelayMessageListener(clientManager, targetNode);

//        ServerManager serverManager = new ServerManager(Integer.parseInt(args[0]), messageListener);
//        serverManager.start();

    }
}
