package com.ioc.dam_final_project.repository;

import com.ioc.dam_final_project.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
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
    List <Tarea> findTareaByAdmin(Admin admin);
    Tarea findTareaByTecnicoAndId(Tecnico tecnico, Long id);

    Tarea findTareaByUbicacion(Ubicacion ubicacion);
    Optional <Tarea> findTareaByUbicacion_Id(Long id);
    Tarea findTareaByAdminAndId(Admin admin, Long name);

    /**
     * QUERY'S CON FILTROS ESPECIFICOS
     */


    @Query(value = "SELECT  *\n" +
                        "FROM tarea\n" +
                        "WHERE fecha_creacion BETWEEN :date1 AND :date2\n" +
                        "ORDER BY tarea.id DESC ;", nativeQuery = true)
    List <Tarea> filterByCreationDateRange(@PathVariable("date1") String date1, @PathVariable("date2") String date2);


    @Query(value = "SELECT  *\n" +
                        "FROM tarea\n" +
                        "WHERE fecha_culminacion BETWEEN :date1 AND :date2\n" +
                        "ORDER BY tarea.id DESC ;", nativeQuery = true)
    List <Tarea> filterByEndingDateRange(@PathVariable("date1") String date1, @PathVariable("date2") String date2);


   //cantidad de tareas por estatus, y agrupadas por técnico (admin)
    @Query(value = "SELECT B.nombre AS 'Técnico',  CAST(COUNT(*) AS NCHAR) AS 'Cantidad', A.estatus As 'Estatus'\n" +
                        "FROM tarea A\n" +
                        "JOIN user B on B.id = A.tecnico\n" +
                            "WHERE A.estatus LIKE :estatus\n" +
                        "GROUP BY A.tecnico, A.estatus\n" +
                        "LIMIT 10;", nativeQuery = true)
    List<Map<String, String>> quantityTaskByStatusAndUser(@Param("estatus") String estatus);

    //cantidad de tareas por estatus, y agrupadas por técnico (tecnico logueado)
    @Query(value = "SELECT B.user AS 'Técnico',  CAST(COUNT(*) AS NCHAR) AS 'Cantidad', A.estatus As 'Estatus'\n" +
                        "FROM tarea A\n" +
                        "JOIN user B on B.id = A.tecnico\n" +
                        "WHERE A.estatus LIKE :estatus AND B.user LIKE :user\n" +
                        "GROUP BY A.tecnico, A.estatus\n" +
                        "LIMIT 10;", nativeQuery = true)
    List<Map<String, String>> quantityTaskByLoginUserAndStatus(@Param("user") String user, @Param("estatus") String estatus);
}
