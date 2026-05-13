package com.proyecto.odontologia.backend_odontologia.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto.odontologia.backend_odontologia.entities.AgendaDto;
import com.proyecto.odontologia.backend_odontologia.entities.Cita;
import com.proyecto.odontologia.backend_odontologia.entities.User;
import com.proyecto.odontologia.backend_odontologia.entities.UserCita;
import com.proyecto.odontologia.backend_odontologia.entities.UserCitaReservaDTO;
import com.proyecto.odontologia.backend_odontologia.services.CitaService;
import com.proyecto.odontologia.backend_odontologia.services.UserCitaService;
import com.proyecto.odontologia.backend_odontologia.services.UserService;


@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/reserva")
public class UserCitaController {

    @Autowired
    private UserCitaService userCitaService;

    @Autowired
    private UserService userService;

    @Autowired
    private CitaService citaService;

    @GetMapping
    public List<UserCita> list(){
        return userCitaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id){

        Optional<UserCita> userCitaOptional = userCitaService.findById(id);

        if(userCitaOptional.isPresent()){
            return ResponseEntity.ok(userCitaOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createCita(@RequestBody UserCitaReservaDTO dto){

        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

            Cita cita = citaService.findById(dto.getCitaId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

            UserCita reserva = userCitaService.save(user, cita);
            return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
        } catch (ResponseStatusException  e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @GetMapping("/agenda")
    public ResponseEntity<?> agendaAdmin(){
        List<AgendaDto> agenda = userCitaService.findAgenda();
        return ResponseEntity.ok(agenda);
    }

}
