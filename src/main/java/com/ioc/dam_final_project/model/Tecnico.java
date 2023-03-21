package com.ioc.dam_final_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ioc.dam_final_project.dto.TecnicoDTO;
import com.ioc.dam_final_project.model.Enums.Rol;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

/********************************************************************************************************
 *                                      *****   CLASE TÉCNICO   *****
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
 * - Motivado a que la clase Técnico, estará en relación a otras entidades, he declarado los atributos con las notaciones necesarias para tal fin.
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
public class Tecnico extends User{

    @JsonIgnore
    @OneToMany(mappedBy = "tecnico", fetch = FetchType.LAZY)
    private List<Tarea> tareas;

    @JsonIgnore
    @OneToMany(mappedBy = "tecnico")
    private Set<Mensaje> mensaje;

    public Tecnico(String user, String password, String nombre, String apellido, String email, String telefono, Rol rol, List<Tarea> tareas, Set<Mensaje> mensaje) {
        super(user, password, nombre, apellido, email, telefono, rol);
        this.tareas = tareas;
        this.mensaje = mensaje;
    }


    public static Tecnico byDTO(TecnicoDTO dto){
        return new Tecnico(dto.getUser(), "", dto.getNombre(), dto.getApellido(), dto.getEmail(), dto.getTelefono(), dto.getRol(), null, null);
    }
}
