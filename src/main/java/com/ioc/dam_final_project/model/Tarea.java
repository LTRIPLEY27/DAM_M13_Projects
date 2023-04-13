package com.ioc.dam_final_project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ioc.dam_final_project.dto.TareaDTO;
import com.ioc.dam_final_project.model.Enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_creacion = LocalDate.now();
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_culminacion;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Tipo_Tarea tarea;
    @NotNull
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
    //
    @JsonIgnore
    @OneToOne(mappedBy = "tarea", cascade = CascadeType.ALL)
    private Ubicacion ubicacion;

    @JsonIgnore
    @OneToMany(mappedBy = "tarea", cascade = CascadeType.REMOVE)
    private Set <Mensaje> mensaje;

    public Tarea(String name, Tipo_Tarea tarea, Estatus estatus, Tecnico tecnico, Admin admin, Ubicacion ubicacion, Set<Mensaje> mensaje) {
        this.name = name;
        this.tarea = tarea;
        this.estatus = estatus;
        this.tecnico = tecnico;
        this.admin = admin;
        this.ubicacion = ubicacion;
        this.mensaje = mensaje;
    }


    public static Tarea byDTO(TareaDTO tareaDTO){
        return new Tarea(tareaDTO.getName(), tareaDTO.getTarea(), tareaDTO.getEstatus(), null, null, new Ubicacion(), null);
    }

}
