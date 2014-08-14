package edu.colostate.cs.manager.topology;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/13/14
 * Time: 1:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcessorDBO extends ElementDBO {

    private List<ReceiverDBO> receivers;

    public List<ReceiverDBO> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<ReceiverDBO> receivers) {
        this.receivers = receivers;
    }
}
