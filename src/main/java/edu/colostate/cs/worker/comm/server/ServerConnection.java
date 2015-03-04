package edu.colostate.cs.worker.comm.server;

import java.io.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/23/14
 * Time: 7:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerConnection {

    private Queue<DataConnection> freeDataConnections;
    private int connectionID = 0;

    public ServerConnection() {
        this.freeDataConnections = new LinkedList<DataConnection>();
    }

    public synchronized DataConnection getDataConnection(){
        DataConnection returnDataConnection = null;
        while ((returnDataConnection = this.freeDataConnections.poll()) == null){
            try {
                this.wait();
            } catch (InterruptedException e) {
                //TODO: handle this properly
            }
        }
        return returnDataConnection;
    }

    public synchronized void releaseDataInput(DataConnection dataConnection){
        this.freeDataConnections.add(dataConnection);
        this.notifyAll();
    }

    public synchronized void registerSelectionKey(SelectionKey selectionKey){
        DataReader dataReader = new DataReader((SocketChannel)selectionKey.channel());
        DataInput dataInput = new DataInputStream(dataReader);
        //remove the current attachment
        selectionKey.attach(dataReader);
        this.connectionID++;
        DataConnection dataConnection = new DataConnection(dataInput, this.connectionID);
        this.freeDataConnections.add(dataConnection);
        this.notifyAll();
    }
}
