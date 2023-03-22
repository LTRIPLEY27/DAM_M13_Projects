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
/********************************************************************************************************
 *                                      *****   CLASE ADMIN   *****
 *********************************************************************************************************
 * SERÁ UNA CLASE HIJA DE 'USER'.
 *
 *   Notaciones :
 * ****************
 *   - He declarado a la clase como 'Entity' para su mappeo en la base de datos.
 *   - He usado las notaciones propias de SpringBoot, en combinación a Java 17 y Loombook, para potenciar al máximo la codificación.
 *
 *   Atributos :
 * * *************
 * - He declarado los atributos : Private, ya que no serán heredados.
 * - Motivado a que la clase Admin, estará en relación a otras entidades, he declarado los atributos con las notaciones necesarias para tal fin.
 *
 *   Métodos :
 * * *************
 * - He declarado un método estático de la clase, para hacer uso del mismo en la transposición de los atributos al formato a mostrar al cliente
 * */
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
        return new Admin(admin.getUser(), "", admin.getNombre(), admin.getApellido(), admin.getEmail(), admin.getTelefono(), admin.getRol(), null);
    }
}
