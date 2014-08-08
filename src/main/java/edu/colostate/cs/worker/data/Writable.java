package edu.colostate.cs.worker.data;

import edu.colostate.cs.worker.comm.exception.MessageProcessingException;

import java.io.DataInput;
import java.io.DataOutput;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/21/14
 * Time: 11:18 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Writable {

    public void serialize(DataOutput dataOutput) throws MessageProcessingException;

    public void parse(DataInput dataInput) throws MessageProcessingException;
}
