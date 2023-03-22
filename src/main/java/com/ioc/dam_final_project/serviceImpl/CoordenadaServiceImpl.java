package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.dto.CoordenadaDTO;
import com.ioc.dam_final_project.model.Coordenada;
import com.ioc.dam_final_project.repository.CoordenadaRepository;
import com.ioc.dam_final_project.repository.TareaRepository;
import com.ioc.dam_final_project.repository.UbicacionRepository;
import com.ioc.dam_final_project.service.CoordenadaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier(value = "coordenada")
public class CoordenadaServiceImpl implements CoordenadaService  {

    private final CoordenadaRepository coordenadaRepository;
    private final UbicacionRepository ubicacionRepository;
    private final TareaRepository tareaRepository;

    public CoordenadaServiceImpl(CoordenadaRepository coordenadaRepository, UbicacionRepository ubicacionRepository, TareaRepository tareaRepository) {
        this.coordenadaRepository = coordenadaRepository;
        this.ubicacionRepository = ubicacionRepository;
        this.tareaRepository = tareaRepository;
    }

   /* @Override
    public Optional<Coordenada> saveObject(Coordenada coordenada) {
        //var coordenada = new Coordenada();
        return Optional.empty();
    }*/

    public void addCoordenada(Coordenada coordenada){
        var ubicacio = ubicacionRepository.findById(1L).orElseThrow();
        var tarea = ubicacio.getTarea();
        coordenada.setUbicacion(ubicacio);

        ubicacio.addCoordenate(coordenada);
        coordenadaRepository.save(coordenada);
        ubicacionRepository.save(ubicacio);

        tarea.setUbicacion(ubicacio);
        tareaRepository.save(tarea);

        System.out.println("Coordenada correctamente adherida");
    }

    @Override
    public List<CoordenadaDTO> coordenas() {

        var object = new ArrayList<CoordenadaDTO>();

        for(var i : coordenadaRepository.findAll()){
            object.add(CoordenadaDTO.byModel(i));
        }
        return object;
    }

    @Override
    public void deleteEntity(Long id) {
        coordenadaRepository.deleteAll();
    }
}
