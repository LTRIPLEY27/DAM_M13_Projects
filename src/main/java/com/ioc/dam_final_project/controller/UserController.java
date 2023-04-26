package com.ioc.dam_final_project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ioc.dam_final_project.dto.MensajeDTO;
import com.ioc.dam_final_project.dto.TareaDTO;
import com.ioc.dam_final_project.dto.UbicacionDTO;
import com.ioc.dam_final_project.dto.UserDTO;
import com.ioc.dam_final_project.model.Coordenada;
import com.ioc.dam_final_project.model.Enums.Rol;
import com.ioc.dam_final_project.model.Tarea;
import com.ioc.dam_final_project.model.Ubicacion;
import com.ioc.dam_final_project.repository.TareaRepository;
import com.ioc.dam_final_project.repository.UserRepository;
import com.ioc.dam_final_project.security.authentication.AuthenticationService;
import com.ioc.dam_final_project.security.authentication.RegisterRequest;
import com.ioc.dam_final_project.serviceImpl.UserServiceImpl;
import com.ioc.dam_final_project.tools.Constantes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;


/**
 * CLASE USERCONTROLLER
 *
 * SERA UNA CLASE RESTCONTROLLER, ESTABLECERA LOS PATHS A USAR Y DEVOLVER UNA RESPUESTA.
 *
 *  La clase sera la RootController, con la misma se ejecutaran todos los paths en cada request, centralizando todos los services a la misma y potenciando
 *  una mayor cobertura de errores y optimizacion de codigo.
 *
 *   Notaciones:
 *
 *  - He declarado a la clase como 'Restcontroller' para su mappeo en la base de datos.
 *  - He declarado a la clase como 'RequestMapping' para identificar el pah especifico a los cuales deberán de hacer referencia los paths derivados.
 *  - He usado las notaciones propias de SpringBoot, en combinacion a Java 17 y Loombook, para potenciar al maximo la codificacion.
 *
 *   Atributos:
 *
 * - He declarado como 'Inyeccion de Dependencias' a los atributos services que serán devueltos como respuesta al cliente.
 * - He declarado los atributos: Prívate, ya que seran de acceso privado de clase.
 *
 *  @author Isabel Calzadilla
 *  @version 1.0
 *  @see AuthenticationService para la implementacion del session storage y validacion / autenticacion de Usuario
 *  @see UserServiceImpl para la implementación centralizada de las respuestas de cada Entity que le hereda.
 *  @see UserRepository para la validación del usuario On session y las excepciones.
 * */
@RestController
@RequestMapping(path = "/")
public class UserController implements Constantes {

    // INYECCION DE DEPENDENCIAS
    /**
     * Servicio a implementar para retornar valores en la request
     */
    private final UserServiceImpl userService;
    /**
     * Servicio a implementar para retornar valores en la request
     */
    private final AuthenticationService serviceAuth;
    /**
     * Repositorio a implementar para validar existencia y throws exceptions
     */
    private final TareaRepository tareaRepository;
    /**
     * Repositorio a implementar para validar existencia y throws exceptions
     */
    private final UserRepository userRepository;

    /**
     * Constructor con 2 parametros
     *
     * @param userService     servicio del usuario
     * @param serviceAuth     servicio del autenticador
     * @param tareaRepository
     * @param userRepository
     */
    public UserController(UserServiceImpl userService, AuthenticationService serviceAuth, TareaRepository tareaRepository, UserRepository userRepository) {
        this.userService = userService;
        this.serviceAuth = serviceAuth;
        this.tareaRepository = tareaRepository;
        this.userRepository = userRepository;
    }

    /*************************************************************
     *                   CREATING REGISTER INTO DATABASE    -       CREATE
     * ***********************************************************/

    // POR ORDEN DE JERARQUÍA EN LA RELACIÓN.

    /**
     * Metodo que valida el registro de un usuario, no es accesible fuera del admin
     *
     * @param principal en referencia al Usuario en sesión actual y que generara los filtro mediante roles
     * @param request en referencia al valor del objeto a almacenar
     * @return <ul>
     *  <li>Token: Retorna un Token como respuesta de un registro exitoso para la utenticación del usuario/li>
     *  </ul>
     */


    @PostMapping(value = "register")
    public ResponseEntity<Object> register(Principal principal, @RequestBody RegisterRequest request) {
        var user = userRepository.findUserByEmail(principal.getName()).orElseThrow();

        return user.getRol() != Rol.ADMIN ? ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permisos para realizar ésta acción") : ResponseEntity.status(HttpStatus.CREATED).body(serviceAuth.register(request));
    }

    /**
     * Metodo que recibe 3 parametros y realiza el alta correspondiente Tarea en referencia al Tecnico  asignado.
     *
     * @param principal en referencia al Usuario en sesión actual y que generara los filtro mediante roles
     * @param idtecnico en referencia al Id especifico del Tecnico  asignado.
     * @param object en referencia al valor del objeto a almacenar
     * @return <ul>
     * <li>Entity: Retorna un UbicacionDTO mostrando el objeto almacenado, HTTPStatus 'CREATED' (201)</li>
     * </ul>
     */
    @PostMapping(path = "/tarea/tecnico/{idtecnico}")
    public ResponseEntity<Object> newObject(Principal principal, @PathVariable Long idtecnico, @RequestBody Object object) {
        var userOnSession = userRepository.findUserByEmail(principal.getName()).orElseThrow();

        return userOnSession.getRol() == Rol.ADMIN && userService.isRegistered(USER, idtecnico) != false ? ResponseEntity.status(HttpStatus.CREATED).body(userService.addNewTar(principal.getName(), idtecnico, object)) : ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ha habido un error en el alta, verifique sus permisos o la inexistencia de un ID correspondiente a la  clase " + TECNICO.toUpperCase() + "  Por favor, verifique");
    }

    /**
     * Metodo que recibe 3 parametros y realiza el alta correspondiente Ubicacion en referencia a la Tarea  que pertenece.
     *
     * @param principal en referencia al Usuario en sesión actual y que generara los filtro mediante roles
     * @param ubicacion en referencia al valor del objeto a almacenar
     * @param idTarea en referencia al Id especifico de la entidad a la que pertenece.
     * @return <ul>
     * <li>Entity: Retorna un UbicacionDTO mostrando el objeto almacenado, HTTPStatus 'CREATED' (201)</li>
     * </ul>
     */
    @PostMapping(path = "/ubicacion/tarea/{idTarea}")
    public ResponseEntity<Object> newObject(Principal principal, @RequestBody Ubicacion ubicacion, @PathVariable Long idTarea) {
        var userOnSession = userRepository.findUserByEmail(principal.getName()).orElseThrow();

        return userOnSession.getRol() == Rol.ADMIN && userService.isRegistered(TAREA, idTarea) != false ? ResponseEntity.status(HttpStatus.CREATED).body(userService.addNewUbicacion(ubicacion, idTarea)) : ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ha habido un error en el alta, verifique sus permisos o la inexistencia de un ID correspondiente a la  clase " + TAREA.toUpperCase() + "  Por favor, verifique");
    }

    /**
     * Metodo que recibe 3 parametros y realiza el alta correspondiente a Coordenada en referencia a la Ubicacion que pertenece.
     *
     * @param principal en referencia al Usuario en sesión actual y que generara los filtro mediante roles
     * @param coordenada en referencia al valor del objeto a almacenar
     * @param idUbicacion en referencia al Id especifico de la entidad a la que pertenece.
     * @return <ul>
     * <li>Entity: Retorna una Coordenada mostrando el objeto almacenado, HTTPStatus 'CREATED' (201)</li>
     * </ul>
     */
    @PostMapping(path = "/coordenada/ubicacion/{idUbicacion}")
    public ResponseEntity<Object> newCoordenada(Principal principal, @RequestBody Coordenada coordenada, @PathVariable Long idUbicacion){
        var userOnSession = userRepository.findUserByEmail(principal.getName()).orElseThrow();
        return userOnSession.getRol() == Rol.ADMIN && userService.isRegistered(UBICACION, idUbicacion) != false ? ResponseEntity.status(HttpStatus.CREATED).body(userService.addNewCoor(coordenada, idUbicacion)) : ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ha habido un error en el alta, verifique sus permisos o la inexistencia de un ID correspondiente a la  clase " + UBICACION.toUpperCase() + "  Por favor, verifique");
    }


    /**
     * Metodo que recibe 2 parametros y realiza el posteo del mensaje correspondiente a la Tarea y Usuario que la realizan.
     *
     * @param principal en referencia al Usuario en sesión actual y que generara los filtro mediante roles
     * @param mensaje en referencia al valor del objeto a almacenar
     * @return <ul>
     * <li>Entity: Retorna un MensajeDTO mostrando el objeto almacenado, HTTPStatus 'CREATED' (201)</li>
     * </ul>
     */
    @PostMapping("/post-mensaje")
    public ResponseEntity<MensajeDTO> postMessage(Principal principal, @RequestBody MensajeDTO mensaje){
        var userOnSession = principal.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.postingMessage(userOnSession, mensaje));
    }
    /*************************************************************
     *                   GETTING RESPONSE FROM DATABASE
     * ***********************************************************/
    /**
     * Metodo que valida el usuario en Session, no es accesible fuera del admin y solo devuelve el perfil de la persona en session
     *
     * @param principal en referencia al Usuario en sesión actual y que generara los filtro mediante roles
     * @return <ul>
     *  <li>Entity: Retorna un perfil según la persona que haga la petición y esté loggueada</li>
     *  </ul>
     */
    @GetMapping(path = "perfil")//TODO : check el endpoint desde el server, verificar si el admin puede acceder al perfil de cualquier usuario
    public ResponseEntity<Object> showMyProfile(Principal principal) {
        return  ResponseEntity.ok(userService.getProfile(principal.getName()));
    }


    /*************************************************************
     *                   GETTING ENTITY BY ID FROM DATABASE
     * ***********************************************************/


    /** Metodo findById
     *
     *  Recibe 2 parametros y valida segun el rol y los parametros la respuesta a emitir, en caso contrario, retorna una respuesta por falta de permisos o ID inexistente, segun aplique
     *
     * @param principal en referencia al Usuario en sesión actual y que generara los filtro mediante roles
     * @param value en referencia a una Entidad definida con la que deseemos especificar el Listado, las cuales pueden ser :
     *              @param User en referencia a las Entidadades totales de Admin y Tecnico.
     *              @param Tarea en referencia a la Entidad Tarea.
     *              @param Ubicacion en referencia a la Entidad Ubicacion.
     *              @param Coordenada en referencia a la Entidad Coordenada.
     *              @param Mensaje en referencia a la Entidad Mensaje.
     * @param id en referencia al Id especifico de la entidad que se desea obtener.
     * @return <ul>
     *  <li>ResponseEntity: Retorna una entidad segun el parametro indicado, o en caso contrario una respuesta indicando el fallo</li>
     *  </ul>
     */
    @GetMapping(path = "find/value/{value}/id/{id}")// TODO verificar la query para que busque por todo, realizar el método para validar la existencia del id, indiferentemente a la clase y retornar la excepcion, verificar los dto de respuestas (users)  retorna aún el objeto
    public ResponseEntity<Object> findById(Principal principal, @PathVariable("value") String value,  @PathVariable("id") Long id) {
        var userOnSession = userRepository.findUserByEmail(principal.getName()).orElseThrow();
        return  userService.isRegistered(value, id) != false && userOnSession.getRol() == Rol.ADMIN ? ResponseEntity.ok(userService.searchById(value, id))  : ResponseEntity.status(HttpStatus.FORBIDDEN).body("Por favor, verifique, es probable que no tengas permisos para esta opcion o el ID proporcionado no este contenido en la Database");
    }
    /*************************************************************
     *                   GETTING LIST RESPONSE FROM DATABASE
     * ***********************************************************/

    /**
     * Metodo que recibe un parametro y valida segun el rol y el parametro
     *
     * @param principal en referencia al Usuario en sesión actual y que generara los filtro mediante roles
     * @param value en referencia a una Entidad definida con la que deseemos especificar el Listado, las cuales pueden ser :
     *              @param User en referencia a las Entidadades totales de Admin y Tecnico.
     *              @param Admin en referencia a la Entidad Admin.
     *              @param Tecnico en referencia a la Entidad Tecnico.
     *              @param Tarea en referencia a la Entidad Tarea.
     *              @param Ubicacion en referencia a la Entidad Ubicacion.
     *              @param Coordenada en referencia a la Entidad Coordenada.
     *              @param Mensaje en referencia a la Entidad Mensaje.
     * @return <ul>
     *  <li>Lista de Valores: Retorna una lista segun el parametro indicado por entidad</li>
     *  </ul>
     */
    @GetMapping(path = "results/{value}")// todo, controlar mejor la respuesta para devolver errores y resultados vacios
    public ResponseEntity<List <Object>> getRegisters(Principal principal, @PathVariable("value") String value){
        var userOnSession = userRepository.findUserByEmail(principal.getName()).orElseThrow();

        //return userOnSession.getRol() == Rol.ADMIN ? ResponseEntity.ok(userService.registers(principal.getName(), value)) : ResponseEntity.ok(Collections.singletonList("Por favor, verifique, es probable que no tengas permisos para esta opcion."));
        return ResponseEntity.ok(userService.registers(principal.getName(), value));
    }


    /**
     * Metodo FilterBy recibe un parametro y valida segun el rol y el parametro
     *
     * @param principal en referencia al Usuario en sesión actual y que generara los filtro mediante roles
     * @param value en referencia al  filtrao especifico a aplicar
     * @param object en referencia al valor especifica del filtro
     * @return <ul>
     *  <li>Lista de Valores: Retorna una lista de tareas según el value : Username Tecnico, las que tenga asignadas, Admin, Listado de Tareas por username del Tecnico</li>
     *  <li>Lista de Valores: Retorna una lista de tareas según el value : Nombre Tecnico, las que tenga asignadas, Admin, Listado de Tareas por Nombre del Tecnico</li>
     *  </ul>
     */
    @GetMapping(path = "tareas/filterBy/{value}")
    public ResponseEntity<List <Object>> filterBy(Principal principal, @PathVariable("value") String value, @RequestParam String object){
        var userOnSession = userRepository.findUserByEmail(principal.getName()).orElseThrow();

        return userOnSession.getRol() == Rol.ADMIN ? ResponseEntity.ok(userService.filterByValue(value, object)) : ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.singletonList("Por favor, verifique, es probable que no tengas permisos para esta opcion."));
    }


    /**
     * Metodo FilterByDateMessages recibe 2 parametros y valida segun el rol y el parametro
     *
     * @param principal en referencia al Usuario en sesión actual y que generara los filtro mediante roles
     * @param value en referencia al  filtrao especifico a aplicar
     * @param object en referencia al valor especifica del filtro
     * @return <ul>
     *  <li>Lista de Valores: Retorna una lista de tareas según el value : Username Tecnico, las que tenga asignadas, Admin, Listado de Tareas por username del Tecnico</li>
     *  <li>Lista de Valores: Retorna una lista de tareas según el value : Nombre Tecnico, las que tenga asignadas, Admin, Listado de Tareas por Nombre del Tecnico</li>
     *  </ul>
     */
    @GetMapping(path = "mensajes/porRango")
    public ResponseEntity<List <Object>> filterByDate(Principal principal, @RequestParam String date1, @RequestParam String date2){
        var userOnSession = userRepository.findUserByEmail(principal.getName()).orElseThrow();

        return userOnSession.getRol() == Rol.ADMIN ? ResponseEntity.ok(userService.filterByDates(date1, date2)) : ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.singletonList("Por favor, verifique, es probable que no tengas permisos para esta opcion."));
    }

    /*************************************************************
     *                   UPDATE VALUES FROM A OBJET IN DATABASE
     * ***********************************************************/

    /**
     * Metodo que recibe 3 parametros y realiza el update correspondiente, validando el rol que realiza la request, el id y el objeto a actualizar
     *
     * @param principal en referencia al Usuario en sesión actual y que generara los filtro mediante roles
     * @param userDTO en referencia al Objeto nuevo que le indicamos en la request con todos los valores a actualizar contenidos
     * @return <ul>
     * <li>Una entidad: El objeto con todos sus campos actualizados</li>
     * </ul>
     */
    @PutMapping(path = "update-user")// TODO revisar la respuesta y controlar mejor, ubicar qué campos realmente ejecutará el rol de user normal en el suyo
    public Object update(Principal principal, @RequestBody Object userDTO){
        var userOnSession = userRepository.findUserByEmail(principal.getName()).orElseThrow();

        return userOnSession.isEnabled() ? userService.update(principal.getName(), userDTO) : ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se puede editar su perfil, contacte al administrador");
    }

    /**
     * Metodo que recibe 4 parametros y realiza el update correspondiente, validando el rol que realiza la request, el valor de la entidad a la que modificar, el id y el objeto a actualizar
     *
     * El metodo centraliza en un unico Request  todos los Services de Update aplicados por cada entidad y que administra userService
     *
     * @param principal en referencia al Usuario en sesión actual y que generara los filtro mediante roles
     * @param value en referencia a una Entidad definida con la que deseemos especificar el Update, las cuales pueden ser :
     *              @param Admin en referencia a la Entidad Admin.
     *              @param Tecnico en referencia a la Entidad Tecnico.
     *              @param Tarea en referencia a la Entidad Tarea.
     *              @param Ubicacion en referencia a la Entidad Ubicacion.
     *              @param Coordenada en referencia a la Entidad Coordenada.
     *              @param Mensaje en referencia a la Entidad Mensaje.
     * @param id en referencia al Id especifico de la entidad que se desea actualizar
     * @param object en referencia al Objeto nuevo que le indicamos en la request con todos los valores a actualizar contenidos
     *
     * @return <ul>
     * <li>Response: El objeto con todos sus campos actualizados, en caso de ser satisfactorio o  un mensaje indicando error</li>
     * </ul>
     */
    @PutMapping(path = "update/value/{value}/id/{id}")
    public ResponseEntity<Object> update(Principal principal, @PathVariable String value, @PathVariable Long id, @RequestBody Object object) throws JsonProcessingException {

        // TODO, CHEQUEAR PUES BLOQUEA TODO EDIT EN TAREA SI ESTA YA ESTA ASIGNADA
        /*if(value.equals(TAREA)){
           return userService.isRegistered(value, id) != false && userService.checkLocation(id) != true ? ResponseEntity.ok(userService.updateValue(principal.getName(), value, id, object)) : ResponseEntity.status(HttpStatus.CONFLICT).body("Esta Tarea ya contine una Ubicación, si desea editar la misma, primero elimine la ubicación existente");
        }
        if (value.equals(UBICACION)){
            var tareaId = mapper.convertValue(object, UbicacionDTO.class);
            return userService.isRegistered(value, id) != false && userService.checkLocation(tareaId.getTarea()) != true ? ResponseEntity.ok(userService.updateValue(principal.getName(), value, id, object)) : ResponseEntity.status(HttpStatus.CONFLICT).body("Esta Tarea ya contine una Ubicación, si desea editar la misma, primero elimine la ubicación existente");
        }*/

        return userService.isRegistered(value, id) != false ? ResponseEntity.ok(userService.updateValue(principal.getName(), value, id, object)) : ResponseEntity.status(HttpStatus.FORBIDDEN).body("No hay registro del ID proporcionado de la clase " + value.toUpperCase() + " Por favor, verifique");
    }

    /*************************************************************
     *                   DELETE VALUES FROM A OBJET IN DATABASE
     * ***********************************************************/

    /**
     * Metodo que recibe 3 parametros y realiza el delete correspondiente, validando el rol que realiza la request, la clase a la que refiere la elimincación y la existencia del ID correspondiente a esa clase en la base de datos.
     *
     * El metodo centraliza en un unico Request  todos los Services de Update aplicados por cada entidad y que administra userService
     *
     * @param principal en referencia al Usuario en sesión actual y que generara los filtro mediante roles
     * @param typus en referencia a una Entidad definida con la que deseemos especificar el Delete, las cuales pueden ser :
     *              @param User en referencia a la Entidad Admin y Tecnico.
     *              @param Tarea en referencia a la Entidad Tarea.
     *              @param Ubicacion en referencia a la Entidad Ubicacion.
     *              @param Coordenada en referencia a la Entidad Coordenada.
     *              @param Mensaje en referencia a la Entidad Mensaje.
     * @param id en referencia al Id especifico de la entidad que se desea eliminar
     * @return <ul>
     * <li>Response: El HTTPStatus con el codigo 'NO_CONTENT' (204), en caso de ser satisfactorio o  un mensaje indicando error</li>
     * </ul>
     * </ul>
     */
    @DeleteMapping("/delete/typus/{typus}/id/{id}")
    public ResponseEntity<Object> deleteById(Principal principal, @PathVariable String typus, @PathVariable Long id){
        var userOnSession = principal.getName();

        return userService.isRegistered(typus, id) != false ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.deleteRegister(userOnSession, typus, id)) : ResponseEntity.status(HttpStatus.FORBIDDEN).body("No hay registro del ID proporcionado de la clase " + typus.toUpperCase() + " Por favor, verifique");
    }

}
