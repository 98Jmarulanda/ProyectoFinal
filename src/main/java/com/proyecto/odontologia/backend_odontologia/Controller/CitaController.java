package com.proyecto.odontologia.backend_odontologia.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.proyecto.odontologia.backend_odontologia.CitaValidation;
import com.proyecto.odontologia.backend_odontologia.entities.Cita;
import com.proyecto.odontologia.backend_odontologia.services.CitaService;

import jakarta.validation.Valid;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private CitaValidation citaValidation;


    @GetMapping
    public List<Cita> list(){
        return citaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id){

        Optional<Cita> optionalCita = citaService.findById(id);
        
        if(optionalCita.isPresent()){
            return ResponseEntity.ok(optionalCita.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Cita cita, BindingResult result){

        citaValidation.validate(cita, result);

        if(result.hasFieldErrors()){
            return validation(result);
        }
        
        Cita citaNew = citaService.save(cita);
        return ResponseEntity.status(HttpStatus.CREATED).body(citaNew);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> update(@PathVariable Long id, @RequestBody Cita cita){
        Optional<Cita> optionalCita = citaService.update(id, cita);
        if(optionalCita.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalCita.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        Cita cita = new Cita();
        cita.setId(id);
        Optional<Cita> optionalCita = citaService.delete(id);
        
        if(optionalCita.isPresent()){
            return ResponseEntity.ok(optionalCita.orElseThrow());
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
