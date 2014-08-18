package edu.colostate.cs.admin.message;

import edu.colostate.cs.util.Constants;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/18/14
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class DeployMessage implements Message {

    private String deployString;

    public DeployMessage() {
    }

    public DeployMessage(String deployString) {
        this.deployString = deployString;
    }

    public int getMessageType() {
        return Constants.DEPLOY_MESSAGE_TYPE;
    }

    public void serialize(DataOutput dataOutput) throws MessageProcessingException {
        try {
            dataOutput.writeInt(Constants.DEPLOY_MESSAGE_TYPE);
            dataOutput.writeUTF(this.deployString);
        } catch (IOException e) {
            throw new MessageProcessingException("Can not write the message ", e);
        }
    }

    public void parse(DataInput dataInput) throws MessageProcessingException {
        try {
            this.deployString = dataInput.readUTF();
        } catch (IOException e) {
            throw new MessageProcessingException("Can not read the message ", e);
        }
    }

    public String getDeployString() {
        return deployString;
    }

    public void setDeployString(String deployString) {
        this.deployString = deployString;
    }
}
