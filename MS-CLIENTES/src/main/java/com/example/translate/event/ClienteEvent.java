package com.example.translate.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEvent {
    private ClienteEventType type;
    private String clienteId;
    private String nombre;
    private Boolean estado;
}
