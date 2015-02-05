package edu.colostate.cs.worker.comm.client;

import edu.colostate.cs.worker.comm.Node;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 2/4/15
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FailureCallback {

    public void nodeFailed(Node node);
}
