package com.example.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class ClienteClient {

    private final RestTemplate restTemplate;

    @Value("${clientes.service.url}")
    private String clientesUrl;

    public ClienteClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public String getNombreCliente(String clienteId) {
        try {
            Map response = restTemplate.getForObject(
                    clientesUrl + "/clientes/clienteId/" + clienteId, Map.class);
            return response != null ? (String) response.get("nombre") : "Desconocido";
        } catch (Exception e) {
            return "Desconocido";
        }
    }
}