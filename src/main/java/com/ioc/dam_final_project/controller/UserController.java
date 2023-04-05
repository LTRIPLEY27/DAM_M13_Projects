package com.ioc.dam_final_project.controller;

import com.ioc.dam_final_project.dto.MensajeDTO;
import com.ioc.dam_final_project.dto.UserDTO;
import com.ioc.dam_final_project.model.Enums.Rol;
import com.ioc.dam_final_project.repository.TareaRepository;
import com.ioc.dam_final_project.repository.UserRepository;
import com.ioc.dam_final_project.security.authentication.AuthenticationService;
import com.ioc.dam_final_project.security.authentication.RegisterRequest;
import com.ioc.dam_final_project.serviceImpl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/")
public class UserController {

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
     *                   GETTING REGISTER INTO DATABASE
     * ***********************************************************/
    @PostMapping(value = "register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> register(Principal principal, @RequestBody RegisterRequest request) {
        var user = userRepository.findUserByEmail(principal.getName()).orElseThrow();

        return user.getRol() != Rol.ADMIN ? ResponseEntity.ok("No tiene permisos para realizar ésta acción") : ResponseEntity.ok(serviceAuth.register(request));
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
    @GetMapping(path = "perfil")//TODO : check el endpoint desde el server
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> showMyProfile(Principal principal) {
        return  ResponseEntity.ok(userService.getProfile(principal.getName()));
    }

    /*************************************************************
     *                   GETTING ENTITY BY ID FROM DATABASE
     * ***********************************************************/
    @GetMapping(path = "find/value/{value}/id/{id}")// TODO verificar la query para que busque por todo, realizar el método para validar la existencia del id, indiferentemente a la clase y retornar la excepcion, verificar los dto de respuestas (users)  retorna aún el objeto
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> findById(@PathVariable("value") String value,  @PathVariable("id") Long id) {
        return  ResponseEntity.ok(userService.searchById(value, id));
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Object>> getRegisters(Principal principal, @PathVariable("value") String value){

        var username = principal.getName();
        return ResponseEntity.ok(userService.registers(username, value));
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
    @PutMapping(path = "update-user")// TODO revisar la respuesta y controlar mejor
    @ResponseStatus(HttpStatus.OK)
    public Object update(Principal principal, @RequestBody UserDTO userDTO){
        var userOnSession = userRepository.findUserByEmail(principal.getName());
        return !userOnSession.isPresent() ? userService.update(principal.getName(), userDTO) : ResponseEntity.ok("No se puede editar su perfil, contacte al administrador");
    }

    /**
     * Metodo que recibe 4 parametros y realiza el update correspondiente, validando el rol que realiza la request, el valor de la entidad a la que modificar, el id y el objeto a actualizar
     *
     * @return <ul>
     * <li>Una entidad: El objeto con todos sus campos actualizados</li>
     * </ul>
     */
    @PutMapping(path = "update/value/{value}/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> update(Principal principal, @PathVariable String value, @PathVariable Long id, @RequestBody Object object)  {
        /*if(!tareaRepository.findById(id).isPresent()){
            throw new Exception("ID inexistente en la base de datos");
        }*/
        // TODO REALIZAR UN MÉTODO QUE VALIDE LOS ID Y RETORNE UN BOOLEAN PARA INDICAR ANTES SI EXITE O NO EL ID
        return ResponseEntity.ok(userService.updateValue(principal.getName(), value, id, object));
    }

    /*************************************************************
     *                   DELETE VALUES FROM A OBJET IN DATABASE
     * ***********************************************************/
    @DeleteMapping("/delete/typus/{typus}/id/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Object> deleteById(Principal principal, @PathVariable String typus, @PathVariable Long id){

        var userOnSession = principal.getName();
        return userService.isRegistered(typus, id) != false ? ResponseEntity.ok(userService.deleteRegister(userOnSession, typus, id)) : ResponseEntity.ok("No hay registro del ID proporcionado de la clase " + typus.toUpperCase() + " Por favor, verifique") ;
    }

    /*************************************************************
     *                   POST VALUES FROM A OBJET IN DATABASE
     * ***********************************************************/

    @PostMapping("/post-mensaje")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MensajeDTO> postMessage(Principal principal, @RequestBody MensajeDTO mensaje){
        var userOnSession = principal.getName();
        return ResponseEntity.ok(userService.postingMessage(userOnSession, mensaje));
    }


}
