package edu.colostate.cs.worker.comm.server;

import java.io.DataInput;

/**
 * this class keeps the sequence number to keep a track of messages received
 * by same connection
 */
public class DataConnection {

    private long seqNo = 0;
    private int connectionID = 0;

    private DataInput dataInput;

    public DataConnection(DataInput dataInput, int connectionID) {
        this.dataInput = dataInput;
        this.connectionID = connectionID;
    }

    public long getNextSeqNo(){
        this.seqNo++;
        return this.seqNo;
    }

    public DataInput getDataInput() {
        return dataInput;
    }

    public int getConnectionID() {
        return connectionID;
    }
}
