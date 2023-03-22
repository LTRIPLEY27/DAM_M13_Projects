package com.ioc.dam_final_project.security.authentication;

import com.ioc.dam_final_project.model.Enums.Rol;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
public class RegisterRequest {
    private String user;
    private String password;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Rol rol;
}
