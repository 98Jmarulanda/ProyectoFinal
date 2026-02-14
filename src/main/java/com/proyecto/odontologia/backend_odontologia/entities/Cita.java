package com.proyecto.odontologia.backend_odontologia.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String name;

    @NotNull
    private Integer price;

    @NotBlank(message = "La descripcion no puede estar vacio")
    private String description;

    private LocalDateTime dateTime;

    // @ManyToOne
    // @JoinColumn(name = "user_id")
    // private User user;

    

    // public Cita(@NotBlank(message = "El nombre no puede estar vacio") String name, @NotNull Integer price,
    //         @NotBlank(message = "La descripcion no puede estar vacio") String description, LocalDateTime dateTime) {
    //     this.name = name;
    //     this.price = price;
    //     this.description = description;
    //     this.dateTime = dateTime;
    // }

    // public Cita() {
    // }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    // public User getUser() {
    //     return user;
    // }

    // public void setUser(User user) {
    //     this.user = user;
    // }
    
    

    
}
