package com.ioc.dam_final_project.dto;

import com.ioc.dam_final_project.model.Coordenada;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CLASE CoordenadaDTO
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
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CoordenadaDTO {
    private Long id;
    private Double latitud;
    private Double longitud;

    //Metodo publico estatico
    /**
     * Traspola los valores del objeto DAO a una vista JSON personalizada
     * @return <ul>
     *  <li>NormalDTO: la instancia con el formato JSON especifico</li>
     *  </ul>
     */
    public static CoordenadaDTO byModel(Coordenada coordenada){
        return new CoordenadaDTO(coordenada.getId(), coordenada.getLatitud(), coordenada.getLongitud());
    }
}
