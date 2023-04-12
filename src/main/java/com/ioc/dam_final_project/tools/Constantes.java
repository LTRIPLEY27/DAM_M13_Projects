package com.ioc.dam_final_project.tools;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface Constantes {
    ObjectMapper mapper = new ObjectMapper();
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
