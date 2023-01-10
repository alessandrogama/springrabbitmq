package com.conections.rabbitmqdocker;

import com.rabbitmq.client.ConnectionFactory;

public class Connect {

    public ConnectionFactory ConfigConnection() throws Exception {
        //Config parameters to connection RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("admin");
        factory.setPassword("pass123");
        factory.setPort(5672);

        return factory;
    }

}
