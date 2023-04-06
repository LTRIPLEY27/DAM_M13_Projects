package com.ioc.dam_final_project.dto;

import com.ioc.dam_final_project.model.Enums.Rol;
import com.ioc.dam_final_project.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    //TODO Verificar si al realizar update : id y rol se pueden editar (en teoría no, pero comprobar)
    public static UserDTO byEntity(User user){
        return new UserDTO(user.getId(), user.getUser(), user.getPassword(), user.getNombre(), user.getApellido(), user.getEmail(), user.getTelefono(), user.getRol());
    }
}
