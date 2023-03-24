package com.ioc.dam_final_project.dto;

import com.ioc.dam_final_project.model.Admin;
import com.ioc.dam_final_project.model.Enums.Rol;
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
    private List<TareaDTO> tarea;

    // SETTING VALUES FROM OBJETC FOR THE VALUES WE WANT SHOW ON JSON
    public static AdminDTO byModel(Admin admin){
        var tadeaDTO = new ArrayList<TareaDTO>();
        for(var i : admin.getTareaList()){
            tadeaDTO.add(TareaDTO.byModel(i));
        }

        return  new AdminDTO(admin.getUser(),  admin.getNombre(), admin.getApellido(), admin.getEmail(), admin.getTelefono(), admin.getRol(), tadeaDTO);
    }
}
