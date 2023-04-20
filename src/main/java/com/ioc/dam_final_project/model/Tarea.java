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

/**
 * CLASE Tarea
 *
 *
 *   Notaciones:
 *
 *   - He declarado a la clase como 'Entity' para su mappeo en la base de datos.
 *   - He usado las notaciones propias de SpringBoot, en combinacion a Java 17 y Loombook, para potenciar al maximo la codificacion.
 *
 *   Atributos:
 *
 * - He declarado los atributos: Private, ya que no seran heredados.
 * - Motivado a que la clase Tarea, estara en relacion a otras entidades, he declarado los atributos con las notaciones necesarias para tal fin.
 *
 *   Metodos:
 *
 * - He declarado un metodo estatico de la clase, para hacer uso del mismo en la transposicion de los atributos al formato a mostrar al cliente
 *
 *  @author Isabel Calzadilla
 *  @version  1.0
 *  @see  Tecnico entidad relacional fuerte
 *  @see  Admin entidad relacional fuerte
 *  @see  Ubicacion entidad relacional fuerte
 *  @see  Mensaje entidad relacional fuerte
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tarea {
    /**
     * Id de Tarea
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Name de Tarea
     */
    @NotNull
    private String name;
    /**
     * Fecha de Creacion de Tarea
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_creacion = LocalDate.now();
    /**
     * Fecha de Culminacion de Tarea
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_culminacion;
    /**
     * Tipo de Tarea
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private Tipo_Tarea tarea;

    /**
     * Estatus de Tarea
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private Estatus estatus;

    // RELATIONSHIP N TAREAS TO 1 TECNICO
    /**
     * Tecnico de Tarea
     */
    @ManyToOne
    @JoinColumn(name = "tecnico")
    private Tecnico tecnico;

    // RELATIONSHIP N TAREAS TO 1 ADMIN
    /**
     * Admin de Culminacion de Tarea
     */
    @ManyToOne
    @JoinColumn(name = "admin")
    private Admin admin;

    // RELATIONSHIP 1 TO 1 WITH COORDENADAS
    /**
     * Ubicacion de Tarea
     */
    @JsonIgnore
    @OneToOne(mappedBy = "tarea", cascade = CascadeType.ALL)
    private Ubicacion ubicacion;

    /**
     * Mensaje de Tarea
     */
    @JsonIgnore
    @OneToMany(mappedBy = "tarea", cascade = CascadeType.REMOVE)
    private Set <Mensaje> mensaje;

    /**
     * Constructor con 8 parametros
     * @param name nombre de Tarea
     * @param fecha_culminacion fecha culminacion de Tarea
     * @param tarea tipo de Tarea
     * @param estatus estatus de Tarea
     * @param tecnico tecnico de Tarea
     * @param admin admin de Tarea
     * @param ubicacion ubicacion de Tarea
     * @param mensaje mensaje  de Tarea
     */
    public Tarea(@NotNull String name, LocalDate fecha_culminacion, @NotNull Tipo_Tarea tarea, @NotNull Estatus estatus, Tecnico tecnico, Admin admin, Ubicacion ubicacion, Set<Mensaje> mensaje) {
        this.name = name;
        this.fecha_culminacion = fecha_culminacion;
        this.tarea = tarea;
        this.estatus = estatus;
        this.tecnico = tecnico;
        this.admin = admin;
        this.ubicacion = ubicacion;
        this.mensaje = mensaje;
    }

    //Metodo publico estatico
    /**
     * Traspola los valores del objeto DAO a una vista JSON personalizada
     * @return <ul>
     *  <li>User: la instancia con el formato JSON especifico</li>
     *  </ul>
     */
    public static Tarea byDTO(TareaDTO tareaDTO){
        return new Tarea(tareaDTO.getName(), tareaDTO.getFecha_culminacion(), tareaDTO.getTarea(), tareaDTO.getEstatus(), null, null, new Ubicacion(), null);
    }

}
