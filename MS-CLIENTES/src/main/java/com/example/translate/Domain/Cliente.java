package com.example.translate.Domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Persona {

    @Column(unique = true, nullable = false)
    private String clienteId;

    private String contrasena;
    private Boolean estado;
}