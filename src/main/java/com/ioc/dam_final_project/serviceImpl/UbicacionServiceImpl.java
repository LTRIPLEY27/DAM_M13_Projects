package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.model.Ubicacion;
import com.ioc.dam_final_project.repository.CoordenadaRepository;
import com.ioc.dam_final_project.repository.TareaRepository;
import com.ioc.dam_final_project.repository.UbicacionRepository;
import com.ioc.dam_final_project.service.UbicacionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier(value = "ubicacion")
public class UbicacionServiceImpl implements UbicacionService {

    private final UbicacionRepository ubicacionRepository;
    private final TareaRepository tareaRepository;
    private final CoordenadaRepository coordenadaRepository;

    public UbicacionServiceImpl(UbicacionRepository ubicacionRepository, TareaRepository tareaRepository, CoordenadaRepository coordenadaRepository) {
        this.ubicacionRepository = ubicacionRepository;
        this.tareaRepository = tareaRepository;
        this.coordenadaRepository = coordenadaRepository;
    }

    @Override
    public Ubicacion addObject(Ubicacion ubicacion) {
        var tarea = tareaRepository.findById(1L).orElseThrow();
        tarea.setUbicacion(ubicacion);
        tareaRepository.save(tarea);
        ubicacion.setTarea(tarea);

        return ubicacionRepository.save(ubicacion);
    }

    @Override
    public List<Ubicacion> getAll() {
        return ubicacionRepository.findAll();
    }

    @Override
    public Ubicacion findById(Long id) {
        return ubicacionRepository.findById(id).orElseThrow();
    }
}
