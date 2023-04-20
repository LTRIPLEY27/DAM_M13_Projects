package com.ioc.dam_final_project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ioc.dam_final_project.dto.TareaDTO;
import com.ioc.dam_final_project.model.Tarea;
import com.ioc.dam_final_project.model.Tecnico;

import java.util.List;

public interface TareaService {

    TareaDTO saveObject(String username, Long id, Object object);
    List<TareaDTO> total();
    List<TareaDTO> getTareaByTecnico(Tecnico tecnico);

    void deleteEntity(Long id);
    Object updateValue(Long id, Object object) throws JsonProcessingException;

    TareaDTO searchById(Long id);
}
