package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.dto.UbicacionDTO;
import com.ioc.dam_final_project.model.Ubicacion;

import java.util.List;

/**
 * Interface UbicacionService
 *
 * SERA UNA INTERFACE PROTOCOLO A IMPLEMENTAR POR EL SERVICES, extiende de la clase JPARepository para realizar el puente entre la entidad y la base de datos.
 *  @author Isabel Calzadilla
 *  @version  1.0
 */
public interface UbicacionService {

   UbicacionDTO saveObject(Ubicacion ubicacion, Long id);

   List<UbicacionDTO> getAll();

   void deleteEntity(Long id);

   UbicacionDTO searchById(Long id);

   //UbicacionDTO updateValue(Long id, Object object);
   Object updateValue(Long id, Object object);

   boolean checkTarea(Long id);
}
