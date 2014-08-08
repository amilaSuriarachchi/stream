package edu.colostate.cs.worker.comm.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/19/14
 * Time: 9:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelReactor implements Runnable {

    private Logger logger = Logger.getLogger(ChannelReactor.class.getName());

    private Queue<SocketChannel> connectionQueue;
    private Selector selector;
    private ServerStreamHandler serverStreamHandler;

    public ChannelReactor(ServerStreamHandler serverStreamHandler) throws IOException {
        this.connectionQueue = new ConcurrentLinkedQueue<SocketChannel>();
        this.selector = Selector.open();
        this.serverStreamHandler = serverStreamHandler;
    }

    public void run() {

        while (this.selector.isOpen()) {
            try {
                processNewChannels();
                this.selector.select();
                for (SelectionKey selectionKey : this.selector.selectedKeys()) {
                    if (selectionKey.isReadable()) {
                        DataReader dataReader = (DataReader) selectionKey.attachment();
                        dataReader.readReady(selectionKey);
                        if (dataReader.isStart()){
                            dataReader.setStart(false);
                            this.serverStreamHandler.requestReceived(dataReader);
                        }
                    }
                }
                this.selector.selectedKeys().clear();

            } catch (Exception e) {
                logger.log(Level.SEVERE, "Can not process the channel ", e);
            }
        }

    }

    private void processNewChannels() throws IOException {
        SocketChannel socketChannel;
        while ((socketChannel = this.connectionQueue.poll()) != null) {
            // register this channel with this selector
            socketChannel.configureBlocking(false);
            DataReader dataReader = new DataReader();
            socketChannel.register(this.selector, SelectionKey.OP_READ, dataReader);

        }
    }

    public void addNewChannel(SocketChannel socketChannel) {
        this.connectionQueue.add(socketChannel);
        //selector thread may be blocking for new connections. Need to wake up that.
        this.selector.wakeup();
    }
}
