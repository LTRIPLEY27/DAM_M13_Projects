package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.model.Coordenada;

import java.util.List;
import java.util.Optional;

public interface CoordenadaService {

    //Optional<Coordenada> saveObject(Long id, Coordenada coordenada);
    List<Coordenada> coordenas();
}
