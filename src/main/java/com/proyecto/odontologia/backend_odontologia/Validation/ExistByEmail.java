package com.proyecto.odontologia.backend_odontologia.Validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ExistByEmailValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistByEmail {

    String message() default "Ya existe en la base de datos, verifique si ya está registrado con nosotros";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
