package edu.colostate.cs.worker.api;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 5/17/14
 * Time: 9:53 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Element {

    public void initialise(Container container, Map<String,String> parameters);

}
