package edu.colostate.cs.manager.topology;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/13/14
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class GraphDBO {

    private List<ProcessorDBO> processors;
    private List<AdapterDBO> adapters;

    public List<ProcessorDBO> getProcessors() {
        return processors;
    }

    public void setProcessors(List<ProcessorDBO> processors) {
        this.processors = processors;
    }

    public List<AdapterDBO> getAdapters() {
        return adapters;
    }

    public void setAdapters(List<AdapterDBO> adapters) {
        this.adapters = adapters;
    }
}
