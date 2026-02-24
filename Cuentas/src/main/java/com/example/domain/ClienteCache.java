package com.example.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clientes_cache")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCache {

    @Id
    @Column(nullable = false, unique = true)
    private String clienteId;

    private String nombre;

    private Boolean estado;
}
