package edu.colostate.cs.manager.topology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.colostate.cs.exception.DeploymentException;
import edu.colostate.cs.worker.deploy.WorkerDBO;
import junit.framework.TestCase;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/14/14
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestBuilder extends TestCase{

    public void testBuild(){
        Builder builder = new Builder("test_home");
        try {
            Map<NodeDBO, WorkerDBO> workerMap = builder.build();
            for (Map.Entry<NodeDBO, WorkerDBO> entry : workerMap.entrySet()){
                String fileName = "test_home/results/" + entry.getKey().getIp().replace("-","") + entry.getKey().getPort() + ".json";
                Gson gson = new GsonBuilder().create();
                String jsonString = gson.toJson(entry.getValue());
                FileWriter fileWriter = new FileWriter(fileName);
                fileWriter.write(jsonString);
                fileWriter.close();
            }
        } catch (DeploymentException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
