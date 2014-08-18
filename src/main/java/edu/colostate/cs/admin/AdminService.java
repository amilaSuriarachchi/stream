package edu.colostate.cs.admin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.colostate.cs.admin.message.ACKMessage;
import edu.colostate.cs.admin.message.DeployMessage;
import edu.colostate.cs.admin.message.Message;
import edu.colostate.cs.admin.message.StartAdaptersMessage;
import edu.colostate.cs.exception.DeploymentException;
import edu.colostate.cs.util.Constants;
import edu.colostate.cs.worker.ElementContainer;
import edu.colostate.cs.worker.WorkerContainer;
import edu.colostate.cs.worker.api.Adaptor;
import edu.colostate.cs.worker.api.Element;
import edu.colostate.cs.worker.api.Processor;
import edu.colostate.cs.worker.comm.CommManager;
import edu.colostate.cs.worker.comm.Node;
import edu.colostate.cs.worker.deploy.*;
import edu.colostate.cs.worker.stream.Stream;
import edu.colostate.cs.worker.stream.StreamFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/18/14
 * Time: 9:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdminService implements MessageCallback {

    private WorkerContainer workerContainer;
    private CommManager commManager;

    public AdminService(WorkerContainer workerContainer, CommManager commManager) {
        this.workerContainer = workerContainer;
        this.commManager = commManager;
    }

    public Message messageReceived(Message message) {
        Message response = null;
        switch (message.getMessageType()){
            case Constants.DEPLOY_MESSAGE_TYPE : {
                response = processDeployMessage((DeployMessage) message);
                break;
            }
            case Constants.START_ADAPTERS_MESSAGE_TYPE: {
                response = processStartAdapters((StartAdaptersMessage) message);
                break;
            }
        }

        return response;

    }

    private Message processStartAdapters(StartAdaptersMessage startAdaptersMessage){
        this.workerContainer.startAdapters();
        return new ACKMessage("Successfully started adapters");
    }

    private Message processDeployMessage(DeployMessage deployMessage){
        Gson gson = new GsonBuilder().create();
        WorkerDBO workerDBO = gson.fromJson(deployMessage.getDeployString(), WorkerDBO.class);
        Message response = null;
        try {
            deploy(workerDBO);
            response = new ACKMessage("Successfully deployed");
        } catch (DeploymentException e) {
            response = new ACKMessage(e.getMessage());
        }

        return response;
    }

    public void deploy(WorkerDBO elements) throws DeploymentException {
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
            List<Stream> streams = new ArrayList<Stream>();
            Stream stream = null;
            for (StreamDBO streamDBO : elementDBO.getStreams()) {
                List<Node> nodes = new ArrayList<Node>();
                for (NodeDBO nodeDBO : streamDBO.getNodes()) {
                    nodes.add(new Node(nodeDBO.getPort(), nodeDBO.getIp()));
                }
                stream = StreamFactory.getStream(streamDBO.getType(),
                        streamDBO.getProcessor(),
                        nodes,
                        this.commManager);
                streams.add(stream);
            }

            ElementContainer elementContainer = new ElementContainer(streams);
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
