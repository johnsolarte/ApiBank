import com.example.translate.Domain.Cliente;
import com.example.translate.Exception.ResourceNotFoundException;
import com.example.translate.Repository.ClienteRepository;
import com.example.translate.Service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void findById_retornaCliente_cuandoExiste() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Jose Lema");
        cliente.setClienteId("jose123");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.findById(1L);

        assertNotNull(result);
        assertEquals("Jose Lema", result.getNombre());
    }

    @Test
    void findById_lanzaExcepcion_cuandoNoExiste() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> clienteService.findById(99L));
    }
}