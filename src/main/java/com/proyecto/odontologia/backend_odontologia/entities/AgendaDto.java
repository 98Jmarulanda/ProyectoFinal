package com.proyecto.odontologia.backend_odontologia.entities;

import java.time.LocalDateTime;

public record AgendaDto(
    Long id,
    String name,
    String lastname,
    String phone,
    String citaNombre,
    LocalDateTime dateTime) {}
