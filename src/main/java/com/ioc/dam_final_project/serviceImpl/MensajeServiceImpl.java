package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.dto.MensajeDTO;
import com.ioc.dam_final_project.model.*;
import com.ioc.dam_final_project.repository.*;
import com.ioc.dam_final_project.service.CoordenadaService;
import com.ioc.dam_final_project.service.MensajeService;
import com.ioc.dam_final_project.tools.Constantes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.*;

/**
 * Clase MensajeServiceImpl
 *
 * SERA UN SERVICES DE LA CLASE Mensaje, implementa a su vez la Interface 'MensajeService' para gestionar los metodos alli suscritos.
 *
 *   Notaciones:
 *
 *   - He declarado a la clase como 'Service' para su mappeo en la base de datos.
 *   - He declarado a la clase con un 'Qualifier' para potenciar el polimorfismo y reuso de multiples Services.
 *   - He usado las notaciones propias de SpringBoot, en combinacion a Java 17 y Loombook, para potenciar al maximo la codificacion.

 *  @author Isabel Calzadilla
 *  @version  1.0
 *  @see  MensajeService interface que implementa
 */
@Service
@Qualifier(value = "mensaje")
public class MensajeServiceImpl implements MensajeService, Constantes {

    /**
     * MensajeRepository, refiere al repositorio de clase
     */
    private final MensajeRepository mensajeRepository;
    /**
     * UserRepository, refiere al repositorio de clase
     */
    private final UserRepository userRepository;
    /**
     * TareaRepository, refiere al repositorio de clase
     */
    private final TareaRepository tareaRepository;
    /**
     * TecnicoRepository, refiere al repositorio de clase
     */
    private final TecnicoRepository tecnicoRepository;
    /**
     * AdminRepository, refiere al repositorio de clase
     */
    private final AdminRepository adminRepository;

    /**
     * Constructor con 5 parametros
     * @param mensajeRepository entidad Repository de la clase
     * @param userRepository entidad Repository de la clase
     * @param tareaRepository entidad Repository de la clase
     * @param tecnicoRepository entidad Repository de la clase
     * @param adminRepository entidad Repository de la clase
     */
    public MensajeServiceImpl(MensajeRepository mensajeRepository, UserRepository userRepository, TareaRepository tareaRepository, TecnicoRepository tecnicoRepository, AdminRepository adminRepository) {
        this.mensajeRepository = mensajeRepository;
        this.userRepository = userRepository;
        this.tareaRepository = tareaRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.adminRepository = adminRepository;
    }

    /** Metodo 'delete'
     * Recibe un Id  y lo elimina de la base de datos.
     */
    @Override
    public void deleteEntity(Long id) {
        mensajeRepository.delete(mensajeRepository.findById(id).orElseThrow());
    }

    /**
     * Metodo que gestionara el posteo de mensajes entre el Admin y el Tecnico a modo de hilo conversacional.
     * Llama a su vez a un metodo privado de clase que actualizara las relaciones de todos los objetos que participan en la accion
     * @param username es la variable que contiene el emisor del mensaje a realizar.
     * @param mensaje es la instancia de Mensaje a la que se le realizara el hilo.
     * @return La instancia mensaje conteniendo todas las relaciones de los objetos con los que participa actualizados.
     */
    @Override
    public MensajeDTO postMessage(String username, MensajeDTO mensaje) {
        var user = userRepository.findUserByEmail(username).orElseThrow();

        switch (user.getRol()){
            case ADMIN -> {
                var object = (Tecnico) userRepository.findUserByUser(mensaje.getTecnico()).orElseThrow();
                var tarea = tareaRepository.findTareaByAdminAndId((Admin) user, mensaje.getTarea()); //name must be unique

                return posting(mensaje, tarea, object, (Admin) user);
            }
            case TECNIC -> {
                var object = (Admin) userRepository.findUserByUser(mensaje.getAdmin()).orElseThrow();
                var tarea = tareaRepository.findTareaByTecnicoAndId((Tecnico) user, mensaje.getTarea()); //name must be unique

                return posting(mensaje, tarea, (Tecnico) user, object);
            }
        }
        return null;
    }

    /** Metodo 'searchById'
     * Recibe un Id y retorna la entidad encontrada en formato DTO.
     */
    @Override
    public MensajeDTO searchById(Long id) {
        return MensajeDTO.byModel(mensajeRepository.findById(id).orElseThrow());
    }

    /** Metodo updateValue
     * Recibe 2 parametros : el Id de la entidad a actualizar, el valor de la entidad a apuntar dicho cambio, el Objeto con los nuevos valores contenidos. Actualiza un objeto 'Entity' en la base de datos
     * @return <ul>
     *  <li>Entity: valida segun el caso de uso y gestiona el update de dicha entidad con los nuevos valores</li>
     *  </ul>
     */
    @Override
    public MensajeDTO updateValue(Long id, Object object) {
        var oldMess = mensajeRepository.findById(id).orElseThrow();
        var newMess = mapper.convertValue(object, MensajeDTO.class);

        oldMess.setDescripcion(newMess.getDescripcion());
        mensajeRepository.save(oldMess);

        return MensajeDTO.byModel(oldMess);
    }

    /** Metodo 'findMessageByTecnic'
     * @return <ul>
     *  <li>List de MensajeDTO: Recorre los mensajes del tecnico, los cuales retorna</li>
     *  </ul>
     */
    @Override
    public List<MensajeDTO> findMessageByTecnic(Tecnico tecnico) {
        var mensajes = new ArrayList<MensajeDTO>();
        mensajeRepository.findMensajeByTecnico(tecnico).forEach(mensaje -> mensajes.add(MensajeDTO.byModel(mensaje)));

        return mensajes;
    }

    /** Metodo 'findMessageByAdmin'
     * @return <ul>
     *  <li>List de MensajeDTO: Recorre los mensajes del admin, los cuales retorna</li>
     *  </ul>
     */
    @Override
    public List<MensajeDTO> findMessageByAdmin(Admin admin) {
        List<MensajeDTO> mensajes = new ArrayList<>();
        mensajeRepository.findMensajeByAdmin(admin).forEach(mensaje -> mensajes.add(MensajeDTO.byModel(mensaje)));
        return mensajes; // todo check
    }

    /** Metodo 'getAll'
     * @return <ul>
     *  <li>List de MensajeDTO: Recorre todos los mensajes contenidos en la base de datos</li>
     *  </ul>
     */
    @Override
    public List<MensajeDTO> getAll() {
        List<MensajeDTO> messages = new ArrayList<MensajeDTO>();
        mensajeRepository.findAll().forEach(mensaje -> messages.add(MensajeDTO.byModel(mensaje)));
        return  messages;
    }

    /** Metodo 'filterByDate'
     * @return <ul>
     *  <li>List de MensajeDTO: Recorre todos los mensajes contenidos en la base de datos con el rango de Fecha indicado</li>
     *  </ul>
     */
    @Override
    public List<MensajeDTO> filteringByDates(String date1, String date2) {
        List<MensajeDTO> messages = new ArrayList<MensajeDTO>();
        mensajeRepository.filterByDateRange(date1, date2).forEach(mensaje -> messages.add(MensajeDTO.byModel(mensaje)));
        return  messages;
    }

    /**
     * Metodo que gestionara el posteo de mensajes entre el Admin y el Tecnico a modo de hilo conversacional.
     * @param mensaje es la instancia de Mensaje a la que se le realizara el hilo.
     * @param tarea es la instancia de Tarea en la que se adjuntara el mensaje.
     * @param tecnico es la instancia Tecnico que sera el emisor o receptor de ese mensaje sobre esa Tarea.
     * @param admin es la instancia Admin que sera el emisor o receptor de ese mensaje sobre esa Tarea.
     *
     * @return La instancia mensaje conteniendo todas las relaciones de los objetos con los que participa actualizados.
     */
    private MensajeDTO posting(MensajeDTO mensaje, Tarea tarea, Tecnico tecnico, Admin admin){

        var byModel = new Mensaje(mensaje.getDescripcion(), tarea, tecnico,  admin);
        tarea.getMensaje().add(byModel);
        admin.getMensaje().add(byModel);
        tecnico.getMensaje().add(byModel);
        adminRepository.save(admin);
        tecnicoRepository.save(tecnico);
        tareaRepository.save(tarea);
        mensajeRepository.save(byModel);

        return MensajeDTO.byModel(byModel);
    }

    /** Metodo 'isExistence'
     * Recibe un Id y valida la existencia del mismo, por entidad, en la base de datos.
     */
    public boolean isExistence(Long id){
        return mensajeRepository.findById(id).isPresent();
    }
}
