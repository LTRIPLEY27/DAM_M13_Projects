package com.ioc.dam_final_project.dto;

import com.ioc.dam_final_project.model.Coordenada;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CoordenadaDTO {
    private Long id;
    private Double latitud;
    private Double longitud;

    public static CoordenadaDTO byModel(Coordenada coordenada){
        return new CoordenadaDTO(coordenada.getId(), coordenada.getLatitud(), coordenada.getLongitud());
    }
}
