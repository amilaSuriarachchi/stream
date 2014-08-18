package edu.colostate.cs.admin.message;

import edu.colostate.cs.util.Constants;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/18/14
 * Time: 9:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class MessageFactory {

    public static Message getMessage(InputStream inputStream) throws MessageProcessingException {
        DataInput dataInput = new DataInputStream(inputStream);
        Message message = null;
        try {
            int messageType = dataInput.readInt();
            switch (messageType){
                case Constants.DEPLOY_MESSAGE_TYPE : {
                    message = new DeployMessage();
                    break;
                }
                case Constants.ACK_MESSAGE_TYPE : {
                    message = new ACKMessage();
                    break;
                }
                case Constants.START_ADAPTERS_MESSAGE_TYPE : {
                    message = new StartAdaptersMessage();
                    break;
                }
            }

            message.parse(dataInput);
            return message;
        } catch (IOException e) {
            throw new MessageProcessingException("Can not read the message ", e);
        }
    }
}
