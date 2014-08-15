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
    private int adminPort;
    private int msgPort;

    @Override
    public int hashCode() {
        return this.ip.hashCode() + this.adminPort + this.msgPort;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NodeDBO){
            NodeDBO nodeDBO = (NodeDBO) obj;
            return this.ip.equals(nodeDBO.ip)
                           && (this.adminPort == nodeDBO.getAdminPort())
                           && (this.msgPort == nodeDBO.msgPort);
        }
        return false;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getAdminPort() {
        return adminPort;
    }

    public void setAdminPort(int adminPort) {
        this.adminPort = adminPort;
    }

    public int getMsgPort() {
        return msgPort;
    }

    public void setMsgPort(int msgPort) {
        this.msgPort = msgPort;
    }
}
