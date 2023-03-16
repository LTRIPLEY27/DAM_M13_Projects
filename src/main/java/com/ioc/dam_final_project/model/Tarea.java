package com.ioc.dam_final_project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ioc.dam_final_project.model.Enums.Estatus;
import com.ioc.dam_final_project.model.Enums.Tipo_Tarea;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_creacion;
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private Date fecha_culminacion;
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private Date fecha_asignacion = new Date();
    @Enumerated(EnumType.STRING)
    private Tipo_Tarea tarea;
    @Enumerated(EnumType.STRING)
    private Estatus estatus;

    // RELATIONSHIP N TAREAS TO 1 TECNICO
    @ManyToOne
    @JoinColumn(name = "tecnico")
    private Tecnico tecnico;

    // RELATIONSHIP N TAREAS TO 1 ADMIN
    @ManyToOne
    @JoinColumn(name = "admin")
    private Admin admin;

    // RELATIONSHIP 1 TO 1 WITH COORDENADAS
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ubicacion_id", referencedColumnName = "id")
    private Ubicacion ubicacion;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "id")
    private List<Mensaje> mensaje;

    public Tarea(String name, Tipo_Tarea tarea, Estatus estatus, Tecnico tecnico, Admin admin, Ubicacion ubicacion, List<Mensaje> mensaje) {
        this.name = name;
        this.tarea = tarea;
        this.estatus = estatus;
        this.tecnico = tecnico;
        this.admin = admin;
        this.ubicacion = ubicacion;
        this.mensaje = mensaje;
        this.fecha_creacion = LocalDate.now();
        this.fecha_culminacion = new Date();
        this.fecha_asignacion = new Date();
    }

}
