package edu.colostate.cs.worker.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.colostate.cs.exception.DeploymentException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/12/14
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class Configurator {

    private ConfigDBO configDBO;

    private static Configurator configurator = new Configurator();

    private Configurator() {

    }
    public static Configurator getInstance(){
        return configurator;
    }

    public void configure(String configFile) throws DeploymentException {
        try {
            InputStream inputStream = new FileInputStream(configFile);
            Gson gson = new GsonBuilder().create();
            this.configDBO = gson.fromJson(new InputStreamReader(inputStream), ConfigDBO.class);
        } catch (FileNotFoundException e) {
            throw new DeploymentException("Can not read the config file ", e);
        }
    }

    public int getIoThreads() {
        return this.configDBO.getIoThreads();
    }

    public int getWorkerPoolSize() {
        return this.configDBO.getWorkerPoolSize();
    }

    public int getTcpConnections() {
        return this.configDBO.getTcpConnections();
    }

    public int getByteBufferSize() {
        return this.configDBO.getByteBufferSize();
    }

    public int getTaskBufferMessages(){
        return  this.configDBO.getTaskBuffMessages();
    }

}
