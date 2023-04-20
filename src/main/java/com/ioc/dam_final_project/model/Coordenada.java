package com.ioc.dam_final_project.model;

import com.ioc.dam_final_project.dto.CoordenadaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CLASE Coordenada
 *
 *
 *   Notaciones:
 *
 *   - He declarado a la clase como 'Entity' para su mappeo en la base de datos.
 *   - He usado las notaciones propias de SpringBoot, en combinacion a Java 17 y Loombook, para potenciar al maximo la codificacion.
 *
 *   Atributos:
 *
 * - He declarado los atributos: Private, ya que no seran heredados.
 * - Motivado a que la clase Coordenada, estara en relacion a otras entidades, he declarado los atributos con las notaciones necesarias para tal fin.
 *
 *   Metodos:
 *
 * - He declarado un metodo estatico de la clase, para hacer uso del mismo en la transposicion de los atributos al formato a mostrar al cliente
 *
 *  @author Isabel Calzadilla
 *  @version  1.0
 *  @see  Ubicacion entidad relacional fuerte
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coordenada {
    /**
     * Id de la Coordenada
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Latitud de la Coordenada
     */
    private Double latitud;
    /**
     * Longitud de la Coordenada
     */
    private Double longitud;

    // relationshiop 1 UBICACION == N COORDENADAS
    /**
     * Ubicacion especifica de la Coordenada
     */
    @ManyToOne
    @JoinColumn(name = "ubicacion")
    private Ubicacion ubicacion;

    /**
     * Constructor con 3 parametros
     * @param latitud indicacion de la coordenada especifica de Clase
     * @param longitud indicacion de la coordenada especifica de Clase
     * @param ubicacion en referencia a la Entidad relacional con la que se mantiene como debil
     */
    public Coordenada(Double latitud, Double longitud, Ubicacion ubicacion) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.ubicacion = ubicacion;
    }

    //Metodo publico estatico
    /**
     * Traspola los valores del objeto DAO a una vista JSON personalizada
     * @return <ul>
     *  <li>Admin: la instancia con el formato JSON especifico</li>
     *  </ul>
     */
    public static Coordenada byDTO(CoordenadaDTO coordenadaDTO){
        return new Coordenada(coordenadaDTO.getId(), coordenadaDTO.getLatitud(), coordenadaDTO.getLongitud(), null);
    }
}
