package edu.colostate.cs.worker.comm.server;

import edu.colostate.cs.worker.WorkerContainer;
import edu.colostate.cs.worker.config.Configurator;
import edu.colostate.cs.worker.data.Message;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/21/14
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerManager {

    private ServerIOReactor serverIOReactor;
    private WorkerContainer workerContainer;
    private int port;

    public ServerManager(int port, WorkerContainer workerContainer) {
        this.port = port;
        this.workerContainer = workerContainer;
    }

    public void start() {

        ServerConnection serverConnection = new ServerConnection();
        // start the Server Task pool
        for (int i = 0; i < Configurator.getInstance().getWorkerPoolSize(); i++) {
            ServerTask serverTask = new ServerTask(serverConnection, this.workerContainer);
            Thread thread = new Thread(serverTask);
            thread.start();
        }

        this.serverIOReactor = new ServerIOReactor(this.port, serverConnection);
        Thread serverThread = new Thread(this.serverIOReactor);
        serverThread.start();

    }

    /**
     * this method most probably invoked by the local transport send messages locally to other processes.
     * @param message
     */
    public void onEvent(Message message){
        this.workerContainer.onMessage(message);
    }

}
