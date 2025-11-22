package com.proyecto.odontologia.backend_odontologia.services;

import java.util.List;


import com.proyecto.odontologia.backend_odontologia.entities.User;

public interface UserService {

    List<User> findAll();
    
    User save(User user);

    boolean existsByEmail(String email);
}
