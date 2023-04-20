package com.ioc.dam_final_project.dto;

import com.ioc.dam_final_project.model.Mensaje;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * CLASE CoordenadaDTO
 *
 * SERA UNA CLASE DEL TIPO 'DATA TRANSFER OBJECT', ESTABLECERA LOS CAMPOS CUSTOMIZADOS A MANEJAR COMO RESPUESTA.
 *
 *   Notaciones:
 *
 *  - He declarado a la clase como 'Getter', 'Setter', 'AllArgsConstructor', 'NoArgsConstructor'  para su mappeo en la base de datos.
 *  - He usado las notaciones propias de SpringBoot, en combinacion a Java 17 y Loombook, para potenciar al maximo la codificacion.
 *
 *  @author Isabel Calzadilla
 *  @version 1.0
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MensajeDTO {
    private Long id;
    private String descripcion;
    private LocalDate fecha;
    private Long tarea;
    private String tecnico;
    private String admin;

    /**
     * Traspola los valores del objeto DAO a una vista JSON personalizada
     * @return <ul>
     *  <li>NormalDTO: la instancia con el formato JSON especifico</li>
     *  </ul>
     */
    public static MensajeDTO byModel(Mensaje mensaje){
        return new MensajeDTO(mensaje.getId(), mensaje.getDescripcion(), mensaje.getFecha(), mensaje.getTarea().getId(), mensaje.getTecnico().getUser(), mensaje.getAdmin().getUser());
    }
}
