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
    private Long id;
    private String descripcion;
    private LocalDate fecha;
    private Long tarea;
    private String tecnico;
    private String admin;

    public static MensajeDTO byModel(Mensaje mensaje){
        return new MensajeDTO(mensaje.getId(), mensaje.getDescripcion(), mensaje.getFecha(), mensaje.getTarea().getId(), mensaje.getTecnico().getUser(), mensaje.getAdmin().getUser());
    }
}
