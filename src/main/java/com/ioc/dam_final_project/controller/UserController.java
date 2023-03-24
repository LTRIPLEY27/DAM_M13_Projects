package com.ioc.dam_final_project.controller;

import com.ioc.dam_final_project.dto.TareaDTO;
import com.ioc.dam_final_project.security.authentication.AuthenticationResponse;
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

    private final UserServiceImpl userService;
    private final AuthenticationService serviceAuth;

    public UserController(UserServiceImpl userService, AuthenticationService serviceAuth) {
        this.userService = userService;
        this.serviceAuth = serviceAuth;
    }

    /*************************************************************
     *                   GETTING REGISTER INTO DATABASE
     * ***********************************************************/
    @PostMapping(value = "register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(serviceAuth.register(request));
    }

    /*************************************************************
     *                   GETTING RESPONSE FROM DATABASE
     * ***********************************************************/
    @GetMapping(path = "perfil")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> showMyProfile(Principal principal){
        return ResponseEntity.ok(userService.getProfile(principal.getName()));
    }

    /*************************************************************
     *                   GETTING LIST RESPONSE FROM DATABASE
     * ***********************************************************/

    @GetMapping(path = "results/{value}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Object>> getRegisters(Principal principal, @PathVariable("value") String value){

        var username = principal.getName();
        return ResponseEntity.ok(userService.registers(username, value));
    }
}
