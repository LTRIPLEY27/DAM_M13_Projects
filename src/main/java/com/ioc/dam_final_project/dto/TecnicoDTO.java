package com.ioc.dam_final_project.dto;

import com.ioc.dam_final_project.model.Enums.Rol;
import com.ioc.dam_final_project.model.Tecnico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TecnicoDTO {

    private String user;
   // private String password;
    private String nombre;
    private String apellido;
    private  String email;
    private  String telefono;
    private Rol rol;
    private List<TareaDTO> tarea; // POR VERIFIAR SI SOLO SE MUESTRA EL NOMBRE EN EL PERFIL O EL DETALLE DE TAREA

    public static TecnicoDTO byModel(Tecnico tecnico){
        List<TareaDTO> tareaDTOS = new ArrayList<>();
        for (var i : tecnico.getTareas()){
            tareaDTOS.add(TareaDTO.byModel(i));
        }
        return new TecnicoDTO(tecnico.getUser(), tecnico.getNombre(), tecnico.getApellido(), tecnico.getEmail(), tecnico.getTelefono(), tecnico.getRol(), tareaDTOS);
    }
}
