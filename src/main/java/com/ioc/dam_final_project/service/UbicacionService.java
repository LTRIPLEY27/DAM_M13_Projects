package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.model.Ubicacion;

import java.util.List;
import java.util.Optional;

public interface UbicacionService {

   Ubicacion addObject(Ubicacion ubicacion);

   List<Ubicacion> getAll();

   Ubicacion findById(Long id);
}
