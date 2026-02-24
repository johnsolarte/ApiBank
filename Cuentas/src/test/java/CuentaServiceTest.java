import com.example.domain.Cuenta;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.CuentaRepository;
import com.example.service.CuentaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CuentaServiceTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private CuentaService cuentaService;

    @Test
    void findById_retornaCuenta_cuandoExiste() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("478758");
        cuenta.setTipoCuenta("Ahorros");
        cuenta.setSaldoDisponible(2000.0);

        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));

        Cuenta result = cuentaService.findById(1L);

        assertNotNull(result);
        assertEquals("478758", result.getNumeroCuenta());
    }

    @Test
    void findById_lanzaExcepcion_cuandoNoExiste() {
        when(cuentaRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> cuentaService.findById(99L));
    }
}