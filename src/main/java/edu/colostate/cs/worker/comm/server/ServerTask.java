package edu.colostate.cs.worker.comm.server;

import edu.colostate.cs.worker.WorkerContainer;
import edu.colostate.cs.worker.comm.exception.MessageProcessingException;
import edu.colostate.cs.worker.data.Message;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private WorkerContainer workerContainer;

    public ServerTask(ServerConnection serverConnection, WorkerContainer workerContainer) {
        this.serverConnection = serverConnection;
        this.workerContainer = workerContainer;
    }

    public void run() {
        DataConnection dataConnection = null;
        DataInput dataInput = null;

        while (true) {
            dataConnection = this.serverConnection.getDataConnection();
            dataInput = dataConnection.getDataInput();
            try {
                int messageSize = dataInput.readInt();
                byte[] message = new byte[messageSize];
                dataInput.readFully(message);
                int connectionID = dataConnection.getConnectionID();
                long seqNo = dataConnection.getNextSeqNo();
                this.serverConnection.releaseDataInput(dataConnection);
                // process the messages
                processMessage(message, connectionID, seqNo);

            } catch (MessageProcessingException e) {
                this.logger.log(Level.SEVERE, "Can not parse the message");
            } catch (IOException e) {
                this.logger.log(Level.SEVERE, "Can not read the message");
            } catch (RuntimeException e) {
                this.logger.log(Level.SEVERE, "Can not read data from connection " + e.getMessage());
            }
        }
    }

    private void processMessage(byte[] byteMessage, int connectionID, long seqNo) throws MessageProcessingException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteMessage);
        DataInput dataInput = new DataInputStream(byteArrayInputStream);
        try {
            int numberOfMessages = dataInput.readInt();
            List<Message> messages = new ArrayList<Message>();
            for (int i = 0; i < numberOfMessages; i++) {
                Message message = new Message();
                message.parse(dataInput, this.workerContainer.getEventTypClassMap());
                messages.add(message);
            }
            this.workerContainer.onMessages(messages, connectionID, seqNo);
        } catch (IOException e) {
            throw new MessageProcessingException("Problem in parsing the message");
        }
    }

    private void processMessage(byte[] byteMessage) throws MessageProcessingException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteMessage);
        DataInput dataInput = new DataInputStream(byteArrayInputStream);
        try {
            int numberOfMessages = dataInput.readInt();
            for (int i = 0; i < numberOfMessages; i++) {
                Message message = new Message();
                message.parse(dataInput, this.workerContainer.getEventTypClassMap());
                this.workerContainer.onMessage(message);
            }
        } catch (IOException e) {
            throw new MessageProcessingException("Problem in parsing the message");
        }
    }
}
