package com.ioc.dam_final_project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ioc.dam_final_project.dto.MensajeDTO;
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

    // TODO, centralizar todos los new registers
    // POR ORDEN DE JERARQUÍA EN LA RELACIÓN.

    /**
     * Metodo que valida el registro de un usuario, no es accesible fuera del admin
     * @return <ul>
     *  <li>Token: Retorna un Token como respuesta de un registro exitoso para la utenticación del usuario/li>
     *  </ul>
     */
    @PostMapping(value = "register")
    public ResponseEntity<Object> register(Principal principal, @RequestBody RegisterRequest request) {
        var user = userRepository.findUserByEmail(principal.getName()).orElseThrow();

        return user.getRol() != Rol.ADMIN ? ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permisos para realizar ésta acción") : ResponseEntity.status(HttpStatus.CREATED).body(serviceAuth.register(request));
    }

    // TODO AGREGAR VALIDATOR PARA OBJETO AGREGADO POR ID, verificador de rol, y dtos de tareas, RESPUESTA DE AGREGADO CORRECTAMENTE
    @PostMapping(path = "/tarea/tecnico/{idtecnico}")
    public ResponseEntity<Object> newObject(Principal principal, @PathVariable Long idtecnico, @RequestBody Tarea object) {
        var userOnSession = userRepository.findUserByEmail(principal.getName()).orElseThrow();

        return userOnSession.getRol() == Rol.ADMIN && userService.isRegistered(USER, idtecnico) != false ? ResponseEntity.status(HttpStatus.CREATED).body(userService.addNewTar(principal.getName(), idtecnico, object)) : ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ha habido un error en el alta, verifique sus permisos o la inexistencia de un ID correspondiente a la  clase " + TECNICO.toUpperCase() + "  Por favor, verifique");
    }

    @PostMapping(path = "/ubicacion/tarea/{idtarea}")
    public ResponseEntity<Object> newObject(Principal principal, @RequestBody Ubicacion ubicacion, @PathVariable Long idtarea) {
        var userOnSession = userRepository.findUserByEmail(principal.getName()).orElseThrow();

        return userOnSession.getRol() == Rol.ADMIN && userService.isRegistered(TAREA, idtarea) != false ? ResponseEntity.status(HttpStatus.CREATED).body(userService.addNewUbicacion(ubicacion, idtarea)) : ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ha habido un error en el alta, verifique sus permisos o la inexistencia de un ID correspondiente a la  clase " + TAREA.toUpperCase() + "  Por favor, verifique");
    }

    @PostMapping(path = "/coordenada/ubicacion/{idUbicacion}")
    public ResponseEntity<Object> newCoordenada(Principal principal, @RequestBody Coordenada coordenada, @PathVariable Long idUbicacion){
        var userOnSession = userRepository.findUserByEmail(principal.getName()).orElseThrow();
        return userOnSession.getRol() == Rol.ADMIN && userService.isRegistered(UBICACION, idUbicacion) != false ? ResponseEntity.status(HttpStatus.CREATED).body(userService.addNewCoor(coordenada, idUbicacion)) : ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ha habido un error en el alta, verifique sus permisos o la inexistencia de un ID correspondiente a la  clase " + UBICACION.toUpperCase() + "  Por favor, verifique");
    }


    /**
     * Metodo que recibe 2 parametros y realiza el posteo del mensaje correspondiente a la Tarea y Usuario que la realizan.
     *
     * @return <ul>
     * <li>String: Con la respuesta de la consulta y según el caso exitoso o no de la eliminacion</li>
     * </ul>
     */
    @PostMapping("/post-mensaje")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MensajeDTO> postMessage(Principal principal, @RequestBody MensajeDTO mensaje){
        var userOnSession = principal.getName();
        return ResponseEntity.ok(userService.postingMessage(userOnSession, mensaje));
    }
    /*************************************************************
     *                   GETTING RESPONSE FROM DATABASE
     * ***********************************************************/
    /**
     * Metodo que valida el usuario en Session, no es accesible fuera del admin y solo devuelve el perfil de la persona en session
     * @return <ul>
     *  <li>Entity: Retorna un perfil según la persona que haga la petición y esté loggueada/li>
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
     *  Recibe 2 parametros y valida segun el rol y los parametros la respuesta a emitir, en caso contrario, retorna una respuesta por falta de permisos o ID inexistente, segun aplique
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
     * @return <ul>
     *  <li>Lista de Valores: Retorna una lista segun el parametro indicado</li>
     *  </ul>
     */
    @GetMapping(path = "results/{value}")// todo, controlar mejor la respuesta para devolver errores y resultados vacios
    public ResponseEntity<List <Object>> getRegisters(Principal principal, @PathVariable("value") String value){
        var userOnSession = userRepository.findUserByEmail(principal.getName()).orElseThrow();

        //return userOnSession.getRol() == Rol.ADMIN ? ResponseEntity.ok(userService.registers(principal.getName(), value)) : ResponseEntity.ok(Collections.singletonList("Por favor, verifique, es probable que no tengas permisos para esta opcion."));
        return ResponseEntity.ok(userService.registers(principal.getName(), value));
    }


    /**
     * Metodo que recibe un parametro y valida segun el rol y el parametro
     * @return <ul>
     *  <li>Lista de Valores: Retorna una lista de tares según el usuario : Tecnico, las que tenga asignadas, Admin, por username del Tecnico</li>
     *  </ul>
     */
    @GetMapping(path = "tareas/tecnico/{tecnico}")
    public ResponseEntity<List <Object>> getRegistersByTecnic(Principal principal, @PathVariable("tecnico") String value){
        var userOnSession = userRepository.findUserByEmail(principal.getName()).orElseThrow();

        return userOnSession.getRol() == Rol.ADMIN ? ResponseEntity.ok(userService.findTaskByTecnic(value)) : ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.singletonList("Por favor, verifique, es probable que no tengas permisos para esta opcion."));
    }

    /*************************************************************
     *                   UPDATE VALUES FROM A OBJET IN DATABASE
     * ***********************************************************/

    /**
     * Metodo que recibe 3 parametros y realiza el update correspondiente, validando el rol que realiza la request, el id y el objeto a actualizar
     *
     * @return <ul>
     * <li>Una entidad: El objeto con todos sus campos actualizados</li>
     * </ul>
     */
    @PutMapping(path = "update-user")// TODO revisar la respuesta y controlar mejor, ubicar qué campos realmente ejecutará el rol de user normal en el suyo
    public Object update(Principal principal, @RequestBody UserDTO userDTO){
        var userOnSession = userRepository.findUserByEmail(principal.getName()).orElseThrow();

        return userOnSession.isEnabled() ? userService.update(principal.getName(), userDTO) : ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se puede editar su perfil, contacte al administrador");
    }

    /**
     * Metodo que recibe 4 parametros y realiza el update correspondiente, validando el rol que realiza la request, el valor de la entidad a la que modificar, el id y el objeto a actualizar
     *
     * @return <ul>
     * <li>Una entidad: El objeto con todos sus campos actualizados</li>
     * </ul>
     */
    @PutMapping(path = "update/value/{value}/id/{id}")
    public ResponseEntity<Object> update(Principal principal, @PathVariable String value, @PathVariable Long id, @RequestBody Object object) throws JsonProcessingException {

        return userService.isRegistered(value, id) != false ? ResponseEntity.ok(userService.updateValue(principal.getName(), value, id, object)) : ResponseEntity.status(HttpStatus.FORBIDDEN).body("No hay registro del ID proporcionado de la clase " + value.toUpperCase() + " Por favor, verifique");
    }

    /*************************************************************
     *                   DELETE VALUES FROM A OBJET IN DATABASE
     * ***********************************************************/

    /**
     * Metodo que recibe 3 parametros y realiza el delete correspondiente, validando el rol que realiza la request, la clase a la que refiere la elimincación y la existencia del ID correspondiente a esa clase en la base de datos.
     *
     * @return <ul>
     * <li>String: Con la respuesta de la consulta y según el caso exitoso o no de la eliminacion</li>
     * </ul>
     */
    @DeleteMapping("/delete/typus/{typus}/id/{id}")
    public ResponseEntity<Object> deleteById(Principal principal, @PathVariable String typus, @PathVariable Long id){
        var userOnSession = principal.getName();

        return userService.isRegistered(typus, id) != false ? ResponseEntity.ok(userService.deleteRegister(userOnSession, typus, id)) : ResponseEntity.status(HttpStatus.FORBIDDEN).body("No hay registro del ID proporcionado de la clase " + typus.toUpperCase() + " Por favor, verifique");
    }

}
