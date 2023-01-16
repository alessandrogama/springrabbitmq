package PubSub;

import com.conections.rabbitmqdocker.Connect;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
public class SenderPubSub {
    private static String NAME_EXCHANGE = "FANOUTEXCHANGE";

    public static void main(String[] args) throws Exception{

        Connect connect = new Connect();
        ConnectionFactory factory =  connect.ConfigConnection();

        try(Connection connection = factory.newConnection()){
            Channel channel = connection.createChannel(); //Create channel
            channel.exchangeDeclare(NAME_EXCHANGE,"fanout");
            String message = "Hi!, test with queues  in exchange pub/sub system";
            channel.basicPublish(NAME_EXCHANGE, "", null, message.getBytes()); //send message to channel
        }
    }
}
