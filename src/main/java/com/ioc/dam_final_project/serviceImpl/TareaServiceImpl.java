package com.ioc.dam_final_project.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ioc.dam_final_project.dto.MensajeDTO;
import com.ioc.dam_final_project.dto.TareaDTO;
import com.ioc.dam_final_project.model.*;
import com.ioc.dam_final_project.model.Enums.Rol;
import com.ioc.dam_final_project.repository.*;
import com.ioc.dam_final_project.service.TareaService;
import com.ioc.dam_final_project.tools.Constantes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;

/**
 * Clase TareaServiceImpl
 *
 * SERA UN SERVICES DE LA CLASE Tarea, implementa a su vez la Interface 'TareaService' para gestionar los metodos alli suscritos.
 *
 *   Notaciones:
 *
 *   - He declarado a la clase como 'Service' para su mappeo en la base de datos.
 *   - He declarado a la clase con un 'Qualifier' para potenciar el polimorfismo y reuso de multiples Services.
 *   - He usado las notaciones propias de SpringBoot, en combinacion a Java 17 y Loombook, para potenciar al maximo la codificacion.

 *  @author Isabel Calzadilla
 *  @version  1.0
 *  @see  TareaService interface que implementa
 */
@Service
@Qualifier(value = "tarea")
public class TareaServiceImpl implements TareaService, Constantes {

    // INYECCION DE DEPENDENCIAS
    /**
     * TecnicoRepository, refiere al repositorio de clase
     */
    private final TecnicoRepository tecnicoRepository;
    /**
     * AdminRepository, refiere al repositorio de clase
     */
    private final AdminRepository adminRepository;
    /**
     * TareaRepository, refiere al repositorio de clase
     */
    private final TareaRepository tareaRepository;
    /**
     * UbicacionRepository, refiere al repositorio de clase
     */
    private final UbicacionRepository ubicacionRepository;
    /**
     * UserRepository, refiere al repositorio de clase
     */
    private final UserRepository userRepository;

    /**
     * Constructor con 4 parametros
     *
     * @param tecnicoRepository   entidad Repository de la clase
     * @param adminRepository     entidad Repository de la clase
     * @param tareaRepository     entidad Repository de la clase
     * @param ubicacionRepository entidad Repository de la clase
     * @param userRepository      entidad Repository de la clase
     */
    public TareaServiceImpl(TecnicoRepository tecnicoRepository, AdminRepository adminRepository, TareaRepository tareaRepository, UbicacionRepository ubicacionRepository, UserRepository userRepository) {
        this.tecnicoRepository = tecnicoRepository;
        this.adminRepository = adminRepository;
        this.tareaRepository = tareaRepository;
        this.ubicacionRepository = ubicacionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Metodo que recibe multiples parametros para crear una tarea
     * @param username entidad del Admin contenido en Principal
     * @param id id del Tecnico a agregar en la Tarea
     * @param object entidad que contiene todos los parametros que conformaran la clase Tarea
     * @return <ul>
     *  <li>TareaDTO: Retorna un Tarea Entity con los valores editados para el formato JSON</li>
     *  </ul>
     */
    @Override// todo agregar identificacion de la fecha de la tarea finalizar
    public TareaDTO saveObject(String username, Long id, Object object) {
        var tarea = mapper.convertValue(object, Tarea.class);
        var tecnico = tecnicoRepository.findById(id).orElseThrow(); // TODO, VALIDAR QUE IDENTIFICACION NOS PASARA EL ADMIN PARA EL TECNICO
        var admin = adminRepository.findAdminByEmail(username).orElseThrow();

        tarea.setAdmin(admin);
        tarea.setTecnico(tecnico);

        return TareaDTO.byModel(tareaRepository.save(tarea));
    }

    /** Metodo 'total'
     * @return <ul>
     *  <li>List de TareaDTO: Recorre todas las Tareas contenidos en la base de datos</li>
     *  </ul>
     */
    @Override
    public List<TareaDTO> total() {
        var object = new ArrayList<TareaDTO>();
        tareaRepository.findAll().forEach(tarea -> object.add(TareaDTO.byModel(tarea)));
        return object;
    }

    /** Metodo 'filterByDate'
     * @return <ul>
     *  <li>List de TareaDTO: Recorre todos las Tareas contenidas en la base de datos con el rango de Fecha indicado</li>
     *  </ul>
     */
    @Override
    public List<TareaDTO> filteringByDates(String fecha, String date1, String date2) {
        List<TareaDTO> tareas = new ArrayList<>();
        switch (fecha){
            case STARTING -> tareaRepository.filterByCreationDateRange(date1, date2).forEach(tarea -> tareas.add(TareaDTO.byModel(tarea)));
            case ENDING -> tareaRepository.filterByEndingDateRange(date1, date2).forEach(tarea -> tareas.add(TareaDTO.byModel(tarea)));
        }

        return  tareas;
    }

    /** Metodo 'getByTecnicsAndStatus'
     * @return <ul>
     *  <li>List de TareaDTO: Recorre todos las Tareas contenidas en la base de datos y las agrupa por tecnico y estatus</li>
     *  </ul>
     */
    @Override
    public List<Object> getByTecnicsAndStatus(String estatus) {
        List<Object> tareas = new ArrayList<>();
        tareaRepository.quantityTaskByStatusAndUser(estatus).forEach(tarea -> tareas.add(tarea));
        return tareas;
    }

    /** Metodo 'getByTecnicsAndStatus'
     * @return <ul>
     *  <li>List de TareaDTO: Recorre todos las Tareas contenidas en la base de datos y las agrupa por tecnico y estatus</li>
     *  </ul>
     */
    @Override
    public List<Object> getByLoginTecnicsAndStatus(String tecnico, String estatus) {
        List<Object> tareas = new ArrayList<>();
        tareaRepository.quantityTaskByLoginUserAndStatus(tecnico, estatus).forEach(tarea -> tareas.add(tarea));
        return tareas;
    }

    /** Metodo 'getTareaByTecnico'
     * @return <ul>
     *  <li>List de TareaDTO: Recorre las Tareas del tecnico, los cuales retorna</li>
     *  </ul>
     */
    @Override
    public List<TareaDTO> getTareaByTecnico(Tecnico tecnico) {

        var object = new ArrayList<TareaDTO>();
        tareaRepository.findTareaByTecnico(tecnico).forEach(tarea -> object.add(TareaDTO.byModel(tarea)));

        return object;
    }

    /** Metodo 'getTareaByUser'
     * @return <ul>
     *  <li>List de TareaDTO: Recorre las Tareas del usuario, los cuales retorna</li>
     *  </ul>
     */
    @Override
    public List<TareaDTO> getTareaByUser(String user) {
        var usuario = userRepository.findUserByUser(user).orElseThrow();
        var object = new ArrayList<TareaDTO>();

        if (usuario.getRol() != Rol.ADMIN) { tareaRepository.findTareaByTecnico((Tecnico) usuario).forEach(tarea -> object.add(TareaDTO.byModel(tarea)));}
        else {tareaRepository.findTareaByAdmin((Admin) usuario).forEach(tarea -> object.add(TareaDTO.byModel(tarea)));}

        return object;
    }

    /** Metodo 'getTareaByName'
     * @return <ul>
     *  <li>List de TareaDTO: Recorre las Tareas del usuario por Nombre, los cuales retorna</li>
     *  </ul>
     */
    @Override
    public List<TareaDTO> getTareaByName(String user) {
        var usuario = userRepository.findUserByNombre(user).orElseThrow();
        var object = new ArrayList<TareaDTO>();

        if (usuario.getRol() != Rol.ADMIN) { tareaRepository.findTareaByTecnico((Tecnico) usuario).forEach(tarea -> object.add(TareaDTO.byModel(tarea)));}
        else {tareaRepository.findTareaByAdmin((Admin) usuario).forEach(tarea -> object.add(TareaDTO.byModel(tarea)));}

        return object;
    }

    /** Metodo 'deleteEntity'
     * Recibe un Id  y lo elimina de la base de datos.
     */
    @Override
    public void deleteEntity(Long id) {
        tareaRepository.deleteById(id);
    }


    // TODO, MUY URGENTE, IDENTIFICAR FINALMENTE QUE CAMPOS DE TAREA SE HARAN UPDATE CADA VEZ QUE SE DESEE?
    /** Metodo updateValue
     * Recibe 2 parametros : el Id de la entidad a actualizar, el valor de la entidad a apuntar dicho cambio, el Objeto con los nuevos valores contenidos. Actualiza un objeto 'Entity' en la base de datos
     * @return <ul>
     *  <li>Entity: valida segun el caso de uso y gestiona el update de dicha entidad con los nuevos valores</li>
     *  </ul>
     */
    @Override
    public Object updateValue(Long id, Object object, User user) throws JsonProcessingException {
        var tar = tareaRepository.findById(id).orElseThrow();
        var newTar = mapper.convertValue(object, TareaDTO.class);

        if(user.getRol() != Rol.TECNIC){
            tar.setName(newTar.getName());
            tar.setTarea(newTar.getTarea());
            tar.setFecha_culminacion(newTar.getFecha_culminacion());
            tar.setTecnico(tecnicoRepository.findTecnicoByUser(newTar.getTecnico()).orElseThrow());// transformar de dto a objeto
            tar.setAdmin(adminRepository.findAdminByUser(newTar.getAdmin()));//from dto
            tar.setUbicacion(ubicacionRepository.findById(newTar.getUbicacionId()).orElseThrow());
            ubicacionRepository.findById(newTar.getUbicacionId()).orElseThrow().setTarea(tar);
        }
        tar.setEstatus(newTar.getEstatus());

        return TareaDTO.byModel(tareaRepository.save(tar));
    }

    /** Metodo 'searchById'
     * Recibe un Id y retorna la entidad encontrada en formato DTO.
     */
    @Override
    public TareaDTO searchById(Long id) {
        return TareaDTO.byModel(tareaRepository.findById(id).orElseThrow());
    }

    /** Metodo 'isExistence'
     * Recibe un Id y valida la existencia del mismo, por entidad, en la base de datos.
     */
    public boolean isExistence(Long id){
        return tareaRepository.findById(id).isPresent();
    }
}
