package com.ioc.dam_final_project.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

/**
 * Interface Constantes
 *
 * SERA UNA INTERFACE QUE ALMACENARA DISTINTOS STRINGS A SER IMPLEMENTADOS DESDE MULTIPLES SERVICES Y GESTIONAR LA VALIDACION DE METODOS, ENTIDADES Y ROLES
 *
 *  @author Isabel Calzadilla
 *  @version  1.0
 */
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
    String NAME = "nombre";

    // plural

    String TAREAS = "tareas";
    String UBICACIONES = "ubicaciones";
    String COORDENADAS = "coordenadas";
    String MENSAJES = "mensajes";
    String ADMINS = "admins";
    String TECNICOS = "tecnicos";
    String USERS = "usuarios";


    // filter's

    String STARTING = "fecha_creacion";
    String ENDING = "fecha_culminacion";
}
