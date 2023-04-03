package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.dto.MensajeDTO;
import com.ioc.dam_final_project.model.Enums.Rol;
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
    private final UbicacionServiceImpl ubicacionService;
    private final UserRepository userRepository;


    public UserServiceImpl(TecnicoServiceimpl tecnicoServiceimpl, AdminServiceImpl adminService, TareaServiceImpl tareaService, CoordenadaServiceImpl coordenadaService, MensajeServiceImpl mensajeService, MensajeServiceImpl mensajeService1, UbicacionServiceImpl ubicacionService, UserRepository userRepository) {
        this.tecnicoServiceimpl = tecnicoServiceimpl;
        this.adminService = adminService;
        this.tareaService = tareaService;
        this.coordenadaService = coordenadaService;
        this.mensajeService = mensajeService1;
        this.ubicacionService = ubicacionService;
        this.userRepository = userRepository;
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
    /*@Override
    public Object update(String username, Object object) throws Exception {
    //public Object update(String username, String value, Long id, Object object) throws Exception {
        var user = userRepository.findUserByEmail(username).orElseThrow();

        if(user.getRol() == Rol.ADMIN){
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
                    return tecnicoServiceimpl.update(user.getId(), object);
                }
            }
        }
        else {
        return tecnicoServiceimpl.update(user.getId(), object); // to implementade tarea
        }

        return null;
    }*/

    @Override
    public Object updateTec(String username, Object object) throws Exception {
        var user = userRepository.findUserByEmail(username).orElseThrow();

        return tecnicoServiceimpl.update(user.getId(), object); // to implementade tarea
    }

    @Override
    public Object updateTar(Long id, Object object) throws Exception {
        return null;
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
    public MensajeDTO postingMessage(String user, MensajeDTO mensajeDTO) {
        return mensajeService.postMessage(user, mensajeDTO);
    }


}
