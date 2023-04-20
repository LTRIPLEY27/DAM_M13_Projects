package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.dto.CoordenadaDTO;
import com.ioc.dam_final_project.model.Coordenada;
import com.ioc.dam_final_project.repository.CoordenadaRepository;
import com.ioc.dam_final_project.repository.TareaRepository;
import com.ioc.dam_final_project.repository.UbicacionRepository;
import com.ioc.dam_final_project.service.CoordenadaService;
import com.ioc.dam_final_project.tools.Constantes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier(value = "coordenada")
public class CoordenadaServiceImpl implements CoordenadaService, Constantes {

    private final CoordenadaRepository coordenadaRepository;
    private final UbicacionRepository ubicacionRepository;
    private final TareaRepository tareaRepository;

    public CoordenadaServiceImpl(CoordenadaRepository coordenadaRepository, UbicacionRepository ubicacionRepository, TareaRepository tareaRepository) {
        this.coordenadaRepository = coordenadaRepository;
        this.ubicacionRepository = ubicacionRepository;
        this.tareaRepository = tareaRepository;
    }


    public CoordenadaDTO saveObject(Coordenada coordenada, Long ubicacion){
        var ubicacio = ubicacionRepository.findById(ubicacion).orElseThrow();
        var tarea = ubicacio.getTarea();
        coordenada.setUbicacion(ubicacio);

        ubicacio.addCoordenate(coordenada);
        coordenadaRepository.save(coordenada);
        ubicacionRepository.save(ubicacio);
        tarea.setUbicacion(ubicacio);
        tareaRepository.save(tarea);

        return CoordenadaDTO.byModel(coordenada);
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

    @Override
    public CoordenadaDTO searchById(Long id) {
        return CoordenadaDTO.byModel(coordenadaRepository.findById(id).orElseThrow());
    }

    /** Metodo updateValue
     * Recibe 2 parametros : el Id de la entidad a actualizar, el valor de la entidad a apuntar dicho cambio, el Objeto con los nuevos valores contenidos. Actualiza un objeto 'Entity' en la base de datos
     * @return <ul>
     *  <li>Entity: valida segun el caso de uso y gestiona el update de dicha entidad con los nuevos valores</li>
     *  </ul>
     */
    @Override
    public CoordenadaDTO updateValue(Long id, Object object) {
        var oldCoor = coordenadaRepository.findById(id).orElseThrow();
        var newCoor = mapper.convertValue(object, CoordenadaDTO.class);

        oldCoor.setLongitud(newCoor.getLongitud());
        oldCoor.setLatitud(newCoor.getLatitud());

        coordenadaRepository.save(oldCoor);

        return CoordenadaDTO.byModel(oldCoor);
    }

    public boolean isExistence(Long id){
        return coordenadaRepository.findById(id).isPresent();
    }
}
