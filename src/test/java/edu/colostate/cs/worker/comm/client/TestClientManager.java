package edu.colostate.cs.worker.comm.client;

import edu.colostate.cs.worker.comm.Node;
import edu.colostate.cs.worker.comm.StringMapEvent;
import edu.colostate.cs.worker.data.Event;
import edu.colostate.cs.worker.data.Message;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/21/14
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestClientManager {

    public static void main(String[] args) {
        ClientManager clientManager = new ClientManager();
        clientManager.start();

        StringMapEvent event = new StringMapEvent();
        event.setValue("key1", "Value1");
        event.setValue("key2", "Value2");
        event.setValue("key3", "Value3");
        event.setValue("key4", "Value4");
        event.setValue("key5", "Value5");

        Node targetNode = new Node(Integer.parseInt(args[0]), args[1]);
        Message message = new Message("PE1", "PE2", event);

//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        DataOutput dataOutput = new DataOutputStream(outputStream);
//
//        try {
//            message.serialize(dataOutput);
//            System.out.println("Size ==> " + outputStream.toByteArray().length);
//        } catch (MessageProcessingException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }

        int numOfThreads = Integer.parseInt(args[2]);
        for (int i = 0 ; i < numOfThreads ; i++){
            MessageSender messageSender = new MessageSender(clientManager, message, targetNode);
            Thread thread = new Thread(messageSender);
            thread.start();
        }

    }
}
