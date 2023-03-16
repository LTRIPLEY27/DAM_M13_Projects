package com.ioc.dam_final_project.security.authentication;

import com.ioc.dam_final_project.model.Enums.Rol;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
public class RegisterRequest {

    public  String user;
    public   String password;
    public  String nombre;
    public  String apellido;
    public    String email;
    public    String telefono;
    public  Rol rol;
}
