package com.example.controller;

import com.example.dto.ReporteDTO;
import com.example.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    // /api/reportes?fechaInicio=2022-01-01&fechaFin=2022-12-31&clienteId=jose123
    @GetMapping
    public ResponseEntity<List<ReporteDTO>> getReporte(
            @RequestParam String clienteId,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {

        LocalDateTime inicio = LocalDate.parse(fechaInicio).atStartOfDay();
        LocalDateTime fin = LocalDate.parse(fechaFin).atTime(23, 59, 59);

        return ResponseEntity.ok(reporteService.generarReporte(clienteId, inicio, fin));
    }
}