package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.dto.MensajeDTO;
import com.ioc.dam_final_project.dto.TareaDTO;
import com.ioc.dam_final_project.model.Admin;
import com.ioc.dam_final_project.model.Tecnico;

import java.util.List;

/**
 * Interface MensajeService
 *
 * SERA UNA INTERFACE PROTOCOLO A IMPLEMENTAR POR EL SERVICES, extiende de la clase JPARepository para realizar el puente entre la entidad y la base de datos.
 *  @author Isabel Calzadilla
 *  @version  1.0
 */
public interface MensajeService {

    void deleteEntity(Long id);

    MensajeDTO postMessage(String username, MensajeDTO mensaje);

    MensajeDTO searchById(Long id);

    MensajeDTO updateValue(Long id, Object object);

    List<MensajeDTO> findMessageByTecnic(Tecnico tecnico);
    List<MensajeDTO> findMessageByAdmin(Admin admin);

    List<MensajeDTO> getAll();
}
