package com.proyecto.odontologia.backend_odontologia.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.odontologia.backend_odontologia.entities.Role;
import com.proyecto.odontologia.backend_odontologia.entities.User;
import com.proyecto.odontologia.backend_odontologia.repositories.RoleRepository;
import com.proyecto.odontologia.backend_odontologia.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {

        Optional<Role> optionalRoleUser = roleRepository.findRoleByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();

        optionalRoleUser.ifPresent(role -> roles.add(role));

        if(user.isAdmin()){

            Optional<Role> optionalRoleAdmin = roleRepository.findRoleByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(role -> roles.add(role));
        }

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {

        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public Optional<User> update(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User userDb = optionalUser.orElseThrow();
            userDb.setName(user.getName());
            userDb.setLastname(user.getLastname());
            userDb.setPhone(user.getPhone());
            userDb.setEmail(user.getEmail());
            return Optional.of(userRepository.save(userDb));
        }
        return optionalUser;

    }

    @Override
    @Transactional
    public Optional<User> delete(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        userOptional.ifPresent(userDB -> {
            userRepository.delete(userDB);
        });
        return userOptional;
    }

}
