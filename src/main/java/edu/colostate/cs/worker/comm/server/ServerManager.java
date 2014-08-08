package edu.colostate.cs.worker.comm.server;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/21/14
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerManager {

    private ServerIOReactor serverIOReactor;
    private MessageListener messageListener;
    private int port;

    public ServerManager(int port, MessageListener messageListener) {
        this.port = port;
        this.messageListener = messageListener;
    }

    public void start(){

        ServerStreamHandler serverStreamHandler = new ServerStreamHandler(this.messageListener);

        this.serverIOReactor = new ServerIOReactor(this.port, serverStreamHandler);
        Thread serverThread = new Thread(this.serverIOReactor);
        serverThread.start();

    }

}
