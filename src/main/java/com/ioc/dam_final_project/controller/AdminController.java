package com.ioc.dam_final_project.controller;

import com.ioc.dam_final_project.model.Coordenada;
import com.ioc.dam_final_project.model.Tarea;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.model.Ubicacion;
import com.ioc.dam_final_project.security.authentication.AuthenticationRequest;
import com.ioc.dam_final_project.security.authentication.AuthenticationResponse;
import com.ioc.dam_final_project.security.authentication.AuthenticationService;
import com.ioc.dam_final_project.security.authentication.RegisterRequest;
import com.ioc.dam_final_project.serviceImpl.AdminServiceImpl;
import com.ioc.dam_final_project.serviceImpl.CoordenadaServiceImpl;
import com.ioc.dam_final_project.serviceImpl.TareaServiceImpl;
import com.ioc.dam_final_project.serviceImpl.UbicacionServiceImpl;
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
    private final TareaServiceImpl tareaService;
    private final AuthenticationService serviceAuth;
    private final UbicacionServiceImpl ubicacionService;
    private final CoordenadaServiceImpl coordenadaService;


    public AdminController(AdminServiceImpl serviceAdmin, TareaServiceImpl tareaService, AuthenticationService serviceAuth, UbicacionServiceImpl ubicacionService, CoordenadaServiceImpl coordenadaService) {
        this.serviceAdmin = serviceAdmin;
        this.ubicacionService = ubicacionService;
        this.tareaService = tareaService;
        this.serviceAuth = serviceAuth;

        this.coordenadaService = coordenadaService;
    }

    /*************************************************************
    *                   GETTING BY ID
    * ***********************************************************/

    @GetMapping(path = "/ubicacion/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Ubicacion getValue(@PathVariable Long id){

        return ubicacionService.findById(id);
    }

    /*************************************************************
     *                   GTTING VALIDATION WITH JWT
     * ***********************************************************/

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(serviceAuth.register(request));
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

    @GetMapping(path = "/ubicaciones")
    @ResponseStatus(HttpStatus.OK)
    public List<Ubicacion> totalUbicaciones(){
        return ubicacionService.getAll();
    }

    @GetMapping(path = "/coordenadas")
    @ResponseStatus(HttpStatus.OK)
    public List<Coordenada> totalCoordenas(){
        return coordenadaService.coordenas();
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
    @PostMapping(path = "/tarea")
    @ResponseStatus(HttpStatus.CREATED)
    public Tarea newObject(@RequestBody Tarea tarea) {
        return tareaService.saveObject(tarea);
    }


    @PostMapping(path = "/ubicacion")
    @ResponseStatus(HttpStatus.CREATED)
    public Ubicacion newObject(@RequestBody Ubicacion ubicacion) {
        return ubicacionService.addObject(ubicacion);
    }

    @PostMapping(path = "/coordenada")
    @ResponseStatus(HttpStatus.CREATED)
    public void newCoordenada(@RequestBody Coordenada coordenada){
        coordenadaService.addCoordenada(coordenada);
    }
}
