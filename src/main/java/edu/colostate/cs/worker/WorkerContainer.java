package edu.colostate.cs.worker;

import edu.colostate.cs.exception.DeploymentException;
import edu.colostate.cs.worker.api.Adaptor;
import edu.colostate.cs.worker.api.Processor;
import edu.colostate.cs.worker.comm.server.MessageListener;
import edu.colostate.cs.worker.data.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    private Map<String, Class> eventTypClassMap;
    private Map<Integer, Long> connectionToSeqMap;
    private Map<Integer, Object> connectionToLockMap;

    public WorkerContainer() {
        this.processors = new ConcurrentHashMap<String, Processor>();
        this.adaptors = new ConcurrentHashMap<String, Adaptor>();
        this.eventTypClassMap = new ConcurrentHashMap<String, Class>();
        this.connectionToSeqMap = new HashMap<Integer, Long>();
        this.connectionToLockMap = new ConcurrentHashMap<Integer, Object>();
    }

    public void onMessage(Message message) {
        Processor processor = this.processors.get(message.getProcessingElement());
        processor.onEvent(message.getEvent());
    }

    public Object getLock(int connectionID) {
        if (!this.connectionToLockMap.containsKey(connectionID)) {
            synchronized (this.connectionToLockMap) {
                if (!this.connectionToLockMap.containsKey(connectionID)) {
                    this.connectionToLockMap.put(connectionID, new Object());
                }
            }
        }
        return this.connectionToLockMap.get(connectionID);
    }

    public void onMessages(List<Message> messages, int connectionID, long seqNo) {

        Object lock = getLock(connectionID);
        synchronized (lock) {
            sendMessages(messages, connectionID, seqNo, lock);
        }
    }

    private void sendMessages(List<Message> messages, int connectionID, long seqNo, Object lock) {
        if (!this.connectionToSeqMap.containsKey(connectionID)) {
            this.connectionToSeqMap.put(connectionID, new Long(0));
        }

        long currentSeq = this.connectionToSeqMap.get(connectionID);
        if (seqNo == currentSeq + 1) {
            // send messages to higher layer
            for (Message message : messages) {
                onMessage(message);
            }
            this.connectionToSeqMap.put(connectionID, seqNo);
            lock.notifyAll();
        } else {
            try {
                lock.wait();
            } catch (InterruptedException e) {
            }
            sendMessages(messages, connectionID, seqNo, lock);
        }
    }

    public void addEventType(String processor, String eventType) throws DeploymentException {
        try {
            Class eventClass = Class.forName(eventType);
            this.eventTypClassMap.put(processor, eventClass);
        } catch (ClassNotFoundException e) {
            throw new DeploymentException("Can not instantiate class " + eventType);
        }
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

    public Map<String, Class> getEventTypClassMap() {
        return eventTypClassMap;
    }
}
