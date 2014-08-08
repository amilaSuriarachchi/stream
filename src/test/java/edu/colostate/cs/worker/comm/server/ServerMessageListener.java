package edu.colostate.cs.worker.comm.server;

import edu.colostate.cs.worker.data.Message;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/21/14
 * Time: 3:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerMessageListener implements MessageListener {
    private AtomicLong atomicLong = new AtomicLong();
    private long lastTime = System.currentTimeMillis();

    public void onMessage(Message message) {
        long currentValue = this.atomicLong.incrementAndGet();
        if ((currentValue % 1000000) == 0){
            System.out.println("Message Rate ==> " + 1000000000/ (System.currentTimeMillis() - this.lastTime));
            this.lastTime =  System.currentTimeMillis();
        }
    }
}
