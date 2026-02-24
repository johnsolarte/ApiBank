package com.example.client;

import com.example.repository.ClienteCacheRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Component
public class ClienteClient {

    private final RestTemplate restTemplate;
    private final ClienteCacheRepository clienteCacheRepository;

    @Value("${clientes.service.url}")
    private String clientesUrl;

    public ClienteClient(RestTemplateBuilder builder, ClienteCacheRepository clienteCacheRepository) {
        this.restTemplate = builder.build();
        this.clienteCacheRepository = clienteCacheRepository;
    }

    public String getNombreCliente(String clienteId) {
        Optional<String> cached = clienteCacheRepository.findById(clienteId)
                .map(c -> c.getNombre());

        if (cached.isPresent() && cached.get() != null && !cached.get().isBlank()) {
            return cached.get();
        }

        try {
            Map response = restTemplate.getForObject(
                    clientesUrl + "/clientes/clienteId/" + clienteId, Map.class);
            return response != null ? (String) response.get("nombre") : "Desconocido";
        } catch (Exception e) {
            return "Desconocido";
        }
    }
}