package com.example.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "clientes.exchange";
    public static final String ROUTING_KEY = "clientes.event";
    public static final String QUEUE_NAME = "cuentas.clientes.queue";

    @Bean
    public TopicExchange clientesExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue cuentasClientesQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    @Bean
    public Binding cuentasClientesBinding(Queue cuentasClientesQueue, TopicExchange clientesExchange) {
        return BindingBuilder.bind(cuentasClientesQueue).to(clientesExchange).with(ROUTING_KEY);
    }
}
