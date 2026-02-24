package com.example.service;

import com.example.client.ClienteClient;
import com.example.domain.*;
import com.example.dto.ReporteDTO;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final ClienteClient clienteClient;

    public List<ReporteDTO> generarReporte(String clienteId, LocalDateTime inicio, LocalDateTime fin) {
        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);
        String nombreCliente = clienteClient.getNombreCliente(clienteId);

        List<ReporteDTO> reporte = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            List<Movimiento> movimientos = movimientoRepository
                    .findByCuentaAndFechaBetween(cuenta, inicio, fin);

            for (Movimiento m : movimientos) {
                ReporteDTO dto = new ReporteDTO();
                dto.setFecha(m.getFecha().toLocalDate().toString());
                dto.setCliente(nombreCliente);
                dto.setNumeroCuenta(cuenta.getNumeroCuenta());
                dto.setTipo(cuenta.getTipoCuenta());
                dto.setSaldoInicial(cuenta.getSaldoInicial());
                dto.setEstado(cuenta.getEstado());
                dto.setMovimiento(m.getValor());
                dto.setSaldoDisponible(m.getSaldo());
                reporte.add(dto);
            }
        }
        return reporte;
    }
}