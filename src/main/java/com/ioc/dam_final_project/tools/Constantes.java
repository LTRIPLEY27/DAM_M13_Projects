package com.ioc.dam_final_project.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.time.format.DateTimeFormatter;

public interface Constantes {
    ObjectMapper mapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());

    String TAREA = "tarea";
    String UBICACION = "ubicacion";
    String COORDENADA = "coordenada";
    String MENSAJE = "mensaje";
    String ADMIN = "admin";
    String TECNICO = "tecnico";
    String USER = "usuario";

    // plural

    String TAREAS = "tareas";
    String UBICACIONES = "ubicaciones";
    String COORDENADAS = "coordenadas";
    String MENSAJES = "mensajes";
    String ADMINS = "admins";
    String TECNICOS = "tecnicos";

    String USERS = "usuarios";
}
