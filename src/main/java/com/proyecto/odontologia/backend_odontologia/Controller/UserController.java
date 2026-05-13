package com.proyecto.odontologia.backend_odontologia.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto.odontologia.backend_odontologia.UserValidation;
import com.proyecto.odontologia.backend_odontologia.entities.User;
import com.proyecto.odontologia.backend_odontologia.entities.UserInfoDto;
import com.proyecto.odontologia.backend_odontologia.services.UserService;

import jakarta.validation.Valid;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${adminCode.user.adminCode}")
    private String adminCode;

    @Autowired
    private UserValidation userValidation;


    
    @GetMapping("/lista")
    public List<User> list(){
        return userService.findAll();
    }

    @GetMapping("/myInfo")
    public ResponseEntity<?> infoUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        
        UserInfoDto dto = new UserInfoDto(
            user.getName(),
            user.getLastname(),
            user.getPhone(),
            user.getEmail()
        ); 
        return ResponseEntity.ok(dto);
    }

    
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){
        
        userValidation.validate(user, result);

        if(result.hasFieldErrors()){
            return validation(result);
        }

        if(Objects.equals(user.getAdminCode(), adminCode)){
            user.setAdmin(true);
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
        }

        user.setAdmin(false);

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user){
        Optional<User> optionalUser = userService.update(id, user);
        if(optionalUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalUser.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        User user = new User();
        user.setId(id);
        Optional<User> optionalUser = userService.delete(id);

        if(optionalUser.isPresent()){
            return ResponseEntity.ok(optionalUser.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }


    private ResponseEntity<?> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();
        
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

    
}
