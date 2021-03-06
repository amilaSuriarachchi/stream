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
    private List<StreamDBO> streams = new ArrayList<StreamDBO>();
    private List<ParameterDBO> parameters = new ArrayList<ParameterDBO>();

    public ElementDBO() {
    }

    public ElementDBO(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public void addStream(StreamDBO streamDBO){
        this.streams.add(streamDBO);
    }

    public void addParameter(ParameterDBO parameterDBO){
        this.parameters.add(parameterDBO);
    }

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

    public List<StreamDBO> getStreams() {
        return streams;
    }

    public void setStreams(List<StreamDBO> streams) {
        this.streams = streams;
    }

    public List<ParameterDBO> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterDBO> parameters) {
        this.parameters = parameters;
    }
}
