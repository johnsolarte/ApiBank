package com.example.event;

import com.example.config.RabbitConfig;
import com.example.domain.ClienteCache;
import com.example.repository.ClienteCacheRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteEventListener {

    private final ClienteCacheRepository clienteCacheRepository;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void onMessage(byte[] body) {
        try {
            ClienteEvent event = objectMapper.readValue(body, ClienteEvent.class);

            if (event.getType() == ClienteEventType.DELETED) {
                if (event.getClienteId() != null) {
                    clienteCacheRepository.deleteById(event.getClienteId());
                }
                return;
            }

            if (event.getClienteId() == null) {
                return;
            }

            ClienteCache cache = new ClienteCache(event.getClienteId(), event.getNombre(), event.getEstado());
            clienteCacheRepository.save(cache);
        } catch (Exception e) {
            // best-effort: ignore malformed messages
        }
    }
}
