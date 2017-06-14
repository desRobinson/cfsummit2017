package com.arity.a6;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    private ConnectionFactory connectionFactory;

    public AmqpConfig() {
        CachingConnectionFactory factory = new CachingConnectionFactory();

        factory.setUri("amqp://guest@rabbitmq-server:5672/%2f");
        factory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
        factory.setChannelCacheSize(20);
        factory.setConnectionCacheSize(10);
        factory.setCloseTimeout(5000);

        this.connectionFactory = factory;
    }
}
