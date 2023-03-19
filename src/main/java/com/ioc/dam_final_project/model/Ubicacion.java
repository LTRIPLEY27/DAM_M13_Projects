package com.ioc.dam_final_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Double centro;
    private Double zoom;
    @JsonIgnore
    @OneToMany(mappedBy = "ubicacion", fetch = FetchType.LAZY)
    private List<Coordenada> mapa;

    @JsonIgnore
    @OneToOne(mappedBy = "ubicacion")
    private Tarea tarea;

    public Ubicacion(Double centro, Double zoom,  Tarea tarea) {
        this.centro = centro;
        this.zoom = zoom;
        this.tarea = tarea;
        setMapa(new ArrayList<>());
    }

    // inicializamos el valor de la coordenada para que almacene de 1 a muchos valores
    public void setMapa(List <Coordenada> coordenadas){
        this.mapa = coordenadas;
    }
    public void addCoordenate(Coordenada coordenada){
        // verificación de no repetición de elementos
        if(mapa.contains(coordenada)){
            System.out.println("Ésta coordenada ya se encuentra almacenada");
            return;
        }
        mapa.add(coordenada);
    }
}
