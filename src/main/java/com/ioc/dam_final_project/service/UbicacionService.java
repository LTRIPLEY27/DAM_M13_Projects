package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.dto.CoordenadaDTO;
import com.ioc.dam_final_project.dto.UbicacionDTO;
import com.ioc.dam_final_project.model.Ubicacion;

import java.util.List;
import java.util.Optional;

public interface UbicacionService {

   Ubicacion saveObject(Ubicacion ubicacion, Long id);

   List<UbicacionDTO> getAll();

   void deleteEntity(Long id);

   UbicacionDTO searchById(Long id);

   //UbicacionDTO updateValue(Long id, Object object);
   Object updateValue(Long id, Object object);

   boolean checkTarea(Long id);
}
