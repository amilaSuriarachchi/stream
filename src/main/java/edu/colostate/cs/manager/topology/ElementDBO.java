package edu.colostate.cs.manager.topology;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/13/14
 * Time: 1:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class ElementDBO {

    private String name;
    private String className;
    private String cluster;
    private int instances;
    private List<ParameterDBO> parameters = new ArrayList<ParameterDBO>();

    public ElementDBO() {
    }

    public ElementDBO(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ElementDBO){
            ElementDBO newElement = (ElementDBO) obj;
            return this.name.equals(newElement.name);
        }
        return false;
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

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public int getInstances() {
        return instances;
    }

    public void setInstances(int instances) {
        this.instances = instances;
    }

    public List<ParameterDBO> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterDBO> parameters) {
        this.parameters = parameters;
    }
}
