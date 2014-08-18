package edu.colostate.cs.admin.message;

import edu.colostate.cs.util.Constants;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/18/14
 * Time: 1:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class StartAdaptersMessage implements Message {

    public int getMessageType() {
        return Constants.START_ADAPTERS_MESSAGE_TYPE;
    }

    public void serialize(DataOutput dataOutput) throws MessageProcessingException {
        try {
            dataOutput.writeInt(Constants.START_ADAPTERS_MESSAGE_TYPE);
        } catch (IOException e) {
            throw new MessageProcessingException("Can not write to writer ", e);
        }
    }

    public void parse(DataInput dataInput) throws MessageProcessingException {

    }
}
