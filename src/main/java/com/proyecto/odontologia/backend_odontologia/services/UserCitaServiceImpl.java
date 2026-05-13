package com.proyecto.odontologia.backend_odontologia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.odontologia.backend_odontologia.entities.AgendaDto;
import com.proyecto.odontologia.backend_odontologia.entities.Cita;
import com.proyecto.odontologia.backend_odontologia.entities.User;
import com.proyecto.odontologia.backend_odontologia.entities.UserCita;
import com.proyecto.odontologia.backend_odontologia.repositories.UserCitaRepository;

@Service    
public class UserCitaServiceImpl implements UserCitaService{

    @Autowired
    private UserCitaRepository userCitaRepository;

    @Transactional(readOnly = true)
    @Override
    public List<UserCita> findAll() {
        return (List<UserCita>) userCitaRepository.findAll();
    }   

    @Transactional
    @Override
    public UserCita save(User user, Cita cita) {

        UserCita userCita = new UserCita();
        userCita.setUser(user);
        userCita.setCita(cita);
        return userCitaRepository.save(userCita);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if(!userCitaRepository.existsById(id)){
            throw new RuntimeException("Reserva no encontrada");
        }

        userCitaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserCita> findById(Long id) {
        return userCitaRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AgendaDto> findAgenda() {
        return userCitaRepository.getAgenda();
    }
    

}
