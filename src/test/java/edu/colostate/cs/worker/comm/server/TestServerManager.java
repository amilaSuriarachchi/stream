package edu.colostate.cs.worker.comm.server;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/21/14
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestServerManager {

    public static void main(String[] args) {

        ServerManager serverManager = new ServerManager(Integer.parseInt(args[0]), new ServerMessageListener());
        serverManager.start();
    }
}
