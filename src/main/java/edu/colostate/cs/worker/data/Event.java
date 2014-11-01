package edu.colostate.cs.worker.data;

import edu.colostate.cs.worker.comm.exception.MessageProcessingException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/16/14
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Event {

    public String getKey();

    public void serialize(DataOutput dataOutput) throws MessageProcessingException;

    public void parse(DataInput dataInput) throws MessageProcessingException;

}
