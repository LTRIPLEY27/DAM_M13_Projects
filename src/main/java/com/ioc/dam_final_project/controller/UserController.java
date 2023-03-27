package com.ioc.dam_final_project.controller;

import com.ioc.dam_final_project.dto.TecnicoDTO;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.dto.MensajeDTO;
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

    /**
     * Servicio a implementar para retornar valores en la request
     */
    private final UserServiceImpl userService;
    /**
     * Servicio a implementar para retornar valores en la request
     */
    private final AuthenticationService serviceAuth;

    /**
     * Constructor con 2 parametros
     * @param userService servicio del usuario
     * @param serviceAuth servicio del autenticador
     */
    public UserController(UserServiceImpl userService, AuthenticationService serviceAuth) {
        this.userService = userService;
        this.serviceAuth = serviceAuth;
    }

    /*************************************************************
     *                   GETTING REGISTER INTO DATABASE
     * ***********************************************************/
    @PostMapping(value = "register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(serviceAuth.register(request));
    }



    /*************************************************************
     *                   GETTING RESPONSE FROM DATABASE
     * ***********************************************************/
    @GetMapping(path = "perfil")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> showMyProfile(Principal principal) throws IllegalArgumentException{
        if(principal.getName().isEmpty()){
            throw new IllegalArgumentException("Necesitas estar logueado para tener un perfil");
        }
        return ResponseEntity.ok(userService.getProfile(principal.getName()));
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
    @GetMapping(path = "results/{value}")
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
     * @return <ul>
     *  <li>Una entidad: El objeto con todos sus campos actualizados</li>
     *  </ul>
     */
    //@PutMapping(path = "update/id/{id}/value/{value}")
    @PutMapping(path = "update-user")
    @ResponseStatus(HttpStatus.OK)
    //public Object update(Principal principal, @PathVariable (name = "id") Long id,/* @PathVariable("value") String value,*/ @RequestBody Object object) throws Exception {
    public Object update(Principal principal, @RequestBody TecnicoDTO tecnico) throws Exception {
        var userOnSession = principal.getName();
        return ResponseEntity.ok(userService.update(userOnSession, tecnico));
    }

    /*************************************************************
     *                   DELETE VALUES FROM A OBJET IN DATABASE
     * ***********************************************************/
    @DeleteMapping("/delete/typus/{typus}/id/{id}")
    public ResponseEntity<String> deleteById(Principal principal, @PathVariable String typus, @PathVariable Long id){
        var userOnSession = principal.getName();
        userService.deleteRegister(userOnSession, typus, id);
        return ResponseEntity.ok("Elemento borrado");
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
