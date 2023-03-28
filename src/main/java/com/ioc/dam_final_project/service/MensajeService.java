package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.dto.MensajeDTO;

public interface MensajeService {

    void deleteEntity(Long id);

    MensajeDTO postMessage(String username, MensajeDTO mensaje);
}
