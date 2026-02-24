package com.example.controller;

import com.example.domain.Movimiento;
import com.example.dto.MovimientoDTO;
import com.example.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @GetMapping
    public ResponseEntity<List<Movimiento>> getAll() {
        return ResponseEntity.ok(movimientoService.findAll());
    }

    @PostMapping
    public ResponseEntity<Movimiento> registrar(@RequestBody MovimientoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoService.registrar(dto));
    }
}