package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.dto.MensajeDTO;
import com.ioc.dam_final_project.dto.TareaDTO;
import com.ioc.dam_final_project.model.Admin;
import com.ioc.dam_final_project.model.Tecnico;

import java.util.List;

public interface MensajeService {

    void deleteEntity(Long id);

    MensajeDTO postMessage(String username, MensajeDTO mensaje);

    MensajeDTO searchById(Long id);

    MensajeDTO updateValue(Long id, Object object);

    List<MensajeDTO> findMessageByTecnic(Tecnico tecnico);
    List<MensajeDTO> findMessageByAdmin(Admin admin);

    List<MensajeDTO> getAll();
}
