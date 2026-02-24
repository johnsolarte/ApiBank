package com.example.translate.Service;

import com.example.translate.Domain.Cliente;
import com.example.translate.Dto.ClienteDTO;
import com.example.translate.Exception.ResourceNotFoundException;
import com.example.translate.Repository.ClienteRepository;
import com.example.translate.event.ClienteEvent;
import com.example.translate.event.ClienteEventPublisher;
import com.example.translate.event.ClienteEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteEventPublisher clienteEventPublisher;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado: " + id));
    }

    public Cliente findByClienteId(String clienteId) {
        return clienteRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado: " + clienteId));
    }

    public Cliente save(ClienteDTO dto) {
        Cliente cliente = toEntity(dto);
        Cliente saved = clienteRepository.save(cliente);
        clienteEventPublisher.publish(new ClienteEvent(
                ClienteEventType.CREATED,
                saved.getClienteId(),
                saved.getNombre(),
                saved.getEstado()
        ));
        return saved;
    }

    public Cliente update(Long id, ClienteDTO dto) {
        Cliente cliente = findById(id);
        cliente.setNombre(dto.getNombre());
        cliente.setGenero(dto.getGenero());
        cliente.setEdad(dto.getEdad());
        cliente.setIdentificacion(dto.getIdentificacion());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        cliente.setContrasena(dto.getContrasena());
        cliente.setEstado(dto.getEstado());
        Cliente saved = clienteRepository.save(cliente);
        clienteEventPublisher.publish(new ClienteEvent(
                ClienteEventType.UPDATED,
                saved.getClienteId(),
                saved.getNombre(),
                saved.getEstado()
        ));
        return saved;
    }

    public Cliente patch(Long id, ClienteDTO dto) {
        Cliente cliente = findById(id);
        if (dto.getNombre() != null) cliente.setNombre(dto.getNombre());
        if (dto.getEstado() != null) cliente.setEstado(dto.getEstado());
        if (dto.getDireccion() != null) cliente.setDireccion(dto.getDireccion());
        if (dto.getTelefono() != null) cliente.setTelefono(dto.getTelefono());
        Cliente saved = clienteRepository.save(cliente);
        clienteEventPublisher.publish(new ClienteEvent(
                ClienteEventType.UPDATED,
                saved.getClienteId(),
                saved.getNombre(),
                saved.getEstado()
        ));
        return saved;
    }

    public void delete(Long id) {
        Cliente existing = findById(id);
        clienteRepository.deleteById(id);

        clienteEventPublisher.publish(new ClienteEvent(
                ClienteEventType.DELETED,
                existing.getClienteId(),
                existing.getNombre(),
                existing.getEstado()
        ));
    }

    private Cliente toEntity(ClienteDTO dto) {
        Cliente c = new Cliente();
        c.setNombre(dto.getNombre());
        c.setGenero(dto.getGenero());
        c.setEdad(dto.getEdad());
        c.setIdentificacion(dto.getIdentificacion());
        c.setDireccion(dto.getDireccion());
        c.setTelefono(dto.getTelefono());
        c.setClienteId(dto.getClienteId());
        c.setContrasena(dto.getContrasena());
        c.setEstado(dto.getEstado());
        return c;
    }
}