package com.ioc.dam_final_project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ioc.dam_final_project.dto.TareaDTO;
import com.ioc.dam_final_project.model.Tecnico;
import java.util.List;

/**
 * Interface TareaService
 *
 * SERA UNA INTERFACE PROTOCOLO A IMPLEMENTAR POR EL SERVICES, extiende de la clase JPARepository para realizar el puente entre la entidad y la base de datos.
 *  @author Isabel Calzadilla
 *  @version  1.0
 */
public interface TareaService {

    TareaDTO saveObject(String username, Long id, Object object);
    List<TareaDTO> total();
    List<TareaDTO> getTareaByTecnico(Tecnico tecnico);

    void deleteEntity(Long id);
    Object updateValue(Long id, Object object) throws JsonProcessingException;

    TareaDTO searchById(Long id);
}
