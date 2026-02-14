package com.proyecto.odontologia.backend_odontologia.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.odontologia.backend_odontologia.entities.Cita;

public interface CitaService {

    List<Cita> findAll();

    Optional<Cita> findById(Long id);

    Cita save (Cita cita);

    Optional<Cita> update (Long id, Cita cita);

    Optional<Cita> delete(Long id);

}
