package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.dto.CoordenadaDTO;
import com.ioc.dam_final_project.model.Coordenada;
import com.ioc.dam_final_project.repository.CoordenadaRepository;
import com.ioc.dam_final_project.repository.TareaRepository;
import com.ioc.dam_final_project.repository.UbicacionRepository;
import com.ioc.dam_final_project.service.AdminService;
import com.ioc.dam_final_project.service.CoordenadaService;
import com.ioc.dam_final_project.tools.Constantes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase CoordenadaServiceImpl
 *
 * SERA UN SERVICES DE LA CLASE Coordenada, implementa a su vez la Interface 'CoordenadaService' para gestionar los metodos alli suscritos.
 *
 *   Notaciones:
 *
 *   - He declarado a la clase como 'Service' para su mappeo en la base de datos.
 *   - He declarado a la clase con un 'Qualifier' para potenciar el polimorfismo y reuso de multiples Services.
 *   - He usado las notaciones propias de SpringBoot, en combinacion a Java 17 y Loombook, para potenciar al maximo la codificacion.

 *  @author Isabel Calzadilla
 *  @version  1.0
 *  @see  CoordenadaService interface que implementa
 */
@Service
@Qualifier(value = "coordenada")
public class CoordenadaServiceImpl implements CoordenadaService, Constantes {

    /**
     * CoordenadaRepository, refiere al repositorio de clase
     */
    private final CoordenadaRepository coordenadaRepository;
    /**
     * UbicacionRepository, refiere al repositorio de clase
     */
    private final UbicacionRepository ubicacionRepository;
    /**
     * TareaRepository, refiere al repositorio de clase
     */
    private final TareaRepository tareaRepository;

    /**
     * Constructor con 3 parametroS
     * @param coordenadaRepository entidad Repository de la clase
     * @param ubicacionRepository entidad Repository de la clase
     * @param tareaRepository entidad Repository de la clase
     */
    public CoordenadaServiceImpl(CoordenadaRepository coordenadaRepository, UbicacionRepository ubicacionRepository, TareaRepository tareaRepository) {
        this.coordenadaRepository = coordenadaRepository;
        this.ubicacionRepository = ubicacionRepository;
        this.tareaRepository = tareaRepository;
    }

    /** Metodo 'saveObject()'
     * @param  coordenada
     * @param  ubicacion
     * Recibe por parametro un Coordenada, y un ID, y retorna, segun multiples validaciones, la Coordenada especifico para esa Ubicacion
     * @return <ul>
     *  <li>Entidad : con la respuesta del service de la Coordenada </li>
     *  </ul>
     */
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

    /** Metodo 'getAll'
     * @return <ul>
     *  <li>List de CoordenadaDTO: Recorre a las coordenadas los cuales retorna</li>
     *  </ul>
     */
    @Override
    public List<CoordenadaDTO> coordenas() {

        var object = new ArrayList<CoordenadaDTO>();

        coordenadaRepository.findAll().forEach(coordenada -> object.add(CoordenadaDTO.byModel(coordenada)));

        return object;
    }

    /** Metodo 'delete'
     * Recibe un Id  y lo elimina de la base de datos.
     */
    @Override
    public void deleteEntity(Long id) {
        coordenadaRepository.deleteById(id);
    }

    /** Metodo 'searchById'
     * Recibe un Id y retorna la entidad encontrada en formato DTO.
     */
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

    /** Metodo 'isExistence'
     * Recibe un Id y valida la existencia del mismo, por entidad, en la base de datos.
     */
    public boolean isExistence(Long id){
        return coordenadaRepository.findById(id).isPresent();
    }
}
