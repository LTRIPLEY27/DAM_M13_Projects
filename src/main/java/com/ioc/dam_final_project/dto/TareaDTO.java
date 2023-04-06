package com.ioc.dam_final_project.dto;

import com.ioc.dam_final_project.model.*;
import com.ioc.dam_final_project.model.Enums.Estatus;
import com.ioc.dam_final_project.model.Enums.Tipo_Tarea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.aspectj.weaver.UnresolvedType.add;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TareaDTO {

    private String name;
    private LocalDate fecha_creacion;
    private LocalDate fecha_culminacion;
    private Tipo_Tarea tarea;
    private Estatus estatus;
    private String tecnico;
    private String admin;
    private Object ubicacion;
    private Set<MensajeDTO> mensajes;

    public static TareaDTO byModel(Tarea tarea){
        Set<MensajeDTO> mensajes = new HashSet<>();

        tarea.getMensaje().forEach(mensaje -> mensajes.add(MensajeDTO.byModel(mensaje)));

        var ubicacion = tarea.getUbicacion() != null ? UbicacionDTO.byModel(tarea.getUbicacion()) : tarea.getUbicacion();
        return new TareaDTO(tarea.getName(), tarea.getFecha_creacion(), tarea.getFecha_culminacion(), tarea.getTarea(), tarea.getEstatus(), tarea.getTecnico().getUser(), tarea.getAdmin().getUser(), ubicacion, mensajes);
    }
}
