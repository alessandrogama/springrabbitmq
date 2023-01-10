package com.example.springrabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.conections.rabbitmqdocker.Connect;
public class Sender {
    private static String NAME_QUEUE = "FIRSTQUEUE";

    public static void main(String[] args) throws Exception{

        Connect connect = new Connect();
        ConnectionFactory factory =  connect.ConfigConnection();

        try(Connection connection = factory.newConnection()){
            Channel channel = connection.createChannel(); //Create channel
            channel.queueDeclare(NAME_QUEUE, false, false, false, null); //create queue
            String message = "This is my first test send message";
            channel.basicPublish("", NAME_QUEUE, null, message.getBytes()); //send message to channel
        };
    }
}
