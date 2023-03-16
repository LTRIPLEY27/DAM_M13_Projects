package com.ioc.dam_final_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "id_tarea")
    private Tarea tarea;

    @ManyToOne
    @JoinColumn(name = "id_tecnico")
    private Tecnico tecnico;

    public Mensaje(String descripcion, Date fecha, Tarea tarea, Tecnico tecnico) {
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tarea = tarea;
        this.tecnico = tecnico;
    }
}
