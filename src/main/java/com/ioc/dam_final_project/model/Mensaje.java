package com.ioc.dam_final_project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ioc.dam_final_project.dto.MensajeDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

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

    public Mensaje(String descripcion, Tarea tarea, Tecnico tecnico) {
        this.descripcion = descripcion;
        this.tarea = tarea;
        this.tecnico = tecnico;
    }

    public static Mensaje byDTO(MensajeDTO mensajeDTO){
        return new Mensaje(mensajeDTO.getDescripcion(), null, null);
    }
}
