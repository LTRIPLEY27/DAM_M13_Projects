package com.ioc.dam_final_project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ioc.dam_final_project.dto.CoordenadaDTO;
import com.ioc.dam_final_project.dto.MensajeDTO;
import com.ioc.dam_final_project.dto.UbicacionDTO;
import com.ioc.dam_final_project.dto.UserDTO;
import com.ioc.dam_final_project.model.Coordenada;
import com.ioc.dam_final_project.model.Ubicacion;

import java.util.List;

/**
 * Interface UserService
 *
 * SERA UNA INTERFACE PROTOCOLO A IMPLEMENTAR POR EL SERVICES, extiende de la clase JPARepository para realizar el puente entre la entidad y la base de datos.
 *  @author Isabel Calzadilla
 *  @version  1.0
 */
public interface UserService {

    /**
     *
     */

    Object getProfile(String username);


    List<Object> registers(String username, String value);

    UserDTO update(String old, Object userDTO);

    Object updateValue(String username, String value, Long id, Object object) throws JsonProcessingException;

    //void deleteRegister(String rol, String typus, Long id);
    Object deleteRegister(String rol, String typus, Long id);

    MensajeDTO postingMessage(String user, MensajeDTO mensajeDTO);

    Object searchById(String value, Long id);

   // Object addNew(String user, String tipo, Long valor, Object object);
    Object addNewTar(String username, Long id, Object object);

    Ubicacion addNewUbicacion(Ubicacion ubicacion, Long id);

    CoordenadaDTO addNewCoor(Coordenada coordenada, Long ubicacion);

    List<Object> findTaskByTecnic(String username);

    boolean checkLocation(Long idTarea);
}
