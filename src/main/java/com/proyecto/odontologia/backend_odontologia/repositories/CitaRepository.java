package com.proyecto.odontologia.backend_odontologia.repositories;

import java.time.LocalDateTime;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.proyecto.odontologia.backend_odontologia.entities.Cita;

@CrossOrigin(originPatterns = "http://localhost:5173")
public interface CitaRepository extends CrudRepository <Cita, Long>{

    boolean existsByDateTime(LocalDateTime dateTime);

}
