package com.proyecto.odontologia.backend_odontologia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.odontologia.backend_odontologia.entities.Cita;
import com.proyecto.odontologia.backend_odontologia.repositories.CitaRepository;

@Service
public class CitaSerivceImpl implements CitaService{

    @Autowired
    private CitaRepository citaRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Cita> findAll() {
        return (List<Cita>) citaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cita> findById(Long id) {
        return citaRepository.findById(id);
    }

    @Override
    @Transactional
    public Cita save(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    @Transactional
    public Optional<Cita> update(Long id, Cita cita) {
        Optional<Cita> optionalCita = citaRepository.findById(id);
        if(optionalCita.isPresent()){
            Cita citaDb = optionalCita.orElseThrow();
            citaDb.setName(cita.getName());
            citaDb.setDescription(cita.getDescription());
            citaDb.setPrice(cita.getPrice());
            citaDb.setDateTime(cita.getDateTime());
            return Optional.of(citaRepository.save(citaDb));
        };
        return optionalCita;
    }

    @Override
    @Transactional
    public Optional<Cita> delete(Long id) {
        Optional<Cita> optionalCita = citaRepository.findById(id);
        optionalCita.ifPresent(citaDb -> {
            citaRepository.delete(citaDb);
        });
        return optionalCita;
    }

   

}
