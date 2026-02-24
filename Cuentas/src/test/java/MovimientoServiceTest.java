import com.example.domain.Cuenta;
import com.example.domain.Movimiento;
import com.example.dto.MovimientoDTO;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.SaldoInsuficienteException;
import com.example.repository.CuentaRepository;
import com.example.repository.MovimientoRepository;
import com.example.service.MovimientoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovimientoServiceTest {

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private MovimientoService movimientoService;

    @Test
    void registrar_depositoExitoso() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("478758");
        cuenta.setSaldoDisponible(2000.0);

        MovimientoDTO dto = new MovimientoDTO("478758", 500.0);

        when(cuentaRepository.findByNumeroCuenta("478758")).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Movimiento result = movimientoService.registrar(dto);

        assertNotNull(result);
        assertEquals(2500.0, cuenta.getSaldoDisponible());
        assertEquals("Depósito", result.getTipoMovimiento());
    }

    @Test
    void registrar_retiroExitoso() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("478758");
        cuenta.setSaldoDisponible(2000.0);

        MovimientoDTO dto = new MovimientoDTO("478758", -575.0);

        when(cuentaRepository.findByNumeroCuenta("478758")).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Movimiento result = movimientoService.registrar(dto);

        assertNotNull(result);
        assertEquals(1425.0, cuenta.getSaldoDisponible());
        assertEquals("Retiro", result.getTipoMovimiento());
    }

    @Test
    void registrar_lanzaExcepcion_cuandoSaldoInsuficiente() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("495878");
        cuenta.setSaldoDisponible(0.0);

        MovimientoDTO dto = new MovimientoDTO("495878", -999.0);

        when(cuentaRepository.findByNumeroCuenta("495878")).thenReturn(Optional.of(cuenta));

        assertThrows(SaldoInsuficienteException.class, () -> movimientoService.registrar(dto));
    }

    @Test
    void registrar_lanzaExcepcion_cuandoCuentaNoExiste() {
        MovimientoDTO dto = new MovimientoDTO("000000", -100.0);
        when(cuentaRepository.findByNumeroCuenta("000000")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> movimientoService.registrar(dto));
    }
}