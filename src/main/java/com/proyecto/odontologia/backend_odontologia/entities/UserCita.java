package com.proyecto.odontologia.backend_odontologia.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "user_cita",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "cita_id"})
    })
public class UserCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties({"userCitas"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "cita_id")
    @JsonIgnoreProperties({"userCita"})
    private Cita cita;

    

    public UserCita() {
    }

    public UserCita(User user, Cita cita) {
        this.user = user;
        this.cita = cita;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    

}
