package edu.colostate.cs.worker;

import edu.colostate.cs.worker.api.Adaptor;
import edu.colostate.cs.worker.api.Processor;
import edu.colostate.cs.worker.comm.server.MessageListener;
import edu.colostate.cs.worker.data.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 5/15/14
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkerContainer implements MessageListener {

    private Map<String, Processor> processors;
    private Map<String, Adaptor> adaptors;

    public WorkerContainer() {
        this.processors = new HashMap<String, Processor>();
        this.adaptors = new HashMap<String, Adaptor>();
    }

    public void onMessage(Message message) {
        Processor processor = this.processors.get(message.getProcessingElement());
        processor.onEvent(message.getEvent());
    }

    public void registerProcessor(String name, Processor processor) {
        this.processors.put(name, processor);
    }

    public void registerAdapter(String name, Adaptor adaptor) {
        this.adaptors.put(name, adaptor);
    }

    public void startAdapters() {
        for (Adaptor adaptor : this.adaptors.values()) {
            Thread thread = new Thread(new AdaptorInvoker(adaptor));
            thread.start();
        }
    }


}
