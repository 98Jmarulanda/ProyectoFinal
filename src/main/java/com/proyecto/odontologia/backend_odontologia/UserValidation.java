package com.proyecto.odontologia.backend_odontologia;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.proyecto.odontologia.backend_odontologia.entities.User;

@Component
public class UserValidation implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty", "Debe de estar diligenciado");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "lastname.empty", "Debe de estar diligenciado");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "phone.empty", "Debe de estar diligenciado");
        if(user.getPhone().length() > 10){
            errors.rejectValue("phone", "phone.empty", "Debe de tener maximo 10 digitos");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.empty", "Debe de estar diligenciado");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "Debe de estar diligenciado");
    }

}
