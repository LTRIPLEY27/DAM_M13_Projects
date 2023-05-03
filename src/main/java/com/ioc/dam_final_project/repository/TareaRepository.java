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


    /*@Query(value = "SELECT  *\n" +
                        "FROM tarea \n" +
                        "WHERE :fecha BETWEEN :date1 AND :date2\n" +
                        "ORDER BY tarea.id DESC ;", nativeQuery = true)*/
    @Query(value = "SELECT  *\n" +
            "    FROM tarea\n" +
            "    WHERE REPLACE(:fecha, '\"\"', '') BETWEEN :date1 AND :date2\n" +
            "    ORDER BY tarea.id DESC ;", nativeQuery = true)
    List <Tarea> filterByDateRange(@Param("fecha") Object fecha, @PathVariable("date1") String date1, @PathVariable("date2") String date2);

    @Query(value = "SELECT B.nombre AS 'Técnico',  COUNT(*) AS 'Cantidad'\n" +
                        "FROM tarea A\n" +
                        "JOIN user B on B.id = A.tecnico\n" +
                            "WHERE A.estatus LIKE :estatus\n" +
                        "GROUP BY A.tecnico\n" +
                        "LIMIT 10;", nativeQuery = true)
    List<Map<String, Integer>> quantityTaskByStatusAndUser(@Param("estatus") String estatus);

}
