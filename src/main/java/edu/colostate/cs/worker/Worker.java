package edu.colostate.cs.worker;

import edu.colostate.cs.admin.AdminService;
import edu.colostate.cs.admin.ConnectionManager;
import edu.colostate.cs.exception.DeploymentException;
import edu.colostate.cs.util.Constants;
import edu.colostate.cs.worker.comm.CommManager;
import edu.colostate.cs.worker.config.Configurator;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * this worker would deploy the artefacts for this worker and start the worker.
 */
public class Worker {

    Logger logger = Logger.getLogger(Worker.class.getName());

    public void start(int adminPort, int msgPort, String homeFolder) {

        try {
            WorkerContainer workerContainer = new WorkerContainer();
            Configurator.getInstance().configure(homeFolder + File.separator + Constants.CONFIG_FILE_NAME);

            CommManager commManager = new CommManager(msgPort, workerContainer);
            commManager.start();
            //start the admin connection manager
            ConnectionManager connectionManager =
                    new ConnectionManager(adminPort, new AdminService(workerContainer, commManager));
            connectionManager.start();
        } catch (DeploymentException e) {
            logger.log(Level.SEVERE, "Can not deploy artefacts ", e);
        }

    }

    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.start(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2]);
    }

}
