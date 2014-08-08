package edu.colostate.cs.worker.comm;

import edu.colostate.cs.worker.comm.exception.MessageProcessingException;
import edu.colostate.cs.worker.data.Event;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/7/14
 * Time: 12:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringMapEvent extends Event {

    private Map<String,String> data;

    public StringMapEvent() {
        this.data = new HashMap<String, String>();
    }

    public void setValue(String name, String value){
        this.data.put(name, value);
    }

    public String getValue(String name){
        return this.data.get(name);
    }

    @Override
    public String getKey() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void serialize(DataOutput dataOutput) throws MessageProcessingException {
        try {
            dataOutput.writeInt(this.data.size());
            for (String key : this.data.keySet()){
                dataOutput.writeUTF(key);
                dataOutput.writeUTF(this.data.get(key));
            }
        } catch (IOException e) {
            throw new MessageProcessingException("Can not write to the out stream ", e);
        }
    }

    public void parse(DataInput dataInput) throws MessageProcessingException {
        try {
            int size = dataInput.readInt();
            for (int i = 0 ; i < size; i++){
                String key = dataInput.readUTF();
                String value = dataInput.readUTF();
                this.data.put(key, value);
            }
        } catch (IOException e) {
            throw new MessageProcessingException("Can not read from the stream ", e);
        }
    }

    @Override
    public String toString() {
        return "Event{" +
                "data=" + data +
                '}';
    }
}
