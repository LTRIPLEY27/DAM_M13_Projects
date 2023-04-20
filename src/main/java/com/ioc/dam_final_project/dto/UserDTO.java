package com.ioc.dam_final_project.dto;

import com.ioc.dam_final_project.model.Enums.Rol;
import com.ioc.dam_final_project.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CLASE UserDTO
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
public class UserDTO {
    private Long id;
    private String user;
    private String password;
    private String nombre;
    private String apellido;
    private  String email;
    private  String telefono;
    private Rol rol;

    //Metodo publico estatico
    /**
     * Traspola los valores del objeto DAO a una vista JSON personalizada
     * @return <ul>
     *  <li>NormalDTO: la instancia con el formato JSON especifico</li>
     *  </ul>
     */
    public static UserDTO byEntity(User user){
        return new UserDTO(user.getId(), user.getUser(), user.getPassword(), user.getNombre(), user.getApellido(), user.getEmail(), user.getTelefono(), user.getRol());
    }
}
