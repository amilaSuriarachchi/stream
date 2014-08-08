package edu.colostate.cs.worker.comm.server;

import edu.colostate.cs.worker.data.Message;
import edu.colostate.cs.worker.comm.exception.MessageProcessingException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/19/14
 * Time: 10:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class Task implements Runnable {

    private Logger logger = Logger.getLogger(Task.class.getName());

    private DataReader dataReader;
    private ServerStreamHandler serverStreamHandler;
    private MessageListener messageListener;

    public Task(DataReader dataReader,
                ServerStreamHandler serverStreamHandler,
                MessageListener messageListener) {
        this.dataReader = dataReader;
        this.serverStreamHandler = serverStreamHandler;
        this.messageListener = messageListener;
    }

    public void run() {
        try {
            Message message = new Message();
            message.parse(this.dataReader.getDataInput());
            this.serverStreamHandler.releaseStream(this.dataReader);
            this.messageListener.onMessage(message);
        } catch (MessageProcessingException e) {
            logger.log(Level.SEVERE, "Can not process the message ", e);
        }
    }
}
