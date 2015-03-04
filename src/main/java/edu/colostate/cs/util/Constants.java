package edu.colostate.cs.util;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/17/14
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Constants {

    public static final String STREAM_TYPE_KEY = "key";
    public static final String STREAM_TYPE_RANDOM = "random";
    public static final String HASH_TYPE_RANDOM = "hash";

    public static final String CONFIG_FILE_NAME = "conf" + File.separator + "config.json";
    public static final String DEPLOY_FOLDER = "deploy";

    public static final String CLUSTER_FILE_NAME = "conf" + File.separator + "cluster.json";
    public static final String GRAPHS_FOLDER = "graphs";

    public static final int DEPLOY_MESSAGE_TYPE = 1;
    public static final int ACK_MESSAGE_TYPE = 2;
    public static final int START_ADAPTERS_MESSAGE_TYPE = 3;



}
