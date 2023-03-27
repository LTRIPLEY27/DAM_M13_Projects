package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.dto.MensajeDTO;
import com.ioc.dam_final_project.model.Admin;
import com.ioc.dam_final_project.model.Enums.Rol;
import com.ioc.dam_final_project.model.Mensaje;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.repository.*;
import com.ioc.dam_final_project.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final TecnicoServiceimpl tecnicoServiceimpl;
    private final AdminServiceImpl adminService;
    private final TareaServiceImpl tareaService;
    private final CoordenadaServiceImpl coordenadaService;
    private final MensajeServiceImpl mensajeService;
    //private final UserServiceImpl userService;
    private final UbicacionServiceImpl ubicacionService;
    private final UserRepository userRepository;
    private final TareaRepository repository;
    private final AdminRepository adminRepository;
    private  final TecnicoRepository tecnicoRepository;
    private final MensajeRepository mensajeRepository;


    public UserServiceImpl(TecnicoServiceimpl tecnicoServiceimpl, AdminServiceImpl adminService, TareaServiceImpl tareaService, CoordenadaServiceImpl coordenadaService, MensajeServiceImpl mensajeService, /*UserServiceImpl userService,*/ UbicacionServiceImpl ubicacionService, UserRepository userRepository, TareaRepository repository, AdminRepository adminRepository, TecnicoRepository tecnicoRepository, MensajeRepository mensajeRepository) {
        this.tecnicoServiceimpl = tecnicoServiceimpl;
        this.adminService = adminService;
        this.tareaService = tareaService;
        this.coordenadaService = coordenadaService;
        this.mensajeService = mensajeService;
        //this.userService = userService;
        this.ubicacionService = ubicacionService;
        this.userRepository = userRepository;
        this.repository = repository;
        this.adminRepository = adminRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.mensajeRepository = mensajeRepository;
    }


    @Override
    public Object getProfile(String username) {

        var user = userRepository.findUserByEmail(username).orElseThrow();

        switch (user.getRol()) {
            case ADMIN -> {
                return adminService.profile(username);
            }
            case TECNIC -> {
                return tecnicoServiceimpl.myProfile(username);
            }
        }
        return null;
    }

    @Override
    public List<Object> registers(String username, String value) {
        var user = userRepository.findUserByEmail(username).orElseThrow();

        if (user.getRol() == Rol.ADMIN) {
            switch (value) {
                case "TAREA" -> {
                    return Collections.singletonList(tareaService.total());
                }
                case "UBICACION" -> {
                    return Collections.singletonList(ubicacionService.getAll());
                }
                case "COORDENADA" -> {
                    return Collections.singletonList(coordenadaService.coordenas());
                }
                case "MENSAJE" -> {
                    return Collections.singletonList(mensajeService); // to implementade on message services
                }
                case "TECNICO" -> {
                    return Collections.singletonList(tecnicoServiceimpl.getAll());
                }
            }
        } else {
            return Collections.singletonList(tareaService.getTareaTec((Tecnico) user)); // to implementade tarea
        }

        return null;
    }

    // podria reutilizarse con admin
    @Override
    public Object update(String username, Object object) throws Exception {
        var user = userRepository.findUserByEmail(username).orElseThrow();

        /*if(user.getRol() == Rol.ADMIN){
            switch (value) {
                case "TAREA" -> {
                    return user;
                }
                case "UBICACION" -> {
                    return user;
                }
                case "COORDENADA" -> {
                    return user;
                }
                case "MENSAJE" -> {
                    return user; // to implementade on message services
                }
                case "TECNICO" -> {  // CHEQUEAR SI ADMIN PUEDE HACER UPDATE DE USER
                    return user;
                }
            }
        }
        else {*/
        return tecnicoServiceimpl.update(user.getId(), object); // to implementade tarea
        //}

        //return null;
    }

    @Override
    public void deleteRegister(String rol, String typus, Long id) {
        var user = userRepository.findUserByEmail(rol).orElseThrow();

        if (user.getRol() == Rol.ADMIN) {
            switch (typus) {
                case "TAREA" -> {
                    tareaService.deleteEntity(id);
                }
                case "UBICACION" -> {
                    ubicacionService.deleteEntity(id);
                }
                case "COORDENADA" -> {
                    coordenadaService.deleteEntity(id);
                }
                case "MENSAJE" -> {
                    mensajeService.deleteEntity(id);
                }
                case "TECNICO" -> {  // CHEQUEAR SI ADMIN PUEDE HACER UPDATE DE USER
                    tecnicoServiceimpl.deleteEntity(id);
                }
            }
        }
        else {
            mensajeService.deleteEntity(id);
        }
    }

    @Override
    public MensajeDTO postingMessage(String username, MensajeDTO mensaje) {

        var user = userRepository.findUserByEmail(username).orElseThrow();

        switch (user.getRol()){
            case ADMIN -> {
                var object = (Tecnico) userRepository.findUserByUser(mensaje.getTecnico()).orElseThrow();
                var tarea = repository.findTareaByAdminAndName((Admin) user, mensaje.getTarea()); //name must be unique
                var byModel = new Mensaje(mensaje.getDescripcion(), tarea, object,(Admin) user);
                tarea.getMensaje().add(byModel);
                object.getMensaje().add(byModel);
                tecnicoRepository.save(object);
                adminRepository.save((Admin) user);
                repository.save(tarea);
                mensajeRepository.save(byModel);

                var dto = MensajeDTO.byModel(byModel);
                return dto;
            }
            case TECNIC -> {
                var object = (Admin) userRepository.findUserByUser(mensaje.getAdmin()).orElseThrow();
                var tarea = repository.findTareaByTecnicoAndName((Tecnico) user, mensaje.getTarea()); //name must be unique
                var byModel = new Mensaje(mensaje.getDescripcion(), tarea, (Tecnico) user, object);
                tarea.getMensaje().add(byModel);
                object.getMensaje().add(byModel);
                adminRepository.save(object);
                tecnicoRepository.save((Tecnico) user);
                repository.save(tarea);
                mensajeRepository.save(byModel);

                var dto = MensajeDTO.byModel(byModel);
                return dto;
            }
        }

        return null;
    }

}
