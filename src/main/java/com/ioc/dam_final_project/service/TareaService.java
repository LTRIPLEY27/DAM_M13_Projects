package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.dto.TareaDTO;
import com.ioc.dam_final_project.model.Tarea;
import com.ioc.dam_final_project.model.Tecnico;

import java.util.List;

public interface TareaService {

    Tarea saveObject(String username, int id, Object object);
    List<TareaDTO> total();
    List<TareaDTO> getTareaTec(Tecnico tecnico);

    void deleteEntity(Long id);
}
