package com.ioc.dam_final_project.repository;

import com.ioc.dam_final_project.model.Admin;
import com.ioc.dam_final_project.model.Tarea;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.model.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TareaRepository extends JpaRepository <Tarea, Long> {

    List <Tarea> findTareaByTecnico(Tecnico tecnico);
    Tarea findTareaByTecnicoAndId(Tecnico tecnico, Long id);

    Tarea findTareaByUbicacion(Ubicacion ubicacion);
    Optional <Tarea> findTareaByUbicacion_Id(Long id);
    Tarea findTareaByAdminAndId(Admin admin, Long name);
}
