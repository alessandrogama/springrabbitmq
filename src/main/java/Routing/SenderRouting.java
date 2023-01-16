package Routing;

import com.conections.rabbitmqdocker.Connect;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
public class SenderRouting {
    private static String NAME_EXCHANGE = "DIRECTEXCHANGE";
    private static String ROUTING_KEY = "myroutingkeytest";
    private static String ROUTING_KEY2 = "secondmyroutingkeytest";
    public static void main(String[] args) throws Exception{

        Connect connect = new Connect();
        ConnectionFactory factory =  connect.ConfigConnection();

        try(Connection connection = factory.newConnection()){
            Channel channel = connection.createChannel(); //Create channel
            channel.exchangeDeclare(NAME_EXCHANGE,"direct");
            //send message to channel
            String message = "Hi!, test with queues  in exchange direct with routing key ";
            String message_2 = "Hi!, test with queues  in exchange direct with routing key 2 ";
            channel.basicPublish(NAME_EXCHANGE, ROUTING_KEY, null, message.getBytes());
            channel.basicPublish(NAME_EXCHANGE, ROUTING_KEY2, null, message_2.getBytes());
        }
    }
}
