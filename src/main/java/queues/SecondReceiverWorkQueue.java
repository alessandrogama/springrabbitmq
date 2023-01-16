package queues;

import com.conections.rabbitmqdocker.Connect;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class SecondReceiverWorkQueue {
    private static String NAME_QUEUE = "WORKQUEUE";
    private static void doWork(String task) throws InterruptedException {
        for (char ch:task.toCharArray()) {
            if(ch == '.')Thread.sleep(1000);
        }
    }
    public static void main(String[] args) throws Exception{

        Connect connect = new Connect();
        ConnectionFactory factory =  connect.ConfigConnection();

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(NAME_QUEUE, false, false, false, null);
        DeliverCallback delivercallback = (ConsumeTag,delivery) ->{ // get and convert message
            String message = new String(delivery.getBody(),"UTF-8");
            try{
                doWork(message);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println("[X]Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
            }
        };
        boolean Autoack = false; // ack is on
        channel.basicConsume(NAME_QUEUE, Autoack, delivercallback, ConsumeTag->{}); //consume channel

    }
}