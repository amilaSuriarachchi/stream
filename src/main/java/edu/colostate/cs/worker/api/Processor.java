package edu.colostate.cs.worker.api;

import edu.colostate.cs.worker.data.Event;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/16/14
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Processor extends Element {

    public void onEvent(Event event);

}
