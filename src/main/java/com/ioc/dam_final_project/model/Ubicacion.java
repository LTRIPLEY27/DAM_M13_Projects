package com.ioc.dam_final_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private List<Coordenadas> mapa;

    @OneToOne(mappedBy = "ubicacion")
    private Tarea tarea;

}
