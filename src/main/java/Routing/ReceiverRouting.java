package Routing;

import com.conections.rabbitmqdocker.Connect;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
public class ReceiverRouting {
    private static String NAME_EXCHANGE = "DIRECTEXCHANGE";
    private static String BINDKEY_NAME = "myroutingkeytest";
    public static void main(String[] args) throws Exception{

        Connect connect = new Connect();
        ConnectionFactory factory =  connect.ConfigConnection();

            Connection connection = factory.newConnection();

            Channel channel = connection.createChannel();

            String nameQueue =   channel.queueDeclare().getQueue(); //queue tempory

            channel.exchangeDeclare(NAME_EXCHANGE,"direct"); // Exchange Declared
            channel.queueBind(nameQueue,NAME_EXCHANGE,BINDKEY_NAME);

            DeliverCallback delivercallback = (ConsumeTag,delivery) ->{ // get and convert message
            String message = new String(delivery.getBody(),"UTF-8");
            };
            channel.basicConsume(nameQueue, true, delivercallback, ConsumeTag->{}); //consume channel

    }
}
