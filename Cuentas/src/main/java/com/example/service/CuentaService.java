package com.example.service;

import com.example.domain.Cuenta;
import com.example.dto.CuentaDTO;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada: " + id));
    }

    public Cuenta findByNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada: " + numeroCuenta));
    }

    public Cuenta save(CuentaDTO dto) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setSaldoDisponible(dto.getSaldoInicial());
        cuenta.setEstado(dto.getEstado());
        cuenta.setClienteId(dto.getClienteId());
        return cuentaRepository.save(cuenta);
    }

    public Cuenta update(Long id, CuentaDTO dto) {
        Cuenta cuenta = findById(id);
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setEstado(dto.getEstado());
        cuenta.setClienteId(dto.getClienteId());
        return cuentaRepository.save(cuenta);
    }

    public Cuenta patch(Long id, CuentaDTO dto) {
        Cuenta cuenta = findById(id);
        if (dto.getTipoCuenta() != null) cuenta.setTipoCuenta(dto.getTipoCuenta());
        if (dto.getEstado() != null) cuenta.setEstado(dto.getEstado());
        return cuentaRepository.save(cuenta);
    }
}