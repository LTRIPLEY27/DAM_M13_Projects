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
     *
     * Metodo ByCreationDate
     * Recibe 2 parametros: Fecha Inicio y fecha Fin para filtrar en la base de datos ese valor de creacion especifico
     * @return <ul>
     *  <li>Listado de Tareas : Registro de todas las Tareas en la Base de Datos contenidas en ese rango de fechas</li>
     *  </ul>
     */
    @Query(value = "SELECT  *\n" +
                        "FROM tarea\n" +
                        "WHERE fecha_creacion BETWEEN :date1 AND :date2\n" +
                        "ORDER BY tarea.id DESC ;", nativeQuery = true)
    List <Tarea> filterByCreationDateRange(@PathVariable("date1") String date1, @PathVariable("date2") String date2);


    /**
     * Metodo filterByEndingDateRange
     * Recibe 2 parametros: Fecha Inicio y fecha Fin para filtrar en la base de datos ese valor de finalizacion especifico
     * @return <ul>
     *  <li>Listado de Tareas : Registro de todas las Tareas en la Base de Datos contenidas en ese rango de fechas</li>
     *  </ul>
     */
    @Query(value = "SELECT  *\n" +
                        "FROM tarea\n" +
                        "WHERE fecha_culminacion BETWEEN :date1 AND :date2\n" +
                        "ORDER BY tarea.id DESC ;", nativeQuery = true)
    List <Tarea> filterByEndingDateRange(@PathVariable("date1") String date1, @PathVariable("date2") String date2);

    /**
     * Metodo filterByCreationDateRangeAndAdmin
     * Recibe 3 parametros: Id del Usuario, Fecha Inicio y fecha Fin para filtrar en la base de datos ese valor de Creacion especifico
     * @return <ul>
     *  <li>Listado de Tareas : Registro de todas las Tareas en la Base de Datos contenidas en ese rango de fechas y relativas a ese usuario especifico</li>
     *  </ul>
     */
    @Query(value = "SELECT  *\n" +
                        "FROM tarea\n" +
                        "WHERE admin = :admin AND fecha_culminacion BETWEEN :date1 AND :date2\n" +
                        "ORDER BY tarea.id DESC ;", nativeQuery = true)
    List <Tarea> filterByCreationDateRangeAndAdmin(@PathVariable("admin") Long admin, @PathVariable("date1") String date1, @PathVariable("date2") String date2);

    /**
     * Metodo filterByEndingDateRangeAndAdmin
     * Recibe 3 parametros: Id del Usuario, Fecha Inicio y fecha Fin para filtrar en la base de datos ese valor de finalizacion especifico
     * @return <ul>
     *  <li>Listado de Tareas : Registro de todas las Tareas en la Base de Datos contenidas en ese rango de fechas y relativas a ese usuario especifico</li>
     *  </ul>
     */
    @Query(value = "SELECT  *\n" +
                        "FROM tarea\n" +
                        "WHERE admin = :admin AND fecha_culminacion BETWEEN :date1 AND :date2\n" +
                        "ORDER BY tarea.id DESC ;", nativeQuery = true)
    List <Tarea> filterByEndingDateRangeAndAdmin(@PathVariable("admin") Long admin, @PathVariable("date1") String date1, @PathVariable("date2") String date2);

    /**
     * Metodo filterByCreationDateRangeAndTecnic
     * Recibe 3 parametros: Id del Usuario, Fecha Inicio y fecha Fin para filtrar en la base de datos ese valor de Creacion especifico
     * @return <ul>
     *  <li>Listado de Tareas : Registro de todas las Tareas en la Base de Datos contenidas en ese rango de fechas y relativas a ese usuario especifico</li>
     *  </ul>
     */
    @Query(value = "SELECT  *\n" +
                        "FROM tarea\n" +
                        "WHERE tecnico = :tecnico AND fecha_creacion BETWEEN :date1 AND :date2\n" +
                        "ORDER BY tarea.id DESC ;", nativeQuery = true)
    List <Tarea> filterByCreationDateRangeAndTecnic(@PathVariable("tecnico") Long tecnico, @PathVariable("date1") String date1, @PathVariable("date2") String date2);

    /**
     * Metodo filterByEndingDateRangeAndTecnic
     * Recibe 3 parametros: Id del Usuario, Fecha Inicio y fecha Fin para filtrar en la base de datos ese valor de Finalizacion especifico
     * @return <ul>
     *  <li>Listado de Tareas : Registro de todas las Tareas en la Base de Datos contenidas en ese rango de fechas y relativas a ese usuario especifico</li>
     *  </ul>
     */
    @Query(value = "SELECT  *\n" +
                        "FROM tarea\n" +
                        "WHERE tecnico = :tecnico AND fecha_culminacion BETWEEN :date1 AND :date2\n" +
                        "ORDER BY tarea.id DESC ;", nativeQuery = true)
    List <Tarea> filterByEndingDateRangeAndTecnic(@PathVariable("tecnico") Long tecnico, @PathVariable("date1") String date1, @PathVariable("date2") String date2);

    /**
     * Metodo filterByStatusAndTecnic
     * Recibe 2 parametros: Id del Usuario, y el Estatus especifico a aplicar para filtrar las tareas
     * @return <ul>
     *  <li>Listado de Tareas : Registro de todas las Tareas en la Base de Datos contenidas con ese Estatus y relativas a ese usuario especifico</li>
     *  </ul>
     */
    @Query(value = "SELECT  *\n" +
                        "FROM tarea\n" +
                        "WHERE tecnico = :tecnico AND estatus LIKE :estatus\n" +
                        "ORDER BY tarea.id DESC ;", nativeQuery = true)
    List <Tarea> filterByStatusAndTecnic(@PathVariable("tecnico") Long tecnico, @PathVariable("estatus") String estatus);

    /**
     * Metodo filterByStatusAndAdmin
     * Recibe 2 parametros: Id del Usuario, y el Estatus especifico a aplicar para filtrar las tareas
     * @return <ul>
     *  <li>Listado de Tareas : Registro de todas las Tareas en la Base de Datos contenidas con ese Estatus y relativas a ese usuario especifico</li>
     *  </ul>
     */
    @Query(value = "SELECT  *\n" +
                        "FROM tarea\n" +
                        "WHERE admin = :admin AND estatus LIKE :estatus\n" +
                        "ORDER BY tarea.id DESC ;", nativeQuery = true)
    List <Tarea> filterByStatusAndAdmin(@PathVariable("admin") Long admin, @PathVariable("estatus") String estatus);

    /**
     * Metodo filterByStatus
     * Recibe 1 parametro: Estatus especifico a aplicar para filtrar las tareas (Solo ADMIN)
     * @return <ul>
     *  <li>Listado de Tareas : Registro de todas las Tareas en la Base de Datos contenidas con ese Estatus</li>
     *  </ul>
     */
    @Query(value = "SELECT  *\n" +
                        "FROM tarea\n" +
                        "WHERE estatus LIKE :estatus\n" +
                        "ORDER BY tarea.id DESC ;", nativeQuery = true)
    List <Tarea> filterByStatus(@PathVariable("estatus") String estatus);

    /**
     * Metodo filterByTareaTypeAndAdmin
     * Recibe 2 parametros: Id del Usuario, y el Tipo de Tarea especifico a aplicar para filtrar las tareas
     * @return <ul>
     *  <li>Listado de Tareas : Registro de todas las Tareas en la Base de Datos contenidas con ese Tipo de Tarea y relativas a ese usuario especifico</li>
     *  </ul>
     */
    @Query(value = "SELECT  *\n" +
                        "FROM tarea\n" +
                        "WHERE admin = :admin AND tarea LIKE :tarea\n" +
                        "ORDER BY tarea.id DESC ;", nativeQuery = true)
    List <Tarea> filterByTareaTypeAndAdmin(@PathVariable("admin") Long admin, @PathVariable("tarea") String tarea);

    /**
     * Metodo filterByTareaTypeAndTecnic
     * Recibe 2 parametros: Id del Usuario, y el Tipo de Tarea especifico a aplicar para filtrar las tareas
     * @return <ul>
     *  <li>Listado de Tareas : Registro de todas las Tareas en la Base de Datos contenidas con ese Tipo de Tarea y relativas a ese usuario especifico</li>
     *  </ul>
     */
    @Query(value = "SELECT  *\n" +
                        "FROM tarea\n" +
                        "WHERE tecnico = :tecnico AND tarea LIKE :tarea\n" +
                        "ORDER BY tarea.id DESC ;", nativeQuery = true)
    List <Tarea> filterByTareaTypeAndTecnic(@PathVariable("tecnico") Long tecnico, @PathVariable("tarea") String tarea);

    /**
     * Metodo filterByTareaType
     * Recibe 1 parametro: Tipo de Tarea especifico a aplicar para filtrar las tareas (Solo ADMIN)
     * @return <ul>
     *  <li>Listado de Tareas : Registro de todas las Tareas en la Base de Datos contenidas con ese Tipo de Tarea</li>
     *  </ul>
     */
    @Query(value = "SELECT  *\n" +
                        "FROM tarea\n" +
                        "WHERE tarea LIKE :tarea\n" +
                        "ORDER BY tarea.id DESC ;", nativeQuery = true)
    List <Tarea> filterByTareaType(@PathVariable("tarea") String tarea);

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
