package com.ioc.dam_final_project.security.authentication;

import com.ioc.dam_final_project.security.config.LogOutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/********************************************************************************************************
 *                                      *****   CONTROLLER PARA AUTHENTICARSE Y SALIR   *****
 *********************************************************************************************************
 * SERÁ UN CONTROLLER SIN RESTRICCIÓN DE AUTENTICADO.
 *
 *   Notaciones :
 * ****************
 *   - He declarado a la clase como con las notaciones '@RestController' y '@RequestMapping', para indicar la ruta con la cual el servidor accederá.
 *   - He usado las notaciones propias de SpringBoot, en combinación a Java 17 y Loombook, para potenciar al máximo la codificación.
 *
 *   Atributos :
 * * *************
 * - He declarado los atributos : Private final, ya que serán 2 Services alternos que necesitaré instanciar y con ello me aseguro.
 * -
 *
 *   Métodos :
 * * *************
 * - He declarado los métodos de acceso como públicos, ya que la idea inicial de éste controlador es el acceso global y filtrar a los usuarios a partir de la respuesta.
 * */
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
