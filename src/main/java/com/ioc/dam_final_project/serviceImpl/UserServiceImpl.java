package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.model.Enums.Rol;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.repository.UserRepository;
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


    public UserServiceImpl(TecnicoServiceimpl tecnicoServiceimpl, AdminServiceImpl adminService, TareaServiceImpl tareaService, CoordenadaServiceImpl coordenadaService, MensajeServiceImpl mensajeService, /*UserServiceImpl userService,*/ UbicacionServiceImpl ubicacionService, UserRepository userRepository) {
        this.tecnicoServiceimpl = tecnicoServiceimpl;
        this.adminService = adminService;
        this.tareaService = tareaService;
        this.coordenadaService = coordenadaService;
        this.mensajeService = mensajeService;
        //this.userService = userService;
        this.ubicacionService = ubicacionService;
        this.userRepository = userRepository;
    }


    @Override
    public Object getProfile(String username) {

        var user = userRepository.findUserByEmail(username).orElseThrow();

        switch (user.getRol()){
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

        if(user.getRol() == Rol.ADMIN){
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
        }
        else {
            return Collections.singletonList(tareaService.getTareaTec((Tecnico) user)); // to implementade tarea
        }

        return null;
    }
}
