package com.ioc.dam_final_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ioc.dam_final_project.dto.UbicacionDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * CLASE Ubicacion
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
 * - Motivado a que la clase Tarea, estara en relacion a otras entidades, he declarado los atributos con las notaciones necesarias para tal fin.
 *
 *   Metodos:
 *
 * - He declarado un metodo estatico de la clase, para hacer uso del mismo en la transposicion de los atributos al formato a mostrar al cliente
 *
 *  @author Isabel Calzadilla
 *  @version  1.0
 *  @see  Tecnico entidad relacional fuerte
 *  @see  Admin entidad relacional fuerte
 *  @see  Ubicacion entidad relacional fuerte
 *  @see  Mensaje entidad relacional fuerte
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ubicacion {

    /**
     * Id de Ubicacion
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * CentroLatitud de Ubicacion
     */
    private Double centroLatitud;
    /**
     * CentroLongitud de Ubicacion
     */
    private Double centroLongitud;
    /**
     * Zoom de Ubicacion
     */
    private Double zoom;
    /**
     * Mapa de Ubicacion
     */
    @JsonIgnore
    @OneToMany(mappedBy = "ubicacion", fetch = FetchType.LAZY, cascade = CascadeType.ALL) //(orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Coordenada> mapa;
    /**
     * Tarea de Ubicacion
     */
    @OneToOne
    @JoinColumn(name = "tarea_id", referencedColumnName = "id")
    private Tarea tarea;

    /**
     * Constructor con 4 parametros
     * @param centroLatitud nombre del usuario
     * @param centroLongitud nombre del usuario
     * @param zoom edad del usuario
     * @param tarea del usuario
     */
    public Ubicacion(Double centroLatitud, Double centroLongitud, Double zoom, Tarea tarea) {
        this.centroLatitud = centroLatitud;
        this.centroLongitud = centroLongitud;
        this.zoom = zoom;
        this.tarea = tarea;
        setMapa(new ArrayList<>());
    }

    //Metodo publico estatico
    /**
     * Traspola los valores del objeto DAO a una vista JSON personalizada
     * @return <ul>
     *  <li>User: la instancia con el formato JSON especifico</li>
     *  </ul>
     */
    public static Ubicacion byDTO(UbicacionDTO ubicacionDTO){
        return new Ubicacion(ubicacionDTO.getCentroLatitud(), ubicacionDTO.getCentroLongitud(), ubicacionDTO.getZoom(), null);
    }

    // inicializamos el valor de la coordenada para que almacene de 1 a muchos valores
    /**
     * Metodo Set de atributo Mapa
     */
    public void setMapa(List <Coordenada> coordenadas){
        this.mapa = coordenadas;
    }

    /**
     * Metodo AddCoordenate
     * @param coordenada del usuario
     * Realiza la adicion de coordenadas en el Array de Objetos definido y valida si ya hay registro de alguna.
     */
    public void addCoordenate(Coordenada coordenada){
        // verificacion de no repeticion de elementos
        if(mapa.contains(coordenada)){
            System.out.println("Esta coordenada ya se encuentra almacenada");
            return;
        }
        mapa.add(coordenada);
    }
}
