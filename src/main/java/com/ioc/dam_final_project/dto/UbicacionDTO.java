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

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionDTO {

    private Double centroLatitud;
    private Double centroLongitud;
    private Double zoom;
    private List<Coordenada> mapa;
    private String tarea;

    public static UbicacionDTO byModel(Ubicacion ubicacion){
        return new UbicacionDTO(ubicacion.getCentroLatitud(), ubicacion.getCentroLongitud(), ubicacion.getZoom(), ubicacion.getMapa(), ubicacion.getTarea().getName());
    }

}
