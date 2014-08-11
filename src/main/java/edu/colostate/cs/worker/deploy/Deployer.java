package edu.colostate.cs.worker.deploy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.colostate.cs.worker.ElementContainer;
import edu.colostate.cs.worker.WorkerContainer;
import edu.colostate.cs.worker.api.Adaptor;
import edu.colostate.cs.worker.api.Element;
import edu.colostate.cs.worker.api.Parameter;
import edu.colostate.cs.worker.api.Processor;
import edu.colostate.cs.worker.comm.CommManager;
import edu.colostate.cs.worker.comm.Node;
import edu.colostate.cs.worker.comm.exception.DeploymentException;
import edu.colostate.cs.worker.stream.KeyStream;
import edu.colostate.cs.worker.stream.Stream;
import edu.colostate.cs.worker.stream.StreamFactory;
import edu.colostate.cs.worker.util.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 5/15/14
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class Deployer {

    private WorkerContainer workerContainer;
    private CommManager commManager;
    private String folder;

    public Deployer(WorkerContainer workerContainer, CommManager commManager, String folder) {
        this.workerContainer = workerContainer;
        this.commManager = commManager;
        this.folder = folder;
    }

    public void deploy() throws DeploymentException {
        // for the moment lets assume there is one deployment file in the deployer folder at start up time.
        // latter this can be improved to have auto deployment feaatures.
        File file = new File(this.folder);

        File[] artefacts = file.listFiles();
        for (File artefact : artefacts) {
            try {
                deploy(new FileInputStream(artefact));

            } catch (FileNotFoundException e) {
                throw new DeploymentException("Invalid file ", e);
            }
        }


    }

    public void deploy(InputStream deployFile) throws DeploymentException {
        Gson gson = new GsonBuilder().create();
        WorkerDBO elements
                = gson.fromJson(new InputStreamReader(deployFile), WorkerDBO.class);
        // iterate through and add elements
        for (ElementDBO elementDBO : elements.getProcessors()) {
            this.workerContainer.registerProcessor(elementDBO.getName(), (Processor) getElement(elementDBO));
        }

        for (ElementDBO elementDBO : elements.getAdapters()) {
            this.workerContainer.registerAdapter(elementDBO.getName(), (Adaptor) getElement(elementDBO));
        }

    }

    private Element getElement(ElementDBO elementDBO) throws DeploymentException {
        try {
            Class pEClass = Class.forName(elementDBO.getClassName());
            Element element = (Element) pEClass.newInstance();
            StreamDBO streamDBO = elementDBO.getStream();
            Stream stream = null;
            if (streamDBO != null) {

                List<Node> nodes = new ArrayList<Node>();
                for (NodeDBO nodeDBO : streamDBO.getNodes()) {
                    nodes.add(new Node(nodeDBO.getPort(), nodeDBO.getIp()));
                }
                stream = StreamFactory.getStream(streamDBO.getType(),
                                                 streamDBO.getProcessor(),
                                                 nodes,
                                                 this.commManager);
            }
            ElementContainer elementContainer = new ElementContainer(stream);
            Map<String, String> parameters = new HashMap<String, String>();
            if (elementDBO.getParameters() != null) {
                for (ParameterDBO parameterDBO : elementDBO.getParameters()) {
                    parameters.put(parameterDBO.getName(), parameterDBO.getValue());
                }
            }
            element.initialise(elementContainer, parameters);
            return element;
        } catch (ClassNotFoundException e) {
            throw new DeploymentException("Class " + elementDBO.getClassName() + " can not be found ", e);
        } catch (InstantiationException e) {
            throw new DeploymentException("Class " + elementDBO.getClassName() + " can not instantiate ", e);
        } catch (IllegalAccessException e) {
            throw new DeploymentException("Class " + elementDBO.getClassName() + " can not access ", e);
        }
    }
}
