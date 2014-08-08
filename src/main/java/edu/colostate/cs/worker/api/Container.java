package edu.colostate.cs.worker.api;

import edu.colostate.cs.worker.comm.exception.MessageProcessingException;
import edu.colostate.cs.worker.data.Event;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/16/14
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Container {

     public void emit(Event event) throws MessageProcessingException;

}
