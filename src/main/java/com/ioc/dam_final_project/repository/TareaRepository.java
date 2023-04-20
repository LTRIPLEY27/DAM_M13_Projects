package com.ioc.dam_final_project.repository;

import com.ioc.dam_final_project.model.Admin;
import com.ioc.dam_final_project.model.Tarea;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.model.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface TareaRepository
 *
 * SERA UN REPOSITORIO DE LA CLASE ADMIN, extiende de la clase JPARepository para realizar el puente entre la entidad y la base de datos.
 *
 *   Notaciones:
 *
 *   - He declarado a la clase como 'Repository' para su mappeo en la base de datos.
 *   - He usado las notaciones propias de SpringBoot, en combinacion a Java 17 y Loombook, para potenciar al maximo la codificacion.

 *  @author Isabel Calzadilla
 *  @version  1.0
 */
@Repository
public interface TareaRepository extends JpaRepository <Tarea, Long> {

    List <Tarea> findTareaByTecnico(Tecnico tecnico);
    Tarea findTareaByTecnicoAndId(Tecnico tecnico, Long id);

    Tarea findTareaByUbicacion(Ubicacion ubicacion);
    Optional <Tarea> findTareaByUbicacion_Id(Long id);
    Tarea findTareaByAdminAndId(Admin admin, Long name);
}
