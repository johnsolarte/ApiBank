package com.example.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoDTO {
    private String numeroCuenta;
    private Double valor; // positivo = depósito, negativo = retiro
}