package PubConfirmation;

import com.conections.rabbitmqdocker.Connect;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Vector;

public class SenderPubConfirmation {
    private static String NAME_EXCHANGE = "FANOUTEXCHANGE";

    public static void main(String[] args) throws Exception{

        Connect connect = new Connect();
        ConnectionFactory factory =  connect.ConfigConnection();

        try(Connection connection = factory.newConnection()){
            Channel channel = connection.createChannel(); //Create channel
            AMQP.Confirm.SelectOk  selectOk = channel.confirmSelect();

            channel.exchangeDeclare(NAME_EXCHANGE,"fanout");
            Vector<String> message = new Vector<String>(6);
            message.add("Hi, new messages 1");
            message.add("Hi, new messages 2");
            message.add("Hi, new messages 3");
            message.add("Hi, new messages 4");
            message.add("Hi, new messages 5");
            message.add("Hi, new messages 6");
            for (int i = 0; i < message.stream().count(); i++) {
                String body = message.get(i);
                channel.basicPublish(NAME_EXCHANGE, "", null, body.getBytes()); //send message to channel
                channel.waitForConfirmsOrDie(5_000); // wait for 5 seconds
            }
        }
    }
}
