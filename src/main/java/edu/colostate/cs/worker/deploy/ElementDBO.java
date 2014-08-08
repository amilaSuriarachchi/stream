package edu.colostate.cs.worker.deploy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 5/15/14
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class ElementDBO {

    private String name;
    private String className;
    private StreamDBO stream;
    private List<ParameterDBO> parameters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public StreamDBO getStream() {
        return stream;
    }

    public void setStream(StreamDBO stream) {
        this.stream = stream;
    }

    public List<ParameterDBO> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterDBO> parameters) {
        this.parameters = parameters;
    }
}
