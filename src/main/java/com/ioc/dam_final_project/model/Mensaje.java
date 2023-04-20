package com.ioc.dam_final_project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ioc.dam_final_project.dto.MensajeDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * CLASE Mensaje
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
 * - Motivado a que la clase Mensaje, estara en relacion a otras entidades, he declarado los atributos con las notaciones necesarias para tal fin.
 *
 *   Metodos:
 *
 * - He declarado un metodo estatico de la clase, para hacer uso del mismo en la transposicion de los atributos al formato a mostrar al cliente
 *
 *  @author Isabel Calzadilla
 *  @version  1.0
 *  @see  Tarea entidad relacional fuerte
 *  @see  Tecnico entidad relacional fuerte
 *  @see  Admin entidad relacional fuerte
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mensaje {

    /**
     * Id de Mensaje
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    /**
     * Descripcion de Mensaje
     */
    private String descripcion;

    /**
     * Fecha de Mensaje
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha = LocalDate.now();

    /**
     * Tarea a la cual refiere el Mensaje
     */
    @ManyToOne
    @JoinColumn(name = "id_tarea")
    private Tarea tarea;

    /**
     * Tecnico al cual refiere el Mensaje
     */
    @ManyToOne
    @JoinColumn(name = "id_tecnico")
    private Tecnico tecnico;

    /**
     * Admin al cual refiere el Mensaje
     */
    @ManyToOne
    @JoinColumn(name = "id_admin")
    private Admin admin;

    /**
     * Constructor con 4 parametros
     * @param descripcion nombre del usuario
     * @param tarea nombre del usuario
     * @param tecnico edad del usuario
     * @param admin del usuario
     */
    public Mensaje(String descripcion, Tarea tarea, Tecnico tecnico, Admin admin) {
        this.descripcion = descripcion;
        this.tarea = tarea;
        this.tecnico = tecnico;
        this.admin = admin;
    }

    //Metodo publico estatico
    /**
     * Traspola los valores del objeto DAO a una vista JSON personalizada
     * @return <ul>
     *  <li>User: la instancia con el formato JSON especifico</li>
     *  </ul>
     */
    public static Mensaje byDTO(MensajeDTO mensajeDTO){
        return new Mensaje(mensajeDTO.getDescripcion(), null, null, null);
    }
}
