package com.ioc.dam_final_project.dto;

import com.ioc.dam_final_project.model.Mensaje;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MensajeDTO {
    private String descripcion;
    private LocalDate fecha;
    private String tarea;
    private String tecnico;

    public static MensajeDTO byModel(Mensaje mensaje){
        return new MensajeDTO(mensaje.getDescripcion(), mensaje.getFecha(), mensaje.getTarea().getName(), mensaje.getTecnico().getUser());
    }
}
