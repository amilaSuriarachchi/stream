package edu.colostate.cs.worker;

import edu.colostate.cs.worker.api.Container;
import edu.colostate.cs.worker.comm.exception.MessageProcessingException;
import edu.colostate.cs.worker.data.Event;
import edu.colostate.cs.worker.stream.Stream;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/16/14
 * Time: 2:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class ElementContainer implements Container {

    private List<Stream> streams;

    public ElementContainer(List<Stream> streams) {
        this.streams = streams;
    }

    public void emit(Event event) throws MessageProcessingException {
        for (Stream stream : this.streams){
            stream.emit(event);
        }
    }

    public void emit(List<Event> events) throws MessageProcessingException {
        for (Stream stream : this.streams){
            stream.emit(events);
        }
    }
}
