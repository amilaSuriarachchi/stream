package edu.colostate.cs.manager.topology;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.colostate.cs.exception.DeploymentException;
import edu.colostate.cs.util.*;
import edu.colostate.cs.worker.deploy.StreamDBO;
import edu.colostate.cs.worker.deploy.WorkerDBO;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/13/14
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Builder {

    private String homeFolder;

    public Builder(String homeFolder) {
        this.homeFolder = homeFolder;
    }

    public Map<NodeDBO, WorkerDBO> build() throws DeploymentException {
        Map<NodeDBO, WorkerDBO> workerMap = new HashMap<NodeDBO, WorkerDBO>();
        // first read the clusters
        TopologyDBO topologyDBO = buildTopology();
        //populate the worker map with nodes
        for (ClusterDBO clusterDBO : topologyDBO.getClusters()) {
            for (NodeDBO nodeDBO : clusterDBO.getNodes()) {
                workerMap.put(nodeDBO, new WorkerDBO());
            }
        }

        List<GraphDBO> graphs = getGraphs();

        Map<ElementDBO, List<NodeDBO>> elementToNodeMap = new HashMap<ElementDBO, List<NodeDBO>>();
        Map<ElementDBO, List<edu.colostate.cs.worker.deploy.ElementDBO>>
                elementToElementMap = new HashMap<ElementDBO, List<edu.colostate.cs.worker.deploy.ElementDBO>>();

        for (GraphDBO graphDBO : graphs) {
            // add all processors and adapters to available nodes
            for (ProcessorDBO processorDBO : graphDBO.getProcessors()) {
                addElementDBO(processorDBO, topologyDBO, workerMap, elementToNodeMap, elementToElementMap);
            }
            for (AdapterDBO adapterDBO : graphDBO.getAdapters()) {
                addElementDBO(adapterDBO, topologyDBO, workerMap, elementToNodeMap, elementToElementMap);
            }
        }

        //go through the processor list and set the receivers
        for (GraphDBO graphDBO : graphs) {
            // add all processors and adapters to available nodes
            for (ProcessorDBO processorDBO : graphDBO.getProcessors()) {
                 for (ReceiverDBO receiverDBO : processorDBO.getReceivers()){
                     setReceiverNodes(elementToNodeMap.get(processorDBO),
                             elementToElementMap.get(new ElementDBO(receiverDBO.getName())),
                             receiverDBO.getType(),
                             processorDBO.getName());
                 }
            }
        }

        return workerMap;
    }

    private void setReceiverNodes(List<NodeDBO> deployedNodes,
                                  List<edu.colostate.cs.worker.deploy.ElementDBO> receiverNodes,
                                  String type,
                                  String processorName){

        for (edu.colostate.cs.worker.deploy.ElementDBO elementDBO : receiverNodes){
            StreamDBO streamDBO = new StreamDBO();
            streamDBO.setType(type);
            streamDBO.setProcessor(processorName);
            for (NodeDBO nodeDBO : deployedNodes){
                streamDBO.addNode(nodeDBO.getIp(), nodeDBO.getPort());
            }
            elementDBO.addStream(streamDBO);
        }
    }

    private void addElementDBO(ElementDBO elementDBO,
                               TopologyDBO topologyDBO,
                               Map<NodeDBO, WorkerDBO> workerMap,
                               Map<ElementDBO, List<NodeDBO>> elementToNodeMap,
                               Map<ElementDBO, List<edu.colostate.cs.worker.deploy.ElementDBO>> elementToElementMap) throws DeploymentException {
        ClusterDBO clusterDBO = topologyDBO.getCluster(elementDBO.getCluster());
        if (clusterDBO == null) {
            throw new DeploymentException("Can not deploy " + elementDBO.getName() + " to cluster "
                    + elementDBO.getCluster() + ". Cluster does not exits");
        }

        // for instance deployment we get the minimum of number of cluster nodes and instance requested
        int min = Math.min(clusterDBO.getSize(), elementDBO.getInstances());
        List<NodeDBO> nodesToDeploy = new ArrayList<NodeDBO>();
        List<edu.colostate.cs.worker.deploy.ElementDBO> deployedElements = new ArrayList<edu.colostate.cs.worker.deploy.ElementDBO>();
        for (int i = 0; i < min; i++) {
            NodeDBO nodeToDeploy = clusterDBO.getNextNode();
            WorkerDBO workerDBO = workerMap.get(nodeToDeploy);
            edu.colostate.cs.worker.deploy.ElementDBO element = getElementDBO(elementDBO);
            if (elementDBO instanceof ProcessorDBO){
                workerDBO.addProcessor(element);
            } else if (elementDBO instanceof  AdapterDBO) {
                workerDBO.addAdapter(element);
            }

            nodesToDeploy.add(nodeToDeploy);
            deployedElements.add(element);
        }
        elementToNodeMap.put(elementDBO, nodesToDeploy);
        elementToElementMap.put(elementDBO, deployedElements);
    }


    private edu.colostate.cs.worker.deploy.ElementDBO getElementDBO(ElementDBO elementDBO) {
        edu.colostate.cs.worker.deploy.ElementDBO element = new edu.colostate.cs.worker.deploy.ElementDBO();
        element.setName(elementDBO.getName());
        element.setClassName(elementDBO.getClassName());

        for (ParameterDBO parameterDBO : elementDBO.getParameters()) {
            edu.colostate.cs.worker.deploy.ParameterDBO param =
                    new edu.colostate.cs.worker.deploy.ParameterDBO(parameterDBO.getName(), parameterDBO.getValue());
            element.addParameter(param);
        }
        return element;
    }

    private TopologyDBO buildTopology() throws DeploymentException {
        try {
            InputStream inputStream =
                    new FileInputStream(this.homeFolder + File.separator + Constants.CLUSTER_FILE_NAME);
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(new InputStreamReader(inputStream), TopologyDBO.class);
        } catch (FileNotFoundException e) {
            throw new DeploymentException("Can not find the cluster config ", e);
        }
    }

    private List<GraphDBO> getGraphs() throws DeploymentException {

        List<GraphDBO> graphs = new ArrayList<GraphDBO>();
        Gson gson = new GsonBuilder().create();
        File graphsFolder = new File(this.homeFolder + File.separator + Constants.GRAPHS_FOLDER);
        try {
            for (File file : graphsFolder.listFiles()) {
                InputStream inputStream = new FileInputStream(file);
                graphs.add(gson.fromJson(new InputStreamReader(inputStream), GraphDBO.class));
            }
            return graphs;
        } catch (FileNotFoundException e) {
            throw new DeploymentException("Problem with reading the file ", e);
        }
    }
}
