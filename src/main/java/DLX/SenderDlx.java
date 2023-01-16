package DLX;

import com.conections.rabbitmqdocker.Connect;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Vector;

public class SenderDlx {
    private static String NAME_EXCHANGE = "mainExchange";

    public static void main(String[] args) throws Exception{

        Connect connect = new Connect();
        ConnectionFactory factory =  connect.ConfigConnection();

        try(Connection connection = factory.newConnection()){
            Channel channel = connection.createChannel(); //Create channel
            AMQP.Confirm.SelectOk  selectOk = channel.confirmSelect();

            channel.exchangeDeclare(NAME_EXCHANGE,"topic");
            String message = "Hi, message test for DLX";
            String routingKey = "bkConsumer.test1";
            channel.basicPublish(NAME_EXCHANGE, routingKey,null , message.getBytes()); //send message to channel
        }
    }
}
