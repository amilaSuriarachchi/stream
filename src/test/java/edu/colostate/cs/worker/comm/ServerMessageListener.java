package edu.colostate.cs.worker.comm;

import edu.colostate.cs.worker.comm.server.MessageListener;
import edu.colostate.cs.worker.data.Message;

import java.util.concurrent.CountDownLatch;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/21/14
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerMessageListener implements MessageListener {

    private CountDownLatch countDownLatch;

    public ServerMessageListener(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void onMessage(Message message) {
        System.out.println("Got the message " + message);
        this.countDownLatch.countDown();
    }
}
