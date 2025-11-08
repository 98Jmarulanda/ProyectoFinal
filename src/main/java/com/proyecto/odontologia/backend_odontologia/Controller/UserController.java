package com.proyecto.odontologia.backend_odontologia.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.odontologia.backend_odontologia.UserValidation;
import com.proyecto.odontologia.backend_odontologia.entities.User;
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


    
    @GetMapping
    public List<User> list(){
        return userService.findAll();
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

    private ResponseEntity<?> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();
        
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }
}
