package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.dto.MensajeDTO;
import com.ioc.dam_final_project.dto.UserDTO;
import com.ioc.dam_final_project.model.Enums.Rol;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.model.User;
import com.ioc.dam_final_project.repository.*;
import com.ioc.dam_final_project.service.UserService;
import com.ioc.dam_final_project.tools.Constantes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, Constantes {

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
                case TAREAS -> {
                    return Collections.singletonList(tareaService.total());
                }
                case UBICACIONES -> {
                    return Collections.singletonList(ubicacionService.getAll());
                }
                case COORDENADAS -> {
                    return Collections.singletonList(coordenadaService.coordenas());
                }
                case MENSAJES -> {
                    return Collections.singletonList(mensajeService); // to implementade on message services
                }
                case TECNICOS -> {
                    return Collections.singletonList(tecnicoServiceimpl.getAll());
                }
            }
        } else {
            return Collections.singletonList(tareaService.getTareaTec((Tecnico) user)); // to implementade tarea
        }

        return null;
    }

    @Override
    public UserDTO update(String old, UserDTO userDTO) {
        var user = userRepository.findUserByEmail(old).orElseThrow();

        switch (user.getRol()){
            case ADMIN, TECNIC -> { // TODO Aplicar el filtro a update específicos, preguntar qué valores van a editar y difieren
                var oldUser = userRepository.findById(user.getId()).orElseThrow();
                var userNew = User.byDTO(userDTO);
                return updateUser(oldUser, userNew);
            }
        }
        return null;
    }

    //TODO VALORAR LOS CAMPOS QUE SE VAN A PODER SETTEAR POR PERFILES
    private UserDTO updateUser(User oldUser, User newUser) {
        oldUser.setUser(newUser.getUser());
        oldUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
        oldUser.setNombre(newUser.getNombre());
        oldUser.setApellido(newUser.getApellido());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setTelefono(newUser.getTelefono());
        //oldUser.setRol(newUser.getRol());

        userRepository.save(oldUser);

        return UserDTO.byEntity(oldUser);
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
    public Object updateTar(Long id, Object object) throws Exception {
        return null;
    }

    @Override
    public void deleteRegister(String rol, String typus, Long id) {
        var user = userRepository.findUserByEmail(rol).orElseThrow();

        if (user.getRol() == Rol.ADMIN) {
            switch (typus) {
                case TAREA -> {
                    tareaService.deleteEntity(id);
                }
                case UBICACION -> {
                    ubicacionService.deleteEntity(id);
                }
                case COORDENADA -> {
                    coordenadaService.deleteEntity(id);
                }
                case MENSAJE -> {
                    mensajeService.deleteEntity(id);
                }
                case TECNICO -> {  // CHEQUEAR SI ADMIN PUEDE HACER UPDATE DE USER
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

    @Override
    public Object searchById(String value, Long id) {
        switch (value) {
            case TAREA -> {
                return tareaService.searchById(id);
            }
            case UBICACION -> {
                return ubicacionService.searchById(id);
            }
            case COORDENADA -> {
                return coordenadaService.searchById(id);
            }
            case MENSAJE -> {
                return mensajeService.searchById(id);
            }
            case TECNICO -> {  // CHEQUEAR SI ADMIN PUEDE HACER UPDATE DE USER
                return userRepository.findById(id).orElseThrow();
            }
        }
        return null;
    }


}
