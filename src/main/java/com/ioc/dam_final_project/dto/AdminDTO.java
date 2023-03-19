package com.ioc.dam_final_project.dto;

import com.ioc.dam_final_project.model.Admin;
import com.ioc.dam_final_project.model.Enums.Rol;
import com.ioc.dam_final_project.model.Tarea;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {

    private String user;
    //private String password;
    private String nombre;
    private String apellido;
    private  String email;
    private  String telefono;
    private Rol rol;

    private List<Tarea> tarea;

    // SETTING VALUES FROM OBJETC FOR THE VALUES WE WANT SHOW ON JSON
    public static AdminDTO byModel(Admin admin){
        return  new AdminDTO(admin.getUser(),  admin.getNombre(), admin.getApellido(), admin.getEmail(), admin.getTelefono(), admin.getRol(), admin.getTareaList());
    }
}
