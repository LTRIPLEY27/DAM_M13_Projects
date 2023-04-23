package com.ioc.dam_final_project.dto;

import com.ioc.dam_final_project.model.Ubicacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * CLASE UbicacionDTO
 *
 * SERA UNA CLASE DEL TIPO 'DATA TRANSFER OBJECT', ESTABLECERA LOS CAMPOS CUSTOMIZADOS A MANEJAR COMO RESPUESTA.
 *
 *   Notaciones:
 *
 *  - He declarado a la clase como 'Getter', 'Setter', 'AllArgsConstructor', 'NoArgsConstructor'  para su mappeo en la base de datos.
 *  - He usado las notaciones propias de SpringBoot, en combinacion a Java 17 y Loombook, para potenciar al maximo la codificacion.
 *
 *  @author Isabel Calzadilla
 *  @version 1.0
 * */
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

    /**
     * Traspola los valores del objeto DAO a una vista JSON personalizada
     * @return <ul>
     *  <li>NormalDTO: la instancia con el formato JSON especifico</li>
     *  </ul>
     */
    public static UbicacionDTO byModel(Ubicacion ubicacion){
        var mapas = new ArrayList<CoordenadaDTO>();
        if(ubicacion.getMapa() != null){
            ubicacion.getMapa().forEach(mapa -> mapas.add(CoordenadaDTO.byModel(mapa)));
        }

        return new UbicacionDTO(ubicacion.getId(), ubicacion.getCentroLatitud(), ubicacion.getCentroLongitud(), ubicacion.getZoom(), mapas, ubicacion.getTarea().getId());
    }

}
