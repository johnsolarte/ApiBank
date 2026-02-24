package com.example.translate.event;

import com.example.translate.config.RabbitConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange clientesExchange;
    private final ObjectMapper objectMapper;

    public void publish(ClienteEvent event) {
        try {
            byte[] body = objectMapper.writeValueAsBytes(event);

            MessageProperties props = new MessageProperties();
            props.setContentType(MessageProperties.CONTENT_TYPE_JSON);

            rabbitTemplate.send(clientesExchange.getName(), RabbitConfig.ROUTING_KEY, new Message(body, props));
        } catch (JsonProcessingException e) {
            // No rompemos el flujo principal (requisito: no afectar funcionamiento)
            // Si Rabbit no está disponible o hay error de serialización, se ignora el evento.
        }
    }
}
