package edu.colostate.cs.worker.comm.server;

import edu.colostate.cs.worker.comm.client.DataWritter;

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

    private Queue<DataInput> freeDataInputs;

    public ServerConnection() {
        this.freeDataInputs = new LinkedList<DataInput>();
    }

    public synchronized DataInput getDataInput(){
        DataInput returnDataInput = null;
        while ((returnDataInput = this.freeDataInputs.poll()) == null){
            try {
                this.wait();
            } catch (InterruptedException e) {
                //TODO: handle this properly
            }
        }
        return returnDataInput;
    }

    public synchronized void releaseDataInput(DataInput dataInput){
        this.freeDataInputs.add(dataInput);
        this.notifyAll();
    }

    public synchronized void registerSelectionKey(SelectionKey selectionKey){
        DataReader dataReader = new DataReader((SocketChannel)selectionKey.channel());
        DataInput dataInput = new DataInputStream(dataReader);
        //remove the current attachment
        selectionKey.attach(dataReader);
        this.freeDataInputs.add(dataInput);
        this.notifyAll();
    }
}
