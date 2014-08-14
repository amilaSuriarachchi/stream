package edu.colostate.cs.manager.topology;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/13/14
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeDBO {

    private String ip;
    private int port;

    @Override
    public int hashCode() {
        return super.hashCode();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NodeDBO){
            NodeDBO nodeDBO = (NodeDBO) obj;
            return this.ip.equals(nodeDBO.ip) && this.port == nodeDBO.getPort();
        }
        return false;
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
