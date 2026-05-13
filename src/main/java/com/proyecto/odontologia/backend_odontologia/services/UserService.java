package com.proyecto.odontologia.backend_odontologia.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.odontologia.backend_odontologia.entities.User;

public interface UserService {

    List<User> findAll();
    
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> update(Long id, User user);

    Optional<User> delete (Long id);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
