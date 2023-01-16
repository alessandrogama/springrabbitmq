package PubSub;

import com.conections.rabbitmqdocker.Connect;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
public class SecondReceiverPubSub {
    private static String NAME_EXCHANGE = "FANOUTEXCHANGE";
    private static String NAME_QUEUE = "broadcast";
    public static void main(String[] args) throws Exception{

        Connect connect = new Connect();
        ConnectionFactory factory =  connect.ConfigConnection();

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(NAME_QUEUE,false,false,false,null);
        channel.exchangeDeclare(NAME_EXCHANGE,"fanout"); // Exchange Declared

        channel.queueBind(NAME_QUEUE,NAME_EXCHANGE,"");

        DeliverCallback delivercallback = (ConsumeTag,delivery) ->{ // get and convert message
            String message = new String(delivery.getBody(),"UTF-8");
        };
        channel.basicConsume(NAME_QUEUE, true, delivercallback, ConsumeTag->{}); //consume channel

    }
}
