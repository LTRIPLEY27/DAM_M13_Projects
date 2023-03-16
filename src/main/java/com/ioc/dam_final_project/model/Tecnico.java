package com.ioc.dam_final_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ioc.dam_final_project.model.Enums.Rol;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tecnico extends User{

    @JsonIgnore
    @OneToMany(mappedBy = "tecnico", fetch = FetchType.LAZY)
    private List<Tarea> tareas;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "id")
    private List <Mensaje> mensaje;

    public Tecnico(String user, String password, String nombre, String apellido, String email, String telefono, Rol rol, List<Tarea> tareas, List<Mensaje> mensaje) {
        super(user, password, nombre, apellido, email, telefono, rol);
        this.tareas = tareas;
        this.mensaje = mensaje;
    }
}
