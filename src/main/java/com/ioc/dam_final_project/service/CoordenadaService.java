package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.dto.CoordenadaDTO;
import com.ioc.dam_final_project.model.Coordenada;

import java.util.List;
import java.util.Optional;

public interface CoordenadaService {

    //Optional<Coordenada> saveObject(Long id, Coordenada coordenada);
    List<CoordenadaDTO> coordenas();
    void deleteEntity(Long id);

    CoordenadaDTO searchById(Long id);
}
