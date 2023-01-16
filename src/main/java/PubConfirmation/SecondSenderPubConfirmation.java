package PubConfirmation;

import com.conections.rabbitmqdocker.Connect;
import com.rabbitmq.client.*;

import java.util.Vector;

public class SecondSenderPubConfirmation {
    private static String NAME_EXCHANGE = "FANOUTEXCHANGE";
    public static void main(String[] args) throws Exception{

        Connect connect = new Connect();
        ConnectionFactory factory =  connect.ConfigConnection();

        try(Connection connection = factory.newConnection()){
            Channel channel = connection.createChannel(); //Create channel
            AMQP.Confirm.SelectOk  selectOk = channel.confirmSelect();

            channel.exchangeDeclare(NAME_EXCHANGE,"fanout");
            String message = "This is message is number ";
            int setOfMessages = 6;
            int outMessages = 0;

            for (int i = 0; i <  setOfMessages ; i++) {
                String bodyMessage = message+i;
                channel.basicPublish(NAME_EXCHANGE, "", null, bodyMessage.getBytes()); //send message to channel
                if (outMessages == setOfMessages) {
                    channel.waitForConfirmsOrDie(5_000); // wait for 5 seconds
                    outMessages = 0;
                }
            }
            if (outMessages != 0){
                channel.waitForConfirmsOrDie(5_000); // wait for 5 seconds
            }
        }
    }
}
