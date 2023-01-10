package com.example.springrabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.conections.rabbitmqdocker.Connect;
public class Receiver {
    private static String NAME_QUEUE = "FIRSTQUEUE";

    public static void main(String[] args) throws Exception{

        Connect connect = new Connect();
        ConnectionFactory factory =  connect.ConfigConnection();

            Connection connection = factory.newConnection();

            Channel channel = connection.createChannel();
            channel.queueDeclare(NAME_QUEUE, false, false, false, null);
            DeliverCallback delivercallback = (ConsumeTag,delivery) ->{ // get and convert message
            String message = new String(delivery.getBody(),"UTF-8");};
            channel.basicConsume(NAME_QUEUE, true, delivercallback, ConsumeTag->{}); //consume channel

    }
}
