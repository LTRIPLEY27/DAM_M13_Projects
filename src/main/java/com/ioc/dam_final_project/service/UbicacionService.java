package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.dto.UbicacionDTO;
import com.ioc.dam_final_project.model.Ubicacion;

import java.util.List;
import java.util.Optional;

public interface UbicacionService {

   Ubicacion addObject(Ubicacion ubicacion);

   List<UbicacionDTO> getAll();

   UbicacionDTO findById(Long id);
}
