package com.ioc.dam_final_project.repository;

import com.ioc.dam_final_project.dto.MensajeDTO;
import com.ioc.dam_final_project.model.Admin;
import com.ioc.dam_final_project.model.Mensaje;
import com.ioc.dam_final_project.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Interface MensajeRepository
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
public interface MensajeRepository extends JpaRepository <Mensaje, Long> {

    List<Mensaje> findMensajeByTecnico(Tecnico tecnico);
    List<Mensaje> findMensajeByAdmin(Admin admin);

    /**
     * QUERY'S CON FILTROS ESPECIFICOS
     */
    @Query(value = "SELECT  * FROM mensaje\n" +
                    "WHERE fecha BETWEEN :date1 AND :date2\n" +
                    "ORDER BY mensaje.id DESC ;", nativeQuery = true)
    List <Mensaje> filterByDateRange(@PathVariable("date1") String date1, @PathVariable("date2") String date2);
}
