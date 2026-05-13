package com.proyecto.odontologia.backend_odontologia.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.odontologia.backend_odontologia.entities.AgendaDto;
import com.proyecto.odontologia.backend_odontologia.entities.Cita;
import com.proyecto.odontologia.backend_odontologia.entities.User;
import com.proyecto.odontologia.backend_odontologia.entities.UserCita;

public interface UserCitaService {

    List<UserCita> findAll();

    UserCita save(User user, Cita cita);

    void delete(Long id);

    Optional<UserCita> findById(Long id);

    List<AgendaDto> findAgenda();
    

}
