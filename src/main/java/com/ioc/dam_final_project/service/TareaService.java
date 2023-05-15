package com.ioc.dam_final_project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ioc.dam_final_project.dto.MensajeDTO;
import com.ioc.dam_final_project.dto.TareaDTO;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.model.User;

import java.util.List;

/**
 * Interface TareaService
 *
 * SERA UNA INTERFACE PROTOCOLO A IMPLEMENTAR POR EL SERVICES, extiende de la clase JPARepository para realizar el puente entre la entidad y la base de datos.
 *  @author Isabel Calzadilla
 *  @version  1.0
 */
public interface TareaService {

    TareaDTO saveObject(String username, Long id, Object object);
    List<TareaDTO> total();
    List<TareaDTO> getTareaByTecnico(Tecnico tecnico);
    List<TareaDTO> getTareaByUser(String user);
    List<TareaDTO> getTareaByName(String nombre);

    void deleteEntity(Long id);
    Object updateValue(Long id, Object object, User user) throws JsonProcessingException;

    List<TareaDTO> filteringByDates(String fecha, String date1, String date2);

    List<TareaDTO> filteringByDatesAndRol(User user, String fecha, String date1, String date2);

    List<TareaDTO> filteringByStatusAndRol(User user, String estatus);

    List<TareaDTO> filteringByTaskTypeAndRol(User user, String task);
    List<TareaDTO> getByTaskByStatus(String estatus);
    List<TareaDTO> getByTaskByTareaType(String task);

    List<Object> getByTecnicsAndStatus(String estatus);
    List<Object> getByTecnicsAndSTaskType(String tarea);
    List<Object> getByLoginTecnicsAndStatus(String tecnico, String estatus);
    List<Object> getByLoginTecnicsAndTaskType(String tecnico, String tarea);

    TareaDTO searchById(Long id);
}
