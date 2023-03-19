package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.model.Mensaje;
import com.ioc.dam_final_project.model.Tarea;
import com.ioc.dam_final_project.model.Tecnico;

import java.util.List;

public interface TecnicoService {

    Tecnico saveObject(Tecnico tecnico);

    List <Tecnico> getAll();

    Tecnico updateObject(Long id,Tecnico tecnico) throws Exception;

    Tecnico getByEmail(String user);

    //Tecnico posting(Tecnico mensaje);

}
