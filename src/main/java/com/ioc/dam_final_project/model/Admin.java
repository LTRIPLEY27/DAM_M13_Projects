package com.ioc.dam_final_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ioc.dam_final_project.dto.AdminDTO;
import com.ioc.dam_final_project.model.Enums.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

/** ******************************************************************************************************
 * CLASE Admin
 *********************************************************************************************************
 * SERA UNA CLASE HIJA DE 'USER'.
 *
 *   Notaciones:
 *
 *   - He declarado a la clase como 'Entity' para su mappeo en la base de datos.
 *   - He usado las notaciones propias de SpringBoot, en combinacion a Java 17 y Loombook, para potenciar al maximo la codificacion.
 *
 *   Atributos:
 *
 * - He declarado los atributos: Prívate, ya que no seran heredados.
 * - Motivado a que la clase Admin, estara en relacion a otras entidades, he declarado los atributos con las notaciones necesarias para tal fin.
 *
 *   Metodos:
 *
 * - He declarado un metodo estatico de la clase, para hacer uso del mismo en la transposicion de los atributos al formato a mostrar al cliente
 *
 *  @author Isabel Calzadilla
 *  @version  1.0
 *  @see  User
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User{

    /**
     *  Lista de Tareas relacionadas al Admin
     */
    @JsonIgnore
    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY)
    private List <Tarea> tareaList;

    /**
     *  Set de Mensajes relacionado con el Tecnico
     */
    @JsonIgnore
    @OneToMany(mappedBy = "admin")
    private Set<Mensaje> mensaje;

    /**
     * Constructor con 8 parametros
     * @param user nombre del usuario
     * @param password nombre del usuario
     * @param nombre edad del usuario
     * @param apellido del usuario
     * @param email del usuario
     * @param telefono del usuario
     * @param rol del usuario
     * @param tareaList del usuario
     */
    public Admin(String user, String password, String nombre, String apellido, String email, String telefono, Rol rol, List<Tarea> tareaList) {
        super(user, password, nombre, apellido, email, telefono, rol);
        this.tareaList = tareaList;
    }

    //Metodo publico estatico
    /**
     * Traspola los valores del objeto DAO a una vista JSON personalizada
     * @return <ul>
     *  <li>Admin: la instancia con el formato JSON especifico</li>
     *  </ul>
     */
    public static Admin byDTO(AdminDTO admin){
        return new Admin(admin.getUser(), "", admin.getNombre(), admin.getApellido(), admin.getEmail(), admin.getTelefono(), admin.getRol(), null);
    }
}
