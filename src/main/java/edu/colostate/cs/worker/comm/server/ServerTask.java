package edu.colostate.cs.worker.comm.server;

import edu.colostate.cs.worker.comm.exception.MessageProcessingException;
import edu.colostate.cs.worker.data.Message;

import java.io.DataInput;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/23/14
 * Time: 7:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerTask implements Runnable {

    private Logger logger = Logger.getLogger(ServerTask.class.getName());

    private ServerConnection serverConnection;
    private MessageListener messageListener;

    public ServerTask(ServerConnection serverConnection, MessageListener messageListener) {
        this.serverConnection = serverConnection;
        this.messageListener = messageListener;
    }

    public void run() {
        DataInput dataInput = null;
        Message message = new Message();
        while (true){
            dataInput = this.serverConnection.getDataInput();
            try {
                message.parse(dataInput);
                this.serverConnection.releaseDataInput(dataInput);
                this.messageListener.onMessage(message);
            } catch (MessageProcessingException e) {
                this.logger.log(Level.SEVERE, "Can not parse the message");
            }
        }
    }
}
