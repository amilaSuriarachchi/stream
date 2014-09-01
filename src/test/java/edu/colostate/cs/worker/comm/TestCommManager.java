package edu.colostate.cs.worker.comm;

import edu.colostate.cs.worker.comm.exception.MessageProcessingException;
import edu.colostate.cs.worker.data.Event;
import edu.colostate.cs.worker.data.Message;

import java.util.concurrent.CountDownLatch;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/21/14
 * Time: 11:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestCommManager {

    public static void main(String[] args) {

//        int numOfMessages = 1000;
//        CountDownLatch countDownLatch = new CountDownLatch(numOfMessages);
//
//        ServerMessageListener messageListener = new ServerMessageListener(countDownLatch);
//        CommManager commManager = new CommManager(8080, messageListener);
//        commManager.start();
//
//        StringMapEvent event = new StringMapEvent();
//        event.setValue("key1", "Value1");
//        event.setValue("key2", "Value2");
//        event.setValue("key3", "Value3");
//
//        Node targetNode = new Node(8080, "localhost");
//
//        try {
//            for (int i = 0 ; i < numOfMessages;i++){
//                Message message = new Message("PE" + i, "PE", event);
//                commManager.sendEvent(message, targetNode);
//            }
//            try {
//                countDownLatch.await();
//                System.out.println("Send all messages ");
//            } catch (InterruptedException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//        } catch (MessageProcessingException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }

    }
}
