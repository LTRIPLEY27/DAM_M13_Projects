package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.dto.MensajeDTO;
import com.ioc.dam_final_project.dto.TareaDTO;
import com.ioc.dam_final_project.model.*;
import com.ioc.dam_final_project.repository.*;
import com.ioc.dam_final_project.service.MensajeService;
import com.ioc.dam_final_project.tools.Constantes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier(value = "mensaje")
public class MensajeServiceImpl implements MensajeService, Constantes {

    private final MensajeRepository mensajeRepository;
    private final UserRepository userRepository;
    private final TareaRepository tareaRepository;
    private final TecnicoRepository tecnicoRepository;
    private final AdminRepository adminRepository;

    public MensajeServiceImpl(MensajeRepository mensajeRepository, UserRepository userRepository, TareaRepository tareaRepository, TecnicoRepository tecnicoRepository, AdminRepository adminRepository) {
        this.mensajeRepository = mensajeRepository;
        this.userRepository = userRepository;
        this.tareaRepository = tareaRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public void deleteEntity(Long id) {
        mensajeRepository.delete(mensajeRepository.findById(id).orElseThrow());
    }

    /**
     * Metodo que gestionara el posteo de mensajes entre el Admin y el Tecnico a modo de hilo conversacional.
     * Llama a su vez a un metodo privado de clase que actualizara las relaciones de todos los objetos que participan en la accion
     * @param username es la variable que contiene el emisor del mensaje a realizar.
     * @param mensaje es la instancia de Mensaje a la que se le realizará el hilo.
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

    @Override
    public MensajeDTO searchById(Long id) {
        return MensajeDTO.byModel(mensajeRepository.findById(id).orElseThrow());
    }

    @Override
    public MensajeDTO updateValue(Long id, Object object) {
        var oldMess = mensajeRepository.findById(id).orElseThrow();
        var newMess = mapper.convertValue(object, MensajeDTO.class);

        oldMess.setDescripcion(newMess.getDescripcion());
        mensajeRepository.save(oldMess);

        return MensajeDTO.byModel(oldMess);
    }

    @Override
    public List<MensajeDTO> findMessageByTecnic(Tecnico tecnico) {
        var mensajes = new ArrayList<MensajeDTO>();
        mensajeRepository.findMensajeByTecnico(tecnico).forEach(mensaje -> mensajes.add(MensajeDTO.byModel(mensaje)));

        return mensajes;
    }

    @Override
    public List<MensajeDTO> findMessageByAdmin(Admin admin) {
        var mensajes = new ArrayList<MensajeDTO>();
        mensajeRepository.findMensajeByAdmin(admin).forEach(mensaje -> mensajes.add(MensajeDTO.byModel(mensaje)));
        return mensajes;
    }

    @Override
    public List<MensajeDTO> getAll() {
        var messages = new ArrayList<MensajeDTO>();
        mensajeRepository.findAll().forEach(mensaje -> messages.add(MensajeDTO.byModel(mensaje)));

        return messages;
    }


    /**
     * Metodo que gestionara el posteo de mensajes entre el Admin y el Tecnico a modo de hilo conversacional.
     * @param mensaje es la instancia de Mensaje a la que se le realizará el hilo.
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

    public boolean isExistence(Long id){
        return mensajeRepository.findById(id).isPresent();
    }
}
