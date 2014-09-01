package edu.colostate.cs.worker.data;

import edu.colostate.cs.worker.comm.exception.MessageProcessingException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/16/14
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Message {

    private String processingElement;

    private String srcElement;

    private Event event;

    public Message() {
    }

    public Message(String processingElement, String srcElement, Event event) {
        this.processingElement = processingElement;
        this.srcElement = srcElement;
        this.event = event;
    }

    public void serialize(DataOutput dataOutput) throws MessageProcessingException {
        try {
            dataOutput.writeUTF(this.processingElement);
            dataOutput.writeUTF(this.srcElement);
            this.event.serialize(dataOutput);
        } catch (IOException e) {
            throw new MessageProcessingException("Can not write to data out ", e);
        }
    }

    public void parse(DataInput dataInput, Map<String,Class> eventTypeMap) throws MessageProcessingException {
        try {
            this.processingElement = dataInput.readUTF();
            this.srcElement = dataInput.readUTF();
            Class eventClass = eventTypeMap.get(this.srcElement);
            this.event = (Event) eventClass.newInstance();
            this.event.parse(dataInput);
        } catch (IOException e) {
            throw new MessageProcessingException("Can not read the data from in ", e);
        } catch (InstantiationException e) {
            throw new MessageProcessingException("Can not instantiate class ", e);
        } catch (IllegalAccessException e) {
            throw new MessageProcessingException("Can not instantiate class ", e);
        }
    }

    @Override
    public String toString() {
        return "Message{" +
                "processingElement='" + processingElement + '\'' +
                ", event=" + event +
                '}';
    }

    public String getProcessingElement() {
        return processingElement;
    }

    public void setProcessingElement(String processingElement) {
        this.processingElement = processingElement;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
