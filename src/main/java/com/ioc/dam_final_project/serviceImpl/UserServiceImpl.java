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

/**
 * CLASE UserServiceImpl
 *
 * SERA UNA CLASE DEL TIPO 'SERVICES', FUNCIONARA COMO UN ROOT-SERVICES PARA CENTRALIZAR TODOS LOS SERVICES A RESPONDER EN EL ROOTCONTROLLER,
 * DICHA APLICACION PROPORCIONARA UN MAYOR CONTROL SOBRE POTENCIALES ERRORES, ACCESOS INDEBIDOS Y POTENCIARA EL ACOPLAMIENTRO ENTRE SERVICES Y CLASES .
 *
 * Implementa 2 Interfaces : 'UserService' y 'Constantes'.
 *
 * 'UserService' : Interface de la clase User, contiene todos los protocolos que se deberan de implementar desde el Services y que centralizan a su vez, los de los demas.
 * 'Constantes' : Interface que almacena multiples variables de tipo estatico a usar, y que potencia la eliminacion de errores y eficiencia de codigo
 *
 *   Notaciones:
 *
 *  - He declarado a la clase como 'Services' para su mappeo en la base de datos.
 *  - He declarado a la clase con un 'Qualifier' para identificar la especificidad de la misma, y potenciando la reutilizacion e implementacion de multiples Services a los cuales deberan de hacer referencia los paths derivados.
 *  - He usado las notaciones propias de SpringBoot, en combinacion a Java 17 y Loombook, para potenciar al maximo la codificacion.
 *
 *   Atributos:
 *
 * - He declarado como 'Inyeccion de Dependencias' a los atributos services que seran devueltos como respuesta al cliente.
 * - He declarado los atributos: Private, ya que seran de acceso privado de clase.
 *
 *  @author Isabel Calzadilla
 *  @version 1.0
 *  @see UserRepository para la implementacion del almacenaje y operaciones de Usuario
 *  @see AdminServiceImpl para la implementacion y acoplamiento centralizado de los metodos inherentes a Admin.
 *  @see TecnicoServiceimpl para la implementacion y acoplamiento centralizado de los metodos inherentes a Tecnico.
 *  @see TareaServiceImpl para la implementacion y acoplamiento centralizado de los metodos inherentes a Tarea.
 *  @see CoordenadaServiceImpl para la implementacion y acoplamiento centralizado de los metodos inherentes a Coordenada.
 *  @see MensajeServiceImpl para la implementacion y acoplamiento centralizado de los metodos inherentes a Mensaje.
 *  @see UbicacionServiceImpl para la implementacion y acoplamiento centralizado de los metodos inherentes a Ubicacion.
 *  @see UserService para la implementacion y acoplamiento centralizado de los metodos inherentes a la Interface de User.
 *  @see Constantes para la implementacion y acoplamiento centralizado de las variables constantes a usar, potenciando el control y asegurando en mayor medida el manejo de errores.
 * */
@Service
public class UserServiceImpl implements UserService, Constantes {

    // INYECCION DE DEPENDENCIAS
    /**
     * Servicio de Tecnico a implementar para retornar valores centralizados en este services.
     */
    private final TecnicoServiceimpl tecnicoServiceimpl;
    /**
     * Servicio de Admin a implementar para retornar valores centralizados en este services.
     */
    private final AdminServiceImpl adminService;
    /**
     * Servicio de Tarea a implementar para retornar valores centralizados en este services.
     */
    private final TareaServiceImpl tareaService;
    /**
     * Servicio de Coordenada a implementar para retornar valores centralizados en este services.
     */
    private final CoordenadaServiceImpl coordenadaService;
    /**
     * Servicio de Mensaje a implementar para retornar valores centralizados en este services.
     */
    private final MensajeServiceImpl mensajeService;
    /**
     * Servicio de Ubicacion a implementar para retornar valores centralizados en este services.
     */
    private final UbicacionServiceImpl ubicacionService;
    /**
     * Repositorio de User a implementar para retornar valores centralizados en este services.
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
     * Recibe 3 parametros: username para validar el usuario admin a definir en la creacion, id del tecnico a indicar en la tarea, Objeto a crear
     * @return <ul>
     *  <li>Entity : Registro de la tarea en la database</li>
     *  </ul>
     */
    @Override
    public Object addNewTar(String username, Long id, Object object) {
        return tareaService.saveObject(username, id, object);
    }

    /** Metodo 'addNewUbicacion()'
     * Recibe 2 parametros : Objeto a crear, Id de la tarea a la que se le asignara para establecer la relacion
     * @return <ul>
     *  <li>Entity : Registro de la ubicacion en la database</li>
     *  </ul>
     */
    @Override
    public UbicacionDTO addNewUbicacion(Ubicacion ubicacion, Long id) {
        return ubicacionService.saveObject(ubicacion, id);
    }

    /** Metodo 'addNewCoor()'
     * Recibe 2 parametros : Objeto a crear, Id de la ubicacion a la que se le asignara para establecer la relacian
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
     * Recibe un parametro que contiene el username del usuario y devuelve un objeto con el perfil de la persona en la sesion autenticada
     * @return <ul>
     *  <li>Entity: Segun el rol devuelve el perfil del usuario</li>
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
     * Recibe 2 parametros : el username del usuario, para validar el rol, y el tipo de entidad a ubicar en la respuesta. Devuelve una lista de elementos contenidos en la base de datos, realiza un switch para devolver la respuesta segun cada clase demandada
     * @return <ul>
     *  <li>Lista de Entidades: Segun el rol administra el acceso a todos los valores contenidos</li>
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
                    return Collections.singletonList(mensajeService.getAll());
                }
                case MENSAJE -> { // todo, este equivale a la lista de los mensajes que ha realizado el admin
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
            case USER -> {  // CHEQUEAR SI ADMIN PUEDE HACER UPDATE DE USER // TODO, VERIFICAR SI LA BUSQUEDA SE NECESITA ESPECIFICAR A ADMIN O A TECNICO
                return UserDTO.byEntity(userRepository.findById(id).orElseThrow());
            }
        }
        return null;
    }


    /**
     * Metodo 'filterByValue'
     * Recibe 2 parametros:
     *
     * Valor del campo especifico a filtrar (Username / Nombre),  y la cadena especifica de la misma
     *
     * @return <ul>
     * <li>Entity : Registro de la coordenada en la database</li>
     * </ul>
     */
    @Override
    public List<Object> filterByValue(String value, String object) {

        if (value.equals(USER)) {
            return Collections.singletonList(tareaService.getTareaByUser(object));
        }

        return Collections.singletonList(tareaService.getTareaByName(object));
    }

    // todo, en prueba tea4
    /**
     * Metodo 'filterByDates'
     * Recibe 2 parametros:
     *
     * Valor del campo especifico a filtrar (fecha1 / fecha2),  y la cadena especifica de la misma
     *
     * @return <ul>
     * <li>List : Registro los Mensajes en ese rango de fecha</li>
     * </ul>
     */
    @Override
    public List<Object> filterByDates(String date1, String date2) {

        return Collections.singletonList(mensajeService.filteringByDates(date1, date2));
    }

    // todo, en prueba tea4
    /**
     * Metodo 'filterByTareaDates'
     * Recibe 2 parametros:
     *
     * Valor del campo especifico a filtrar (fecha1 / fecha2),  y la cadena especifica de la misma
     *
     * @return <ul>
     * <li>List : Registro las Tareas en ese rango de fecha</li>
     * </ul>
     */
    @Override
    public List<Object> filterByTareaDates(String fecha, String date1, String date2) {

        return Collections.singletonList(tareaService.filteringByDates(fecha, date1, date2));
    }

    /**
     * Metodo 'filterByTareaDatesAndRol'
     * Recibe 4 parametros:
     *
     * Valor del Usuario, tipo de fecha, campo especifico a filtrar (fecha1 / fecha2),  y la cadena especifica de la misma
     *
     * @return <ul>
     * <li>List : Registro de las Tareas en ese rango de fecha y en relación al Usuario especifico</li>
     * </ul>
     */
    @Override
    public List<Object> filterByTareaDatesAndRol(User user, String fecha, String date1, String date2) {

        return Collections.singletonList(tareaService.filteringByDatesAndRol(user, fecha, date1, date2));
    }

    /**
     * Metodo 'filterByMensajeDatesAndRol'
     * Recibe 3 parametros:
     *
     * Valor del Usuario especifico a filtrar por Rol, valor del campo especifico a filtrar (fecha1 / fecha2),  y la cadena especifica de la misma
     *
     * @return <ul>
     * <li>List : Registro las Mensajes por Usuario en ese rango de fecha</li>
     * </ul>
     */
    @Override
    public List<Object> filterByMensajeDatesAndRol(User user, String date1, String date2) {

        if(user.getRol() != Rol.ADMIN){
            return Collections.singletonList(mensajeService.filteringByDatesAndTecnic(user.getId(), date1, date2));
        }

        return Collections.singletonList(mensajeService.filteringByDatesAndAdmin(user.getId(), date1, date2));
    }

    /**
     * Metodo 'getTaskByStatus'
     * Recibe 1 parametro:
     *
     * Valor del campo especifico a filtrar (Estatus),  y la cadena especifica de la misma
     *
     * @return <ul>
     * <li>List : Registro de tareas en ese estatus, por tecnico y cantidad</li>
     * </ul>
     */
    @Override
    public List<Object> getTaskByStatus(String estatus) {

        return Collections.singletonList(tareaService.getByTecnicsAndStatus(estatus));
    }

    /**
     * Metodo 'getAllTaskByStatus'
     * Recibe 1 parametro:
     *
     * Valor del campo especifico a filtrar (Estatus),  y la cadena especifica de la misma
     *
     * @return <ul>
     * <li>List : Registro de todas las tareas en ese estatus</li>
     * </ul>
     */
    @Override
    public List<Object> getAllTaskByStatus(String estatus) {

        return Collections.singletonList(tareaService.getByTaskByStatus(estatus));
    }

    /**
     * Metodo 'filteringByStatusAndRol'
     * Recibe 2 parametros:
     *
     * Valor del Usuario Logueado, valor del campo especifico a filtrar (Estatus),  y la cadena especifica de la misma
     *
     * @return <ul>
     * <li>List : Registro de tareas en ese estatus, relativo al Usuario que se encuentre Logueado</li>
     * </ul>
     */
    @Override
    public List<Object> filteringByStatusAndRol(User user, String estatus) {

        return Collections.singletonList(tareaService.filteringByStatusAndRol(user, estatus));
    }

    /**
     * Metodo 'filteringByTaskTypeAndRol'
     * Recibe 2 parametros:
     *
     * Valor del Usuario Logueado, valor del campo especifico a filtrar (Tipo Tarea),  y la cadena especifica de la misma
     *
     * @return <ul>
     * <li>List : Registro de tareas en ese Tipo Tarea, relativas al Usuario que se encuentre Logueado</li>
     * </ul>
     */
    @Override
    public List<Object> filteringByTaskTypeAndRol(User user, String task){

        return Collections.singletonList(tareaService.filteringByTaskTypeAndRol(user, task));
    }

    /**
     * Metodo 'getByAllByTareaType'
     * Recibe 1 parametro:
     *
     * Valor del campo especifico a filtrar (Tipo Tarea),  y la cadena especifica de la misma
     *
     * @return <ul>
     * <li>List : Registro de todas las tareas en ese Tipo Tarea</li>
     * </ul>
     */
    @Override
    public List<Object> getByAllByTareaType(String task) {

        return Collections.singletonList(tareaService.getByTaskByTareaType(task));
    }

    /**
     * Metodo 'getByAllByTareaTypeQuantity'
     * Recibe 1 parametro:
     *
     * Valor del campo especifico a filtrar (Tipo Tarea),  y la cadena especifica de la misma
     *
     * @return <ul>
     * <li>List : Registro de todas las tareas en ese Tipo Tarea, agrupadas por cantidad y tecnico</li>
     * </ul>
     */
    @Override
    public List<Object> getByAllByTareaTypeQuantity(String task) {

        return Collections.singletonList(tareaService.getByTecnicsAndSTaskType(task));
    }

    /**
     * Metodo 'getByAllByTareaTypeQuantity'
     * Recibe 1 parametro:
     *
     * Valor del campo especifico a filtrar (Tipo Tarea),  y la cadena especifica de la misma
     *
     * @return <ul>
     * <li>List : Registro de todas las tareas en ese Tipo Tarea, agrupadas por cantidad y tecnico</li>
     * </ul>
     */
    @Override
    public List<Object> getByAllByTareaTypeQuantityAndUserLogin(User user, String task) {

        return Collections.singletonList(tareaService.getByLoginTecnicsAndTaskType(user.getUser(), task));
    }

    /**
     * Metodo 'getTaskByStatus'
     * Recibe 1 parametro:
     *
     * Valor del campo especifico a filtrar (Estatus),  y la cadena especifica de la misma
     *
     * @return <ul>
     * <li>List : Registro de tareas en ese estatus, por tecnico y cantidad</li>
     * </ul>
     */
    @Override
    public List<Object> getTaskByStatusAndTecnic(String tecnico, String estatus) {
        return Collections.singletonList(tareaService.getByLoginTecnicsAndStatus(tecnico, estatus));
    }

    /** Metodo 'checkLocation()'
     * Recibe 1 parametro: Id de la clase Ubicacion  a la cual se validara la existencia en la base de datos de alguna Tarea, ya que la relaciones 1 : 1
     * @return <ul>
     *  <li>Boolean : True o False segun aplique el caso</li>
     *  </ul>
     */
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
     * Recibe 2 argumentos: username del usuario logueado a realizar el update y los datos nuevos a actualizar. Actualiza un objeto 'Entity' en la base de datos
     * @return <ul>
     *  <li>Entity: valida segun el caso de uso y gestiona el update de dicha entidad con los nuevos valores</li>
     *  </ul>
     */
    @Override
    public Object update(String old, Object userDTO) {
        var user = userRepository.findUserByEmail(old).orElseThrow();

        switch (user.getRol()){
            case ADMIN, TECNIC -> { // TODO Aplicar el filtro a update especificos, preguntar que valores van a editar y difieren
                var oldUser = userRepository.findById(user.getId()).orElseThrow();
                var userNew = mapper.convertValue(userDTO, UserDTO.class);
                return updateUser(oldUser, User.byDTO(userNew));
            }
        }
        return null;
    }

    /** Metodo update Sobrecarga del Metodo 'update'
     * Recibe 2 argumentos: Id del usuario a realizar el update y los datos nuevos a actualizar. Actualiza un objeto 'Entity' en la base de datos
     * @return <ul>
     *  <li>Entity: Valida el Rol de Admin para realizar modificaciones sobre usuarios en la Base datos.</li>
     *  </ul>
     */
    public UserDTO update(Long id, Object object) {
        var oldUser = userRepository.findById(id).orElseThrow();
        var userNew = mapper.convertValue(object, UserDTO.class);

        return updateUser(oldUser, User.byDTO(userNew));
    }

    //TODO VALORAR LOS CAMPOS QUE SE VAN A PODER SETTEAR POR PERFILES
    /** Metodo privado updateUser
     * Recibe 2 argumentos: Usuario antiguo y Usuario Nuevo con todos los campos a settear. Actualiza un objeto 'Entity' en la base de datos, aplicando filtros a ciertos campos sensibles
     * @return <ul>
     *  <li>Entity: valida segun el caso de uso y gestiona el update de dicha entidad con los nuevos valores</li>
     *  </ul>
     */
    private UserDTO updateUser(User oldUser, User newUser) {
        oldUser.setUser(newUser.getUser());

        // CONDICIONAL A ESTABLECER NUEVA CONTRASENA EN CASO DE APLICAR.
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
     * Recibe 4 parametros: el usuario para validar el rol, el valor de la entidad a apuntar dicho cambio, el id especifico de la entidad a actualizar y el Objeto con los nuevos valores contenidos. Actualiza un objeto 'Entity' en la base de datos
     * @return <ul>
     *  <li>Entity: valida segun el caso de uso y gestiona el update de dicha entidad con los nuevos valores</li>
     *  </ul>
     */
    @Override// todo, agregar edicion de mensajes para tecnicos?
    public Object updateValue(String username, String value, Long id, Object object) throws JsonProcessingException {
        var user = userRepository.findUserByEmail(username).orElseThrow();

        if (user.getRol() == Rol.ADMIN) {
            switch (value) {
                case TAREA -> {
                    return tareaService.updateValue(id, object, user);
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
                case TECNICO, ADMIN -> {
                    return update(id,  object);
                }
            }
        }
        else {
            switch (value) {
                case TAREA -> {
                    return tareaService.updateValue(id, object, user);
                }
                case MENSAJE -> {
                    return mensajeService.updateValue(id, object); // to implementade on message services
                }
            }
        }

        return null;
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
            mensajeService.deleteEntity(id); // TODO, validar si un usuario puede o no eliminar un mensaje sin ser admin?
            return "Registro eliminado";
        }
        return "No hay registros con estos valores";
    }



}
