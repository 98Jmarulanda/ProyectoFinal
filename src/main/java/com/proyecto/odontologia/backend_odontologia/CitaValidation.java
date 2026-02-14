package com.proyecto.odontologia.backend_odontologia;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.proyecto.odontologia.backend_odontologia.entities.Cita;
import com.proyecto.odontologia.backend_odontologia.repositories.CitaRepository;


@Component
public class CitaValidation implements Validator {

    @Autowired
    private CitaRepository citaRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Cita.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Cita cita = (Cita) target;  
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty", "Debe de estar diligenciado");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "phone.empty", "Debe de estar diligenciado");
        if (cita.getPrice() != null && cita.getPrice() <= 0) {
            errors.rejectValue("price", "price.invalid", "Debe ser mayor a 0");
        }
        if(cita.getDateTime() != null){
            if(cita.getDateTime().isBefore(LocalDateTime.now())){
                errors.rejectValue("dateTime", "dateTime.past", "La fecha no puede ser anterior a la actual");
            }
        }
        if(cita.getDateTime() != null && citaRepository.existsByDateTime(cita.getDateTime())){
            errors.rejectValue("dateTime", "dateTime.duplicated", "Ya existe una cita para esa fecha y hora");
        }
    }

    
}
