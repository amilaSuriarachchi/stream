package edu.colostate.cs.worker.stream;

import edu.colostate.cs.worker.comm.exception.MessageProcessingException;
import edu.colostate.cs.worker.data.Event;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/16/14
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Stream {

    public void emit(Event event) throws MessageProcessingException;

    public void emit(List<Event> events) throws MessageProcessingException;

}
