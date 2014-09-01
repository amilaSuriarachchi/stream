package edu.colostate.cs.worker.deploy;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 9/1/14
 * Time: 9:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventTypeDBO {

    private String source;
    private String type;

    public EventTypeDBO() {
    }

    public EventTypeDBO(String source, String type) {
        this.source = source;
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
