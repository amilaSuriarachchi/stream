package edu.colostate.cs.worker.comm.server;

import edu.colostate.cs.worker.comm.exception.MessageProcessingException;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/19/14
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerStreamHandler {

    private ExecutorService executor;
    private MessageListener messageListener;

    public ServerStreamHandler(MessageListener messageListener) {
        this.executor = Executors.newFixedThreadPool(500);
        this.messageListener = messageListener;
    }

    public void requestReceived(DataReader dataReader) {
        Task task = new Task(dataReader, this, messageListener);
        this.executor.submit(task);
    }

    public void releaseStream(DataReader dataReader) throws MessageProcessingException {
        try {
            if (dataReader.available() > 0) {
                //this means we can still create messages from this stream
                requestReceived(dataReader);
            } else {
                // set the input stream close for current message reading. then at the readReady method
                // it can trigger event.
                dataReader.close();
            }
        } catch (IOException e) {
            throw new MessageProcessingException("Can not check the stream availability", e);
        }
    }
}
