package com.ioc.dam_final_project.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ioc.dam_final_project.dto.*;
import com.ioc.dam_final_project.model.*;
import com.ioc.dam_final_project.model.Enums.Rol;
import com.ioc.dam_final_project.repository.*;
import com.ioc.dam_final_project.service.UserService;
import com.ioc.dam_final_project.tools.Constantes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, Constantes {

    // INYECCION DE DEPENDENCIAS
    /**
     * Servicio de Tecnico a implementar para retornar valores centralizados en éste services.
     */
    private final TecnicoServiceimpl tecnicoServiceimpl;
    /**
     * Servicio de Admin a implementar para retornar valores centralizados en éste services.
     */
    private final AdminServiceImpl adminService;
    /**
     * Servicio de Tarea a implementar para retornar valores centralizados en éste services.
     */
    private final TareaServiceImpl tareaService;
    /**
     * Servicio de Coordenada a implementar para retornar valores centralizados en éste services.
     */
    private final CoordenadaServiceImpl coordenadaService;
    /**
     * Servicio de Mensaje a implementar para retornar valores centralizados en éste services.
     */
    private final MensajeServiceImpl mensajeService;
    /**
     * Servicio de Ubicacion a implementar para retornar valores centralizados en éste services.
     */
    private final UbicacionServiceImpl ubicacionService;
    /**
     * Repositorio de User a implementar para retornar valores centralizados en éste services.
     */
    private final UserRepository userRepository;


    /**
     * Constructor con 7 parametros
     * @param tecnicoServiceimpl en referencia al Service de Tecnico
     * @param adminService en referencia al Service de Admin
     * @param tareaService en referencia al Service de Tarea
     * @param coordenadaService en referencia al Service de Coordenada
     * @param mensajeService en referencia al Service de Mensaje
     * @param ubicacionService en referencia al Service de Ubicacion
     * @param userRepository en referencia al Repository de User
     */
    public UserServiceImpl(TecnicoServiceimpl tecnicoServiceimpl, AdminServiceImpl adminService, TareaServiceImpl tareaService, CoordenadaServiceImpl coordenadaService, MensajeServiceImpl mensajeService, MensajeServiceImpl mensajeService1, UbicacionServiceImpl ubicacionService, UserRepository userRepository) {
        this.tecnicoServiceimpl = tecnicoServiceimpl;
        this.adminService = adminService;
        this.tareaService = tareaService;
        this.coordenadaService = coordenadaService;
        this.mensajeService = mensajeService1;
        this.ubicacionService = ubicacionService;
        this.userRepository = userRepository;
    }

    /*************************************************************
     *                   CREATING REGISTER INTO DATABASE    -       CREATE
     * ***********************************************************/



    /** Metodo 'addNewTar()'
     * Recibe 3 parametros : username para validar el usuario admin a definir en la creacion, id del tecnico a indicar en la tarea, Objeto a crear
     * @return <ul>
     *  <li>Entity : Registro de la tarea en la database</li>
     *  </ul>
     */
    @Override
    public Object addNewTar(String username, Long id, Object object) {
        return tareaService.saveObject(username, id, object);
    }

    /** Metodo 'addNewUbicacion()'
     * Recibe 2 parametros : Objeto a crear, Id de la tarea a la que se le asignará para establecer la relación
     * @return <ul>
     *  <li>Entity : Registro de la ubicacion en la database</li>
     *  </ul>
     */
    @Override
    public Ubicacion addNewUbicacion(Ubicacion ubicacion, Long id) {
        return ubicacionService.saveObject(ubicacion, id);
    }

    /** Metodo 'addNewCoor()'
     * Recibe 2 parametros : Objeto a crear, Id de la ubicacion a la que se le asignará para establecer la relación
     * @return <ul>
     *  <li>Entity : Registro de la coordenada en la database</li>
     *  </ul>
     */
    @Override
    public CoordenadaDTO addNewCoor(Coordenada coordenada, Long ubicacion) {
        return coordenadaService.saveObject(coordenada, ubicacion);
    }

    /** Metodo 'postingMessage()'
     * Recibe 2 parametros : username para validar el rol del usuario, mensaje contenido a postear
     * @return <ul>
     *  <li>String : con respuesta de la operacion exitosa</li>
     *  </ul>
     */
    @Override
    public MensajeDTO postingMessage(String user, MensajeDTO mensajeDTO) {
        return mensajeService.postMessage(user, mensajeDTO);
    }


    /*************************************************************
     *                   READING  REGISTER IN THE DATABASE    -       READ
     * ***********************************************************/

    /** Metodo getProfile
     * Recibe un parametro que contiene el username del usuario y devuelve un objeto con el perfil de la persona en la sesión autenticada
     * @return <ul>
     *  <li>Entity: Según el rol devuelve el perfil del usuario</li>
     *  </ul>
     */
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

    /** Metodo registers
     * Recibe 2 parametros : el username del usuario, para validar el rol, y el tipo de entidad a ubicar en la respuesta. Devuelve una lista de elementos contenidos en la base de datos, realiza un switch para devolver la respuesta según cada clase demandada
     * @return <ul>
     *  <li>Lista de Entidades: Según el rol administra el acceso a todos los valores contenidos</li>
     *  </ul>
     */
    @Override
    public List<Object> registers(String username, String value) {
        var usuari = userRepository.findUserByEmail(username).orElseThrow();

        if (usuari.getRol() == Rol.ADMIN) {
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
                case MENSAJES -> { // todo, corresponde a todos los mensajes contenidos en la base de datos
                    return Collections.singletonList(mensajeService.getAll()); // to implementade on message services
                }
                case MENSAJE -> { // todo, éste equivale a la lista de los mensajes que ha realizado el admin
                    return Collections.singletonList(mensajeService.findMessageByAdmin((Admin) usuari)); // to implementade on message services
                }
                case TECNICOS -> {
                    return Collections.singletonList(tecnicoServiceimpl.getAll());
                }
                case ADMINS -> {
                    return Collections.singletonList(adminService.getAll());
                }
                case USERS -> { // TODO, CHEQUEAR USERS GENERIC
                    var users = new ArrayList<UserDTO>();
                    userRepository.findAll().forEach(user -> users.add(UserDTO.byEntity(user)));
                    return Collections.singletonList(users);
                }
            }
        } else {
            switch (value) {
                case MENSAJES -> { // todo, implementar el listado de mensajes a retornar en el services de mensajes
                    return Collections.singletonList(mensajeService.findMessageByTecnic((Tecnico) usuari)); // to implementade on message services
                }
                case TAREAS -> {
                    return Collections.singletonList(tareaService.getTareaByTecnico((Tecnico) usuari));
                }
            }
        }

        return null;
    }



    /** Metodo 'searchById()'
     * Recibe 2 parametros : value para validar el nombre de la entidad dentro del switch, id para realizar el llamado al service de esa entidad con el id
     * @return <ul>
     *  <li>String : con respuesta de la operacion exitosa</li>
     *  </ul>
     */
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
            case USER -> {  // CHEQUEAR SI ADMIN PUEDE HACER UPDATE DE USER // TODO, VERIFICAR SI LA BÚSQUEDA SE NECESITA ESPECÍFICAR A ADMIN O A TÉCNICO
                return UserDTO.byEntity(userRepository.findById(id).orElseThrow());
            }
        }
        return null;
    }


    /**
     * Metodo 'findTaskByTecnic'
     * Recibe 1 parametro: Username del tecnico al que se retornara la lista de Tareas asignadas
     *
     * @return <ul>
     * <li>Entity : Registro de la coordenada en la database</li>
     * </ul>
     */
    @Override
    public List<Object> findTaskByTecnic(String username) {
        var tecnico = userRepository.findUserByUser(username).orElseThrow();

        return Collections.singletonList(tareaService.getTareaByTecnico((Tecnico) tecnico));
    }

    @Override
    public boolean checkLocation(Long idTarea) {
        return ubicacionService.checkTarea(idTarea);
    }


    /** Metodo 'isRegistered()'
     * Recibe 2 parametros : definicion de la clase a la cual validar el id, Id de la clase a la cual se validara la existencia en la base de datos
     * @return <ul>
     *  <li>Boolean : True o False segun aplique el caso</li>
     *  </ul>
     */
    public boolean isRegistered(String value, Long id){
        switch (value){
            case COORDENADA -> {
                return coordenadaService.isExistence(id);
            }
            case MENSAJE -> {
                return mensajeService.isExistence(id);
            }
            case TAREA -> {
                return tareaService.isExistence(id);
            }
            case USER , TECNICO, ADMIN -> {
                return userRepository.findById(id).isPresent();
            }
            case UBICACION -> {
                return ubicacionService.isExistence(id);
            }
        }
        return false;
    }


    /*************************************************************
     *                   UPDATING REGISTER IN THE DATABASE    -       UPDATE
     * ***********************************************************/

    /** Metodo update
     * Recibe 2 argumentos : username del usuario a realizar el update y los datos nuevos a actualizar. Actualiza un objeto 'Entity' en la base de datos
     * @return <ul>
     *  <li>Entity: valida segun el caso de uso y gestiona el update de dicha entidad con los nuevos valores</li>
     *  </ul>
     */
    @Override
    public UserDTO update(String old, Object userDTO) {
        var user = userRepository.findUserByEmail(old).orElseThrow();

        switch (user.getRol()){
            case ADMIN, TECNIC -> { // TODO Aplicar el filtro a update específicos, preguntar qué valores van a editar y difieren
                var oldUser = userRepository.findById(user.getId()).orElseThrow();
                var userNew = mapper.convertValue(userDTO, UserDTO.class);
                return updateUser(oldUser, User.byDTO(userNew));
            }
        }
        return null;
    }

    //TODO VALORAR LOS CAMPOS QUE SE VAN A PODER SETTEAR POR PERFILES
    /** Metodo privado updateUser
     * Recibe 2 argumentos : Usuario antiguo y Usuario Nuevo con todos los campos a settear. Actualiza un objeto 'Entity' en la base de datos
     * @return <ul>
     *  <li>Entity: valida segun el caso de uso y gestiona el update de dicha entidad con los nuevos valores</li>
     *  </ul>
     */
    private UserDTO updateUser(User oldUser, User newUser) {
        oldUser.setUser(newUser.getUser());

        // CONDICIONAL A ESTABLECER NUEVA CONTRASEÑA EN CASO DE APLICAR.
        if(newUser.getPassword() != null){
            oldUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
        }
        oldUser.setNombre(newUser.getNombre());
        oldUser.setApellido(newUser.getApellido());
        if(oldUser.getRol() == Rol.ADMIN){
            oldUser.setRol(newUser.getRol());
        }
        oldUser.setTelefono(newUser.getTelefono());

        userRepository.save(oldUser);

        return UserDTO.byEntity(oldUser);
    }


    /** Metodo updateValue
     * Recibe 4 parametros : el usuario para validar el rol, el valor de la entidad a apuntar dicho cambio, el id especifico de la entidad a actualizar y el Objeto con los nuevos valores contenidos. Actualiza un objeto 'Entity' en la base de datos
     * @return <ul>
     *  <li>Entity: valida segun el caso de uso y gestiona el update de dicha entidad con los nuevos valores</li>
     *  </ul>
     */
    @Override
    public Object updateValue(String username, String value, Long id, Object object) throws JsonProcessingException {
        var user = userRepository.findUserByEmail(username).orElseThrow();

        if (user.getRol() == Rol.ADMIN) {
            switch (value) {
                case TAREA -> {
                    return tareaService.updateValue(id, object);
                }
                case UBICACION -> {
                    return ubicacionService.updateValue(id, object);
                }
                case COORDENADA -> {
                    return coordenadaService.updateValue(id, object);
                }
                case MENSAJE -> {
                    return mensajeService.updateValue(id, object); // to implementade on message services
                }
                case TECNICO, ADMIN -> {  // CHEQUEAR SI ADMIN PUEDE HACER UPDATE DE USER
                    return update(id,  object);
                }
            }
        }

        return null; // to implementade tarea
    }


    public UserDTO update(Long id, Object object) {
        var oldUser = userRepository.findById(id).orElseThrow();
        var userNew = mapper.convertValue(object, UserDTO.class);

        return updateUser(oldUser, User.byDTO(userNew));
    }


    /*************************************************************
     *                   DELETING REGISTER FROM DATABASE    -       DELETE
     * ***********************************************************/

    /** Metodo 'deleteRegister()'
     * Recibe 3 parametros : username para validar el rol del usuario, typus para definir la entidad dentro del switch y el id al que se eliminara en la base de datos. Elimina un registro en la base de datos segun el id validado
     * @return <ul>
     *  <li>String : con respuesta de la operacion exitosa</li>
     *  </ul>
     */
    @Override
    public Object deleteRegister(String rol, String typus, Long id) {
        var user = userRepository.findUserByEmail(rol).orElseThrow();

        if (user.getRol() == Rol.ADMIN) {
            switch (typus) {
                case TAREA -> {
                    tareaService.deleteEntity(id);
                    return "Registro eliminado";
                }
                case UBICACION -> {
                    ubicacionService.deleteEntity(id);
                    return "Registro eliminado";
                }
                case COORDENADA -> {
                    coordenadaService.deleteEntity(id);
                    return "Registro eliminado";
                }
                case MENSAJE -> {
                    mensajeService.deleteEntity(id);
                    return "Registro eliminado";
                }
                case USER -> {  // TODO, CHEQUEAR EL DELETE DEFINIDO POR ADMIN CHEQUEAR SI ADMIN PUEDE HACER UPDATE DE USER
                    userRepository.deleteById(id);
                    return "Registro eliminado";
                }
            }
        }
        else {
            mensajeService.deleteEntity(id);
            return "Registro eliminado";
        }
        return "No hay registros con estos valores";
    }



}
