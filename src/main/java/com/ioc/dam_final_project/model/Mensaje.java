package com.ioc.dam_final_project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ioc.dam_final_project.dto.MensajeDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String descripcion;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "id_tarea")
    private Tarea tarea;

    @ManyToOne
    @JoinColumn(name = "id_tecnico")
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "id_admin")
    private Admin admin;

    public Mensaje(String descripcion, Tarea tarea, Tecnico tecnico, Admin admin) {
        this.descripcion = descripcion;
        this.tarea = tarea;
        this.tecnico = tecnico;
        this.admin = admin;
    }

    public static Mensaje byDTO(MensajeDTO mensajeDTO){
        return new Mensaje(mensajeDTO.getDescripcion(), null, null, null);
    }
}
