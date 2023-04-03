package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.dto.TecnicoDTO;
import com.ioc.dam_final_project.model.Tecnico;

import java.util.List;

public interface TecnicoService {

    Tecnico saveObject(Tecnico tecnico);

    List <TecnicoDTO> getAll();

    Tecnico getByEmail(String user);

    TecnicoDTO myProfile(String tecnico);

    //TecnicoDTO update(Long id, Object object);

    void deleteEntity(Long id);

}
