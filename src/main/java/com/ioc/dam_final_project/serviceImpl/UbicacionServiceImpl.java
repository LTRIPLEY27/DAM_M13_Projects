package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.dto.UbicacionDTO;
import com.ioc.dam_final_project.model.Ubicacion;
import com.ioc.dam_final_project.repository.CoordenadaRepository;
import com.ioc.dam_final_project.repository.TareaRepository;
import com.ioc.dam_final_project.repository.UbicacionRepository;
import com.ioc.dam_final_project.service.AdminService;
import com.ioc.dam_final_project.service.UbicacionService;
import com.ioc.dam_final_project.tools.Constantes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase UbicacionServiceImpl
 *
 * SERA UN SERVICES DE LA CLASE Ubicacion, implementa a su vez la Interface 'UbicacionService' para gestionar los metodos alli suscritos.
 *
 *   Notaciones:
 *
 *   - He declarado a la clase como 'Service' para su mappeo en la base de datos.
 *   - He declarado a la clase con un 'Qualifier' para potenciar el polimorfismo y reuso de multiples Services.
 *   - He usado las notaciones propias de SpringBoot, en combinacion a Java 17 y Loombook, para potenciar al maximo la codificacion.

 *  @author Isabel Calzadilla
 *  @version  1.0
 *  @see  UbicacionService interface que implementa
 */
@Service
@Qualifier(value = "ubicacion")
public class UbicacionServiceImpl implements UbicacionService, Constantes {

    /**
     * UbicacionRepository, refiere al repositorio de clase
     */
    private final UbicacionRepository ubicacionRepository;
    /**
     * TareaRepository, refiere al repositorio de clase
     */
    private final TareaRepository tareaRepository;
    /**
     * CoordenadaRepository, refiere al repositorio de clase
     */
    private final CoordenadaRepository coordenadaRepository;

    /**
     * Constructor con 3 parametros
     * @param ubicacionRepository entidad Repository de la clase
     * @param tareaRepository entidad Repository de la clase
     * @param coordenadaRepository entidad Repository de la clase
     */
    public UbicacionServiceImpl(UbicacionRepository ubicacionRepository, TareaRepository tareaRepository, CoordenadaRepository coordenadaRepository) {
        this.ubicacionRepository = ubicacionRepository;
        this.tareaRepository = tareaRepository;
        this.coordenadaRepository = coordenadaRepository;
    }

    /**
     * Metodo que recibe multiples parametros para crear una tarea
     * @param ubicacion entidad que contiene todos los parametros que conformaran la clase Ubicacion
     * @param tareaId id del Tecnico a agregar en la Tarea
     * @return <ul>
     *  <li>Ubicacion: Retorna una Ubicacion Entity con los valores editados para el formato JSON</li>
     *  </ul>
     */
    @Override
    public Ubicacion saveObject(Ubicacion ubicacion, Long tareaId) {
        var tarea = tareaRepository.findById(tareaId).orElseThrow();
        tarea.setUbicacion(ubicacion);
        ubicacion.setTarea(tarea);

        return ubicacionRepository.save(ubicacion);
    }

    /** Metodo 'getAll'
     * @return <ul>
     *  <li>List de UbicacionDTO: Recorre todas las Ubicaciones contenidas en la base de datos, y las retorna</li>
     *  </ul>
     */
    @Override
    public List<UbicacionDTO> getAll() {
        var ubicaciones = new ArrayList<UbicacionDTO>();
        ubicacionRepository.findAll().forEach(ubicacion -> ubicaciones.add(UbicacionDTO.byModel(ubicacion)));

        return ubicaciones;
    }

    /** Metodo 'deleteEntity'
     * Recibe un Id  y lo elimina de la base de datos.
     */
    @Override
    public void deleteEntity(Long id) {
        var tarea = tareaRepository.findTareaByUbicacion(ubicacionRepository.findById(id).orElseThrow());
        tarea.setUbicacion(null);
        ubicacionRepository.deleteById(id);
    }

    /** Metodo 'searchById'
     * Recibe un Id y retorna la entidad encontrada en formato DTO.
     */
    @Override
    public UbicacionDTO searchById(Long id) {
        return UbicacionDTO.byModel(ubicacionRepository.findById(id).orElseThrow());
    }

    /** Metodo updateValue
     * Recibe 2 parametros : el Id de la entidad a actualizar, el valor de la entidad a apuntar dicho cambio, el Objeto con los nuevos valores contenidos. Actualiza un objeto 'Entity' en la base de datos
     * @return <ul>
     *  <li>Entity: valida segun el caso de uso y gestiona el update de dicha entidad con los nuevos valores</li>
     *  </ul>
     */
    @Override
    public Object updateValue(Long id, Object object) {
        var oldUbi = ubicacionRepository.findById(id).orElseThrow();
        var newUbi = mapper.convertValue(object, UbicacionDTO.class);

        oldUbi.setZoom(newUbi.getZoom());
        oldUbi.setCentroLatitud(newUbi.getCentroLatitud());
        oldUbi.setCentroLongitud(newUbi.getCentroLongitud());
        //oldUbi.setTarea(tareaRepository.findById(newUbi.getTarea()).orElseThrow());

        return UbicacionDTO.byModel(ubicacionRepository.save(oldUbi));
    }

    /** Metodo 'checkTarea'
     * Recibe un Id y valida si la Tarea ya se encuentra registrada en una Ubicacion.
     */
    @Override
    public boolean checkTarea(Long id) {
        return ubicacionRepository.findByTarea_Id(id).isPresent();
    }

    /** Metodo 'isExistence'
     * Recibe un Id y valida la existencia del mismo, por entidad, en la base de datos.
     */
    public boolean isExistence(Long id){
        return ubicacionRepository.findById(id).isPresent();
    }
}
