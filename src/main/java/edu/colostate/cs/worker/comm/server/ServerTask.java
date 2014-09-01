package edu.colostate.cs.worker.comm.server;

import edu.colostate.cs.worker.WorkerContainer;
import edu.colostate.cs.worker.comm.exception.MessageProcessingException;
import edu.colostate.cs.worker.config.Configurator;
import edu.colostate.cs.worker.data.Message;

import java.io.DataInput;
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
        DataInput dataInput = null;
        List<Message> messages = new ArrayList<Message>();
        for (int i = 0; i < Configurator.getInstance().getTaskBufferMessages(); i++) {
            messages.add(new Message());
        }

        while (true) {
            dataInput = this.serverConnection.getDataInput();
            try {
                for (Message message : messages){
                    message.parse(dataInput, this.workerContainer.getEventTypClassMap());
                }
                this.serverConnection.releaseDataInput(dataInput);
                for (Message message : messages){
                    this.workerContainer.onMessage(message);
                }
            } catch (MessageProcessingException e) {
                this.logger.log(Level.SEVERE, "Can not parse the message");
            }
        }
    }
}
