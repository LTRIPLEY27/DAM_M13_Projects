package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.dto.TecnicoDTO;
import com.ioc.dam_final_project.model.Tecnico;

import java.util.List;

/**
 * Interface TecnicoService
 *
 * SERA UNA INTERFACE PROTOCOLO A IMPLEMENTAR POR EL SERVICES, extiende de la clase JPARepository para realizar el puente entre la entidad y la base de datos.
 *  @author Isabel Calzadilla
 *  @version  1.0
 */
public interface TecnicoService {

    List <TecnicoDTO> getAll();

    TecnicoDTO myProfile(String tecnico);

    //TecnicoDTO update(Long id, Object object);

    void deleteEntity(Long id);

}
