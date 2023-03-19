package com.ioc.dam_final_project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ioc.dam_final_project.model.*;
import com.ioc.dam_final_project.model.Enums.Estatus;
import com.ioc.dam_final_project.model.Enums.Tipo_Tarea;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TareaDTO {

    private String name;
    private LocalDate fecha_creacion;
    private Date fecha_culminacion;
    private Date fecha_asignacion;
    private Tipo_Tarea tarea;
    private Estatus estatus;
    private String tecnico;
    private String admin;
    private UbicacionDTO ubicacion;
    private Set<MensajeDTO> mensajes;

    public static TareaDTO byModel(Tarea tarea){
        Set<MensajeDTO> mensajes = new HashSet<>();

        for (var i : tarea.getMensaje()){
            mensajes.add(MensajeDTO.byModel(i));
        }

        var ubicacion = UbicacionDTO.byModel(tarea.getUbicacion());
        return new TareaDTO(tarea.getName(), tarea.getFecha_creacion(), tarea.getFecha_culminacion(), tarea.getFecha_asignacion(), tarea.getTarea(), tarea.getEstatus(), tarea.getTecnico().getUser(), tarea.getAdmin().getUser(), ubicacion, mensajes);
    }
}
