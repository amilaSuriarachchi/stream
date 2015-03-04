package edu.colostate.cs.worker.stream;

import edu.colostate.cs.worker.comm.CommManager;
import edu.colostate.cs.worker.comm.Node;
import edu.colostate.cs.exception.DeploymentException;
import edu.colostate.cs.util.*;

import java.util.List;

/**
 * this class will create streams according to the type.
 */
public class StreamFactory {

    private static StreamFactory instance = new StreamFactory();

    private StreamFactory() {
    }

    public static StreamFactory getInstance() {
        return instance;
    }

    public static Stream getStream(String type,
                                   String destProcessor,
                                   String srcProcessor,
                                   List<Node> nodes,
                                   CommManager commManager) throws DeploymentException {

        Stream stream = null;
        if (type.equals(Constants.STREAM_TYPE_KEY)) {
            stream = new KeyStream(destProcessor, srcProcessor, nodes, commManager);
        } else if (type.equals(Constants.STREAM_TYPE_RANDOM)) {
            stream = new RandomStream(destProcessor, srcProcessor, nodes, commManager);
        } else if (type.equals(Constants.HASH_TYPE_RANDOM)){
            stream = new HashStream(destProcessor, srcProcessor, nodes, commManager);
        } else {
            throw new DeploymentException("Unknown stream type");
        }
        return stream;
    }
}
