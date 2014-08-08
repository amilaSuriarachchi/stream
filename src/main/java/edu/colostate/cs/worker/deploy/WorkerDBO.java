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
}
