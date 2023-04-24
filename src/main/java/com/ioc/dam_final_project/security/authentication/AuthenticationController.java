package com.ioc.dam_final_project.security.authentication;

import com.ioc.dam_final_project.security.config.LogOutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *  CONTROLLER PARA AUTHENTICARSE Y SALIR
 *
 * SERA UN CONTROLLER SIN RESTRICCION DE AUTENTICADO.
 *
 *   Notaciones :
 *
 *   - He declarado a la clase como con las notaciones '@RestController' y '@RequestMapping', para indicar la ruta con la cual el servidor accedera.
 *   - He usado las notaciones propias de SpringBoot, en combinacion a Java 17 y Loombook, para potenciar al maximo la codificacion.
 *
 *   Atributos :
 *
 * - He declarado los atributos: Private final, ya que seran 2 Services alternos que necesitare instanciar y con ello me aseguro.
 * -
 *
 *   Metodos :
 *
 * - He declarado los metodos de acceso como publicos, ya que la idea inicial de este controlador es el acceso global y filtrar a los usuarios a partir de la respuesta.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService serviceAuth;
    private final LogOutService logOutService;

    public AuthenticationController(AuthenticationService serviceAuth, LogOutService logOutService) {
        this.serviceAuth = serviceAuth;
        this.logOutService = logOutService;
    }

    @PostMapping(value ="/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {

        return ResponseEntity.ok(serviceAuth.authenticate(request));
    }

    @PostMapping(value = "/logout")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> logOut(){

        logOutService.logout(null, null, null);
        return ResponseEntity.ok("bye bye cowboy");
    }
}
