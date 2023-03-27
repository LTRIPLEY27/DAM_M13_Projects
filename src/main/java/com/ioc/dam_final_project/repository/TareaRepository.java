package com.ioc.dam_final_project.repository;

import com.ioc.dam_final_project.model.Tarea;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.model.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository <Tarea, Long> {

    List <Tarea> findTareaByTecnico(Tecnico tecnico);

    Tarea findTareaByTecnicoAndName(Tecnico tecnico, String name);

    Tarea findTareaByUbicacion(Ubicacion ubicacion);
}
