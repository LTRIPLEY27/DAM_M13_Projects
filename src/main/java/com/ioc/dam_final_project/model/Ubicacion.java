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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double centroLatitud;
    private Double centroLongitud;
    private Double zoom;
    @JsonIgnore
    @OneToMany(mappedBy = "ubicacion", fetch = FetchType.LAZY)
    private List<Coordenada> mapa;
    @OneToOne(mappedBy = "ubicacion")
    private Tarea tarea;

    public Ubicacion(Double centroLatitud, Double centroLongitud, Double zoom, Tarea tarea) {
        this.centroLatitud = centroLatitud;
        this.centroLongitud = centroLongitud;
        this.zoom = zoom;
        this.tarea = tarea;
        setMapa(new ArrayList<>());
    }

    public static Ubicacion byDTO(UbicacionDTO ubicacionDTO){
        return new Ubicacion(ubicacionDTO.getCentroLatitud(), ubicacionDTO.getCentroLongitud(), ubicacionDTO.getZoom(), null);
    }

    // inicializamos el valor de la coordenada para que almacene de 1 a muchos valores
    public void setMapa(List <Coordenada> coordenadas){
        this.mapa = coordenadas;
    }
    public void addCoordenate(Coordenada coordenada){
        // verificacion de no repeticion de elementos
        if(mapa.contains(coordenada)){
            System.out.println("Esta coordenada ya se encuentra almacenada");
            return;
        }
        mapa.add(coordenada);
    }
}
