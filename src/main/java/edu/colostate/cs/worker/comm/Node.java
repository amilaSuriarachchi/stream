package edu.colostate.cs.worker.comm;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/16/14
 * Time: 3:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class Node {

    private int port;
    private String ipAddress;

    public Node(int port, String ipAddress) {
        this.port = port;
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
