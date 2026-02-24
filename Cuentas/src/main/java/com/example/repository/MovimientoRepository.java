package com.example.repository;

import com.example.domain.Cuenta;
import com.example.domain.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaAndFechaBetween(Cuenta cuenta, LocalDateTime inicio, LocalDateTime fin);
    List<Movimiento> findByCuenta(Cuenta cuenta);
}