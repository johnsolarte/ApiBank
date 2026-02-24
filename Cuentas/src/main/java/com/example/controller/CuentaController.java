package com.example.controller;

import com.example.domain.Cuenta;
import com.example.dto.CuentaDTO;
import com.example.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<Cuenta>> getAll() {
        return ResponseEntity.ok(cuentaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Cuenta> create(@RequestBody CuentaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> update(@PathVariable Long id, @RequestBody CuentaDTO dto) {
        return ResponseEntity.ok(cuentaService.update(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cuenta> patch(@PathVariable Long id, @RequestBody CuentaDTO dto) {
        return ResponseEntity.ok(cuentaService.patch(id, dto));
    }
}
