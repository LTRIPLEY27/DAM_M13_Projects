package com.ioc.dam_final_project.dto;

import com.ioc.dam_final_project.model.*;
import com.ioc.dam_final_project.model.Enums.Estatus;
import com.ioc.dam_final_project.model.Enums.Tipo_Tarea;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.aspectj.weaver.UnresolvedType.add;

@Setter
@Getter
//@NoArgsConstructor
@AllArgsConstructor
public class TareaDTO {
    private Long id;
    private String name;
    private LocalDate fecha_creacion;
    private LocalDate fecha_culminacion;
    private Tipo_Tarea tarea;
    private Estatus estatus;
    private String tecnico;
    private String admin;
    private Object ubicacion;
    private Set<MensajeDTO> mensajes;
    private Long ubicacionId;


    public static TareaDTO byModel(Tarea tarea){
        Set<MensajeDTO> mensajes = new HashSet<>();

        if(tarea.getMensaje() != null){
            tarea.getMensaje().forEach(mensaje -> mensajes.add(MensajeDTO.byModel(mensaje)));
        }

        // VALIDACIONES PARA CONSIDERAR OPCIONES EN NULL
        var ubicacion = tarea.getUbicacion() != null ? UbicacionDTO.byModel(tarea.getUbicacion()) : tarea.getUbicacion();
        var tecnic = tarea.getTecnico() != null ? tarea.getTecnico().getUser() : "";
        var admin = tarea.getAdmin() != null ? tarea.getAdmin().getUser() : "";

        return new TareaDTO(tarea.getId(), tarea.getName(), tarea.getFecha_creacion(), tarea.getFecha_culminacion(), tarea.getTarea(), tarea.getEstatus(), tecnic, admin, ubicacion, mensajes, null);
    }
}
