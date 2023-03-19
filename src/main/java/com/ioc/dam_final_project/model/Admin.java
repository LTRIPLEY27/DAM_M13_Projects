package com.ioc.dam_final_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ioc.dam_final_project.dto.AdminDTO;
import com.ioc.dam_final_project.model.Enums.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User{

    @JsonIgnore
    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY)
    private List <Tarea> tareaList;

    public Admin(String user, String password, String nombre, String apellido, String email, String telefono, Rol rol, List<Tarea> tareaList) {
        super(user, password, nombre, apellido, email, telefono, rol);
        this.tareaList = tareaList;
    }

    public static Admin byDTO(AdminDTO admin){
        return new Admin(admin.getUser(), "", admin.getNombre(), admin.getApellido(), admin.getEmail(), admin.getTelefono(), admin.getRol(), admin.getTarea());
    }
}
