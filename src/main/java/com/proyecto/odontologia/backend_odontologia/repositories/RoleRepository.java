package com.proyecto.odontologia.backend_odontologia.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.odontologia.backend_odontologia.entities.Role;

public interface RoleRepository extends CrudRepository <Role, Long> {

    Optional<Role> findRoleByName(String name);
}
