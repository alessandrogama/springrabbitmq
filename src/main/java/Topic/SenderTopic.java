package Topic;

import com.conections.rabbitmqdocker.Connect;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
public class SenderTopic {
    private static String NAME_EXCHANGE = "TOPICEXCHANGE";
    public static void main(String[] args) throws Exception{

        Connect connect = new Connect();
        ConnectionFactory factory =  connect.ConfigConnection();

        try(Connection connection = factory.newConnection()){
            Channel channel = connection.createChannel(); //Create channel
            channel.exchangeDeclare(NAME_EXCHANGE,"topic");
            //send message to channel
            String routing_key = "test.queue.work";
            String message = "Hi!, test with queues  in exchange direct with routing key ";
            String routing_key_2 = "queue.work";
            String message_2 = "Hi!, Second test with queues  in exchange direct with routing key "+ routing_key_2;
            channel.basicPublish(NAME_EXCHANGE, routing_key, null, message.getBytes());
            channel.basicPublish(NAME_EXCHANGE, routing_key_2, null, message_2.getBytes());
        }
    }
}
