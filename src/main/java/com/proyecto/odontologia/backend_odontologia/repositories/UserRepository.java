package com.proyecto.odontologia.backend_odontologia.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.proyecto.odontologia.backend_odontologia.entities.User;

@CrossOrigin(origins = "http://localhost:5173")
@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository <User, Long>{

    boolean existsByEmail(String email);
}
