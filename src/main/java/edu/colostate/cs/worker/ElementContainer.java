package edu.colostate.cs.worker;

import edu.colostate.cs.worker.api.Container;
import edu.colostate.cs.worker.comm.exception.MessageProcessingException;
import edu.colostate.cs.worker.data.Event;
import edu.colostate.cs.worker.stream.Stream;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/16/14
 * Time: 2:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class ElementContainer implements Container {

    private Stream stream;

    public ElementContainer(Stream stream) {
        this.stream = stream;
    }

    public void emit(Event event) throws MessageProcessingException {
        this.stream.emit(event);
    }

    public void emit(List<Event> events) throws MessageProcessingException {
        this.stream.emit(events);
    }
}
