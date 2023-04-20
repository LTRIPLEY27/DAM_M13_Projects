package com.ioc.dam_final_project.dto;

import com.ioc.dam_final_project.model.Enums.Rol;
import com.ioc.dam_final_project.model.Tecnico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * CLASE TecnicoDTO
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
public class TecnicoDTO {

    private Long id;
    private String user;
    private String nombre;
    private String apellido;
    private  String email;
    private  String telefono;
    private Rol rol;
    private List<TareaDTO> tarea; // POR VERIFIAR SI SOLO SE MUESTRA EL NOMBRE EN EL PERFIL O EL DETALLE DE TAREA

    //Metodo publico estatico
    /**
     * Traspola los valores del objeto DAO a una vista JSON personalizada
     * @return <ul>
     *  <li>NormalDTO: la instancia con el formato JSON especifico</li>
     *  </ul>
     */
    public static TecnicoDTO byModel(Tecnico tecnico){
        List<TareaDTO> tareaDTOS = new ArrayList<>();
        tecnico.getTareas().forEach(tarea1 -> tareaDTOS.add(TareaDTO.byModel(tarea1)));

        return new TecnicoDTO(tecnico.getId(), tecnico.getUser(), tecnico.getNombre(), tecnico.getApellido(), tecnico.getEmail(), tecnico.getTelefono(), tecnico.getRol(), tareaDTOS);
    }
}
