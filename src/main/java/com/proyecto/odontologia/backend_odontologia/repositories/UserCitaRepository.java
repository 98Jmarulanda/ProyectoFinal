package com.proyecto.odontologia.backend_odontologia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyecto.odontologia.backend_odontologia.entities.AgendaDto;
import com.proyecto.odontologia.backend_odontologia.entities.UserCita;

public interface UserCitaRepository extends CrudRepository<UserCita, Long>{


    @Query("""
        SELECT new com.proyecto.odontologia.backend_odontologia.entities.AgendaDto(
        uc.id,
        uc.user.name,
        uc.user.lastname,
        uc.user.phone,
        uc.cita.name,
        uc.cita.dateTime
        ) FROM UserCita uc
        """)
    public List<AgendaDto> getAgenda();
    

}
