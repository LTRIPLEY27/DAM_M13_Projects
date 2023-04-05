package com.ioc.dam_final_project.controller;

import com.ioc.dam_final_project.dto.*;
import com.ioc.dam_final_project.model.Coordenada;
import com.ioc.dam_final_project.model.Tarea;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.model.Ubicacion;
import com.ioc.dam_final_project.security.authentication.AuthenticationResponse;
import com.ioc.dam_final_project.security.authentication.AuthenticationService;
import com.ioc.dam_final_project.security.authentication.RegisterRequest;
import com.ioc.dam_final_project.serviceImpl.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {


    @Qualifier("admin")
    private final AdminServiceImpl serviceAdmin;
    private final TareaServiceImpl tareaService;
    private final AuthenticationService serviceAuth;
    private final UbicacionServiceImpl ubicacionService;
    private final CoordenadaServiceImpl coordenadaService;
    private final MensajeServiceImpl mensajeService;
    private final UserServiceImpl userService;


    public AdminController(AdminServiceImpl serviceAdmin, TareaServiceImpl tareaService, AuthenticationService serviceAuth, UbicacionServiceImpl ubicacionService, CoordenadaServiceImpl coordenadaService, MensajeServiceImpl mensajeService, UserServiceImpl userService) {
        this.serviceAdmin = serviceAdmin;
        this.ubicacionService = ubicacionService;
        this.tareaService = tareaService;
        this.serviceAuth = serviceAuth;
        this.coordenadaService = coordenadaService;
        this.mensajeService = mensajeService;
        this.userService = userService;
    }

    /*************************************************************
    *                   GETTING BY ID
    * ***********************************************************/


    @GetMapping(path = "/ubicacion/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UbicacionDTO getValue(@PathVariable Long id){

        return ubicacionService.searchById(id);
    }

    /*************************************************************
     *                   GTTING VALIDATION WITH JWT
     * ***********************************************************/


   /* @PostMapping(value = "register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(serviceAuth.register(request));
    }*/


    @PostMapping(path = "/add-new-tecnic")
    @ResponseStatus(HttpStatus.CREATED)
    public Tecnico newObject(@RequestBody Tecnico tecnico) {
        return serviceAdmin.create(tecnico);
    }



    /*************************************************************
     *                   GETTING RESPONSE FROM DATABASE
     * ***********************************************************/


    /*************************************************************
     *                  UPDATE VALUES FROM DATABASE
     * ***********************************************************/
   /* @PutMapping(path = "/update/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tecnico update(@PathVariable Long id, @RequestBody Tecnico tecnico){
        return serviceAdmin.update(id, tecnico);
    }*/

    /*************************************************************
     *                  CREATING TASK IN THE DATABASE
     * ***********************************************************/


    @PostMapping(path = "/tarea/tecnico/{tecnico}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> newObject(Principal principal, @PathVariable int tecnico, @RequestBody Tarea tarea) {
        var userOnSession = principal.getName();
        return ResponseEntity.ok(tareaService.saveObject(userOnSession, tecnico, tarea));
    }

    @PostMapping(path = "/ubicacion/tarea/{tarea}")
    @ResponseStatus(HttpStatus.CREATED)
    public Ubicacion newObject(@RequestBody Ubicacion ubicacion, @PathVariable Long tarea) {
        return ubicacionService.addObject(ubicacion, tarea);
    }

    @PostMapping(path = "/coordenada/ubicacion/{ubicacion}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> newCoordenada(@RequestBody Coordenada coordenada, @PathVariable Long ubicacion){
        coordenadaService.addCoordenada(coordenada, ubicacion);
        return ResponseEntity.ok("Coordenada adherida a la ubicacion " + ubicacion);
    }

    /*************************************************************
     *                  DELETE VALUES FROM DATABASE
     * ***********************************************************/

    @DeleteMapping("/tarea/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEntity(@PathVariable (name = "id") Long id){
        tareaService.deleteEntity(id);
    }

    @DeleteMapping("/mensaje/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEntityMesa(@PathVariable (name = "id") Long id){
        mensajeService.deleteEntity(id);
    }

    @DeleteMapping("/coordenada/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEntityCoor(@PathVariable (name = "id") Long id){
        coordenadaService.deleteEntity(id);
    }

    @DeleteMapping("/ubicacion/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEntityUbi(@PathVariable (name = "id") Long id){
        ubicacionService.deleteEntity(id);
    }

}
