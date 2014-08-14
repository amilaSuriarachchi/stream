package edu.colostate.cs.manager.topology;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 8/13/14
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class TopologyDBO {

    private List<ClusterDBO> clusters;

    public ClusterDBO getCluster(String name){
        for (ClusterDBO clusterDBO : this.clusters){
            if (clusterDBO.getName().equals(name)){
                return clusterDBO;
            }
        }
        return null;
    }

    public List<ClusterDBO> getClusters() {
        return clusters;
    }

    public void setClusters(List<ClusterDBO> clusters) {
        this.clusters = clusters;
    }
}
