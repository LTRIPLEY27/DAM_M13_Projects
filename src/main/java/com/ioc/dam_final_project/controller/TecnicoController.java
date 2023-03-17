package com.ioc.dam_final_project.controller;

import com.ioc.dam_final_project.model.Tarea;
import com.ioc.dam_final_project.serviceImpl.TareaServiceImpl;
import com.ioc.dam_final_project.serviceImpl.TecnicoServiceimpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/tecnico")
public class TecnicoController {

    private final TareaServiceImpl tareaService;
    private final TecnicoServiceimpl tecnicoServiceimpl;

    public TecnicoController(TareaServiceImpl tareaService, TecnicoServiceimpl tecnicoServiceimpl) {
        this.tareaService = tareaService;
        this.tecnicoServiceimpl = tecnicoServiceimpl;
    }

    @GetMapping("/tareas")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Tarea>> getTareas(Principal principal){

        var user = tecnicoServiceimpl.getByEmail(principal.getName());
        return ResponseEntity.ok(tareaService.getTareaTec(user));
    }
}
