package com.ioc.dam_final_project.controller;

import com.ioc.dam_final_project.model.Tarea;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.repository.UserRepository;
import com.ioc.dam_final_project.security.authentication.AuthenticationRequest;
import com.ioc.dam_final_project.security.authentication.AuthenticationResponse;
import com.ioc.dam_final_project.security.authentication.AuthenticationService;
import com.ioc.dam_final_project.security.authentication.RegisterRequest;
import com.ioc.dam_final_project.serviceImpl.AdminServiceImpl;
import com.ioc.dam_final_project.serviceImpl.TareaServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    // INYECCION DE DEPENDENCIAS
    @Qualifier("admin")
    private final AdminServiceImpl serviceAdmin;
    private final UserRepository userRepository;
    private final TareaServiceImpl tareaService;
    private final AuthenticationService serviceAuth;


    public AdminController(AdminServiceImpl serviceAdmin, UserRepository userRepository, TareaServiceImpl tareaService, AuthenticationService serviceAuth) {
        this.serviceAdmin = serviceAdmin;
        this.userRepository = userRepository;
        this.tareaService = tareaService;
        this.serviceAuth = serviceAuth;

    }

    /*************************************************************
    *                   POSTING A NEW INSTANCE TO DATABASE
    * ***********************************************************/


    /*************************************************************
     *                   GTTING VALIDATION WITH JWT
     * ***********************************************************/

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(serviceAuth.register(request));
    }

    @PostMapping(value ="/authenticate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(serviceAuth.authenticate(request));
    }

    /*@PostMapping(path = "/add-new-tecnic")
    @ResponseStatus(HttpStatus.CREATED)
    public Tecnico newObject(@RequestBody Tecnico tecnico) {
        return serviceAdmin.create(tecnico);
    }
*/
    /*************************************************************
     *                   GETTING RESPONSE FROM DATABASE
     * ***********************************************************/

    @GetMapping(path = "/tecnicos")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Tecnico> getAll(){
        return  serviceAdmin.getAll();
    }


    @GetMapping(path = "/tareas")
    @ResponseStatus(HttpStatus.OK)
    public List<Tarea> totalTarea(){
        return tareaService.total();
    }

    /*************************************************************
     *                  UPDATE VALUES FROM DATABASE
     * ***********************************************************/
    /*@PutMapping(path = "/update/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tecnico update(@PathVariable Long id, @RequestBody Tecnico tecnico){
        return serviceAdmin.update(id, tecnico);
    }*/

    /*************************************************************
     *                  CREATING TASK IN THE DATABASE
     * ***********************************************************/
    @PostMapping(path = "/task")
    @ResponseStatus(HttpStatus.CREATED)
    public Tarea newObject(@RequestBody Tarea tarea) {
        return tareaService.saveObject(tarea);
    }
}
