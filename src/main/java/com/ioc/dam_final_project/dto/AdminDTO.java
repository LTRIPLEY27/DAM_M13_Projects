package com.ioc.dam_final_project.dto;

import com.ioc.dam_final_project.model.Admin;
import com.ioc.dam_final_project.model.Enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * CLASE AdminDTO
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
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {

    private Long id;
    private String user;
    //private String password;
    private String nombre;
    private String apellido;
    private  String email;
    private  String telefono;
    private Rol rol;
    private List<TareaDTO> tarea;

    // SETTING VALUES FROM OBJETC FOR THE VALUES WE WANT SHOW ON JSON
    //Metodo publico estatico
    /**
     * Traspola los valores del objeto DAO a una vista JSON personalizada
     * @return <ul>
     *  <li>AdminDTO: la instancia con el formato JSON especifico</li>
     *  </ul>
     */
    public static AdminDTO byModel(Admin admin){
        var tareaDTO = new ArrayList<TareaDTO>();
        admin.getTareaList().forEach(tarea1 -> tareaDTO.add(TareaDTO.byModel(tarea1)));

        return  new AdminDTO(admin.getId(), admin.getUser(),  admin.getNombre(), admin.getApellido(), admin.getEmail(), admin.getTelefono(), admin.getRol(), tareaDTO);
    }
}
