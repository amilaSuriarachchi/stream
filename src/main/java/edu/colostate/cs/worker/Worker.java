package edu.colostate.cs.worker;

import edu.colostate.cs.worker.comm.CommManager;
import edu.colostate.cs.worker.comm.exception.DeploymentException;
import edu.colostate.cs.worker.deploy.Deployer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * this worker would deploy the artefacts for this worker and start the worker.
 */
public class Worker {

    Logger logger = Logger.getLogger(Worker.class.getName());

    public void start(int port, String deployFolder) {

        WorkerContainer workerContainer = new WorkerContainer();
        CommManager commManager = new CommManager(port, workerContainer);
        Deployer deployer = new Deployer(workerContainer, commManager, deployFolder);
        try {
            deployer.deploy();
            commManager.start();
            workerContainer.startAdapters();
        } catch (DeploymentException e) {
            logger.log(Level.SEVERE, "Can not deploy artefacts ", e);
        }

    }

    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.start(Integer.parseInt(args[0]), args[1]);
    }

}
