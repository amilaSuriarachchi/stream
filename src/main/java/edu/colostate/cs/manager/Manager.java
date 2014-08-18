package edu.colostate.cs.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.colostate.cs.admin.ConnectionException;
import edu.colostate.cs.admin.ConnectionManager;
import edu.colostate.cs.admin.message.*;
import edu.colostate.cs.exception.DeploymentException;
import edu.colostate.cs.manager.topology.Builder;
import edu.colostate.cs.manager.topology.NodeDBO;
import edu.colostate.cs.worker.deploy.WorkerDBO;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/13/14
 * Time: 1:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class Manager {

    Logger logger = Logger.getLogger(Manager.class.getName());

    private void start(int adminPort, String homeFolder) {

        ConnectionManager connectionManager = new ConnectionManager(adminPort, null);
        Builder builder = new Builder(homeFolder);
        try {
            Map<NodeDBO, WorkerDBO> nodeWorkerMap = builder.build();
            // send the message to every worker
            Gson gson = new GsonBuilder().create();
            for (Map.Entry<NodeDBO, WorkerDBO> entry : nodeWorkerMap.entrySet()) {
                String jsonString = gson.toJson(entry.getValue());
                DeployMessage deployMessage = new DeployMessage(jsonString);
                Message response =
                        connectionManager.sendMessage(deployMessage, entry.getKey().getIp(), entry.getKey().getAdminPort());
                logger.log(Level.INFO, ((ACKMessage) response).getMessage());
            }

            for (Map.Entry<NodeDBO, WorkerDBO> entry : nodeWorkerMap.entrySet()) {
                StartAdaptersMessage startAdaptersMessage = new StartAdaptersMessage();
                Message response = connectionManager.sendMessage(startAdaptersMessage,
                        entry.getKey().getIp(), entry.getKey().getAdminPort());
                logger.log(Level.INFO, ((ACKMessage) response).getMessage());
            }
        } catch (DeploymentException e) {
            logger.log(Level.SEVERE, "Can not start the server ", e);
        } catch (ConnectionException e) {
            logger.log(Level.SEVERE, "Can not connect to the worker ", e);
        } catch (MessageProcessingException e) {
            logger.log(Level.SEVERE, "Can not process the message ", e);
        }

    }

    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.start(Integer.parseInt(args[0]), args[1]);
    }

}
