package edu.colostate.cs.worker.config;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/12/14
 * Time: 4:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigDBO {

    private int ioThreads;
    private int workerPoolSize;
    private int tcpConnections;
    private int byteBufferSize;

    public int getIoThreads() {
        return ioThreads;
    }

    public void setIoThreads(int ioThreads) {
        this.ioThreads = ioThreads;
    }

    public int getWorkerPoolSize() {
        return workerPoolSize;
    }

    public void setWorkerPoolSize(int workerPoolSize) {
        this.workerPoolSize = workerPoolSize;
    }

    public int getTcpConnections() {
        return tcpConnections;
    }

    public void setTcpConnections(int tcpConnections) {
        this.tcpConnections = tcpConnections;
    }

    public int getByteBufferSize() {
        return byteBufferSize;
    }

    public void setByteBufferSize(int byteBufferSize) {
        this.byteBufferSize = byteBufferSize;
    }

}
