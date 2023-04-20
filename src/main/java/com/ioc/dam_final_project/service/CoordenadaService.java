package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.dto.CoordenadaDTO;
import com.ioc.dam_final_project.dto.TareaDTO;
import com.ioc.dam_final_project.model.Coordenada;

import java.util.List;
import java.util.Optional;

/**
 * Interface CoordenadaService
 *
 * SERA UNA INTERFACE PROTOCOLO A IMPLEMENTAR POR EL SERVICES, extiende de la clase JPARepository para realizar el puente entre la entidad y la base de datos.
 *  @author Isabel Calzadilla
 *  @version  1.0
 */
public interface CoordenadaService {

    //Optional<Coordenada> saveObject(Long id, Coordenada coordenada);
    List<CoordenadaDTO> coordenas();
    void deleteEntity(Long id);

    CoordenadaDTO searchById(Long id);

    CoordenadaDTO updateValue(Long id, Object object);
    CoordenadaDTO saveObject(Coordenada coordenada, Long ubicacion);
}
