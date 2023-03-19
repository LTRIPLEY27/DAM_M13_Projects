package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.model.Mensaje;
import com.ioc.dam_final_project.model.Tarea;
import com.ioc.dam_final_project.model.Tecnico;

import java.util.List;
import java.util.Set;

public interface TareaService {

    Tarea saveObject(Tarea tarea);
    List<Tarea> total();
    List<Tarea> getTareaTec(Tecnico tecnico);

}
