package com.ioc.dam_final_project.model;

import com.ioc.dam_final_project.dto.CoordenadaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coordenada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double latitud;
    private Double longitud;

    // relationshiop 1 UBICACION == N COORDENADAS
    @ManyToOne
    @JoinColumn(name = "ubicacion")
    private Ubicacion ubicacion;

    public Coordenada(Double latitud, Double longitud, Ubicacion ubicacion) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.ubicacion = ubicacion;
    }

    public static Coordenada byDTO(CoordenadaDTO coordenadaDTO){
        return new Coordenada(coordenadaDTO.getId(), coordenadaDTO.getLatitud(), coordenadaDTO.getLongitud(), null);
    }
}
