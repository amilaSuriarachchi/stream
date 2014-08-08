package edu.colostate.cs.worker.api;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/7/14
 * Time: 4:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Parameter {
    private String name;
    private String value;

    public Parameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
