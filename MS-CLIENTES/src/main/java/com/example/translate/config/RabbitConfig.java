package com.example.translate.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "clientes.exchange";
    public static final String ROUTING_KEY = "clientes.event";

    @Bean
    public TopicExchange clientesExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // Durable queue so events survive broker restart (simple + safe for demo)
    @Bean
    public Queue cuentasClientesQueue() {
        return QueueBuilder.durable("cuentas.clientes.queue").build();
    }

    @Bean
    public Binding cuentasClientesBinding(Queue cuentasClientesQueue, TopicExchange clientesExchange) {
        return BindingBuilder.bind(cuentasClientesQueue).to(clientesExchange).with(ROUTING_KEY);
    }
}
