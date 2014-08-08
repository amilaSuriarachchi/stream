package edu.colostate.cs.worker;

import edu.colostate.cs.worker.api.Adaptor;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 5/17/14
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdaptorInvoker implements Runnable{

    private Adaptor adaptor;

    public AdaptorInvoker(Adaptor adaptor) {
        this.adaptor = adaptor;
    }

    public void run() {
        this.adaptor.start();
    }
}
