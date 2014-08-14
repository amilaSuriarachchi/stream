package edu.colostate.cs.worker.comm.client;

import edu.colostate.cs.worker.comm.Node;
import edu.colostate.cs.worker.config.Configurator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/17/14
 * Time: 12:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientIOReactor implements Runnable {

    Logger logger = Logger.getLogger(ClientIOReactor.class.getName());

    private Queue<ClientConnection> pendingClientConnections;

    public ClientIOReactor() {
        this.pendingClientConnections = new ConcurrentLinkedQueue<ClientConnection>();
    }

    public void add(ClientConnection clientConnection) {
        this.pendingClientConnections.add(clientConnection);
    }


    public void run() {

        try {
            Selector selector = Selector.open();

            List<ChannelReactor> channelReactors = new ArrayList<ChannelReactor>();
            //create channel reactors according to the number of processors
            int numberOfProcessors = Configurator.getInstance().getIoThreads();
            for (int i = 0; i < numberOfProcessors; i++) {
                channelReactors.add(new ChannelReactor());
            }
            // start the channel reactors
            for (ChannelReactor channelReactor : channelReactors) {
                Thread thread = new Thread(channelReactor);
                thread.start();
            }

            int lastChennelSelected = 0;

            //TODO: think how to stop this looping for ever. If there are no pending tasks and all connections has
            //connected we need to make it wait
            while (selector.isOpen()) {

                ClientConnection clientConnection;
                while ((clientConnection = this.pendingClientConnections.poll()) != null) {
                    Node targetNode = clientConnection.getTargetNode();
                    // create connections for that
                    for (int i = 0; i < Configurator.getInstance().getTcpConnections(); i++) {
                        SocketChannel socketChannel = SocketChannel.open();
                        socketChannel.configureBlocking(false);
                        socketChannel.connect(new InetSocketAddress(targetNode.getIpAddress(), targetNode.getPort()));
                        socketChannel.register(selector, SelectionKey.OP_CONNECT, clientConnection);
                    }

                    selector.select();
                    for (SelectionKey selectionKey : selector.selectedKeys()) {
                        if (selectionKey.isConnectable()) {
                            SocketChannel channel = (SocketChannel) selectionKey.channel();
                            if (!channel.finishConnect()) {
                                continue;
                            }
                            //find a chennel to handover this thread in round robin manner
                            int channelNum = lastChennelSelected % numberOfProcessors;
                            lastChennelSelected++;
                            channelReactors.get(channelNum).addNewChannel(selectionKey);
                        }
                    }
                    selector.selectedKeys().clear();
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can not open the selector");
        }
    }
}