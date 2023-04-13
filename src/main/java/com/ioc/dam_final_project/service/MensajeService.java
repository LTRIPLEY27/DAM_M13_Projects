package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.dto.MensajeDTO;
import com.ioc.dam_final_project.dto.TareaDTO;

import java.util.List;

public interface MensajeService {

    void deleteEntity(Long id);

    MensajeDTO postMessage(String username, MensajeDTO mensaje);

    MensajeDTO searchById(Long id);

    MensajeDTO updateValue(Long id, Object object);

    List<MensajeDTO> getAll();
}
