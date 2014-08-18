package edu.colostate.cs.admin;


import edu.colostate.cs.admin.message.Message;


public interface MessageCallback {

    public Message messageReceived(Message message);
}
