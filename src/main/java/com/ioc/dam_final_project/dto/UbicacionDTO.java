package com.ioc.dam_final_project.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ioc.dam_final_project.model.Coordenada;
import com.ioc.dam_final_project.model.Tarea;
import com.ioc.dam_final_project.model.Ubicacion;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class UbicacionDTO {

    private Long id;
    private Double centroLatitud;
    private Double centroLongitud;
    private Double zoom;
    private List<CoordenadaDTO> mapa;
    private Long tarea;

    public static UbicacionDTO byModel(Ubicacion ubicacion){
        var mapas = new ArrayList<CoordenadaDTO>();
        ubicacion.getMapa().forEach(mapa -> mapas.add(CoordenadaDTO.byModel(mapa)));

        return new UbicacionDTO(ubicacion.getId(), ubicacion.getCentroLatitud(), ubicacion.getCentroLongitud(), ubicacion.getZoom(), mapas, ubicacion.getTarea().getId());
    }

}
