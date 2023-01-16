package Topic;

import com.conections.rabbitmqdocker.Connect;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
public class ReceiverTopic {
    private static String NAME_EXCHANGE = "TOPICEXCHANGE";
    public static void main(String[] args) throws Exception{
        Connect connect = new Connect();
        ConnectionFactory factory =  connect.ConfigConnection();

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        String nameQueue =   channel.queueDeclare().getQueue(); //queue tempory
        String bindingkey = "*.*.work";
        channel.exchangeDeclare(NAME_EXCHANGE,"topic"); // Exchange Declared
        channel.queueBind(nameQueue,NAME_EXCHANGE,bindingkey);

        DeliverCallback delivercallback = (ConsumeTag,delivery) ->{ // get and convert message
            String message = new String(delivery.getBody(),"UTF-8");
        };
        channel.basicConsume(nameQueue, true, delivercallback, ConsumeTag->{}); //consume channel

    }
}
