package edu.colostate.cs.worker.deploy;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 5/16/14
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeDBO {

    private String ip;
    private int port;

    public NodeDBO() {
    }

    public NodeDBO(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
