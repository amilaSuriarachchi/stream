package edu.colostate.cs.worker.deploy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 5/16/14
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkerDBO {

    private List<ElementDBO> processors = new ArrayList<ElementDBO>();
    private List<ElementDBO> adapters = new ArrayList<ElementDBO>();
    private List<EventTypeDBO> eventTypes = new ArrayList<EventTypeDBO>();

    public void addProcessor(ElementDBO elementDBO){
        this.processors.add(elementDBO);
    }

    public void addAdapter(ElementDBO elementDBO){
        this.adapters.add(elementDBO);
    }

    public void addEventType(EventTypeDBO eventTypeDBO){
        this.eventTypes.add(eventTypeDBO);
    }

    public List<ElementDBO> getProcessors() {
        return processors;
    }

    public void setProcessors(List<ElementDBO> processors) {
        this.processors = processors;
    }

    public List<ElementDBO> getAdapters() {
        return adapters;
    }

    public void setAdapters(List<ElementDBO> adapters) {
        this.adapters = adapters;
    }

    public List<EventTypeDBO> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<EventTypeDBO> eventTypes) {
        this.eventTypes = eventTypes;
    }
}
