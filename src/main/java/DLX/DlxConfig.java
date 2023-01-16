package DLX;

import com.conections.rabbitmqdocker.Connect;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.ObjectStreamClass;
import java.util.HashMap;
import java.util.Map;

public class DlxConfig {
    private static final String  DLX_NAME = "dlxExchange";
    private static final String  DLX_QUEUE = "dlxQueue";
    private static final String  DLX_BIDING_KEY = "dlxrk";
    private static final String EXCHANGE_NAME = "mainExchange";
    private static final String  CONSUMER_QUEUE = "queueConsumer";
    private static final String  CONSUMER_BINDING_KEY = "bkConsumer";
    public static void main(String[] args) throws Exception{

        Connect connect = new Connect();
        ConnectionFactory factory =  connect.ConfigConnection();
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel(); //Create channel

        channel.exchangeDeclare(DLX_NAME,"topic");
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        channel.queueDeclare(DLX_QUEUE,false,false,false,null);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("x-message-ttl",1000);
        map.put("x-dead-letter-exchange",DLX_NAME);
        map.put("x-dead-letter-routing-key",DLX_BIDING_KEY);
        channel.queueDeclare(CONSUMER_QUEUE,false,false,false,map);

        channel.queueBind(DLX_QUEUE,DLX_NAME,DLX_BIDING_KEY+".#");
        channel.queueBind(CONSUMER_QUEUE,EXCHANGE_NAME,CONSUMER_BINDING_KEY+".#");

        connection.close();
    }
}
