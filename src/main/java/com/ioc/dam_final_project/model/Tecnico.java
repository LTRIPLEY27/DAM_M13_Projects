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
 *CLASE TECNICO
 *********************************************************************************************************
 * SERA UNA CLASE HIJA DE 'USER'.
 *
 *   Notaciones :
 * ****************
 *   - He declarado a la clase como 'Entity' para su mappeo en la base de datos.
 *   - He usado las notaciones propias de SpringBoot, en combinacion a Java 17 y Loombook, para potenciar al maximo la codificacion.
 *
 *   Atributos :
 * * *************
 * - He declarado los atributos : Private, ya que no seran heredados.
 * - Motivado a que la clase Tecnico, estara en relacion a otras entidades, he declarado los atributos con las notaciones necesarias para tal fin.
 *
 *   Metodos :
 * * *************
 * - He declarado un metodo estatico de la clase, para hacer uso del mismo en la transposicion de los atributos al formato a mostrar al cliente
 *
 *  @author Isabel Calzadilla
 *  @version 1.0
 *  @see User
 * */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tecnico extends User{

    /**
     *  Lista de Tareas relacionadas al Tecnico
     */
    @JsonIgnore
    @OneToMany(mappedBy = "tecnico", fetch = FetchType.LAZY)
    private List<Tarea> tareas;

    /**
     *  Set de Mensajes relacionadas al Tecnico
     */
    @JsonIgnore
    @OneToMany(mappedBy = "tecnico")
    private Set<Mensaje> mensaje;

    /**
     * Constructor con 9 parametros
     * @param user nombre del usuario
     * @param password nombre del usuario
     * @param nombre edad del usuario
     * @param apellido del usuario
     * @param email del usuario
     * @param telefono del usuario
     * @param rol del usuario
     * @param tareas del usuario
     * @param mensaje del usuario
     */
    public Tecnico(String user, String password, String nombre, String apellido, String email, String telefono, Rol rol, List<Tarea> tareas, Set<Mensaje> mensaje) {
        super(user, password, nombre, apellido, email, telefono, rol);
        this.tareas = tareas;
        this.mensaje = mensaje;
    }


    //Metodo publico estatico
    /**
     * Traspola los valores del objeto DAO a una vista JSON personalizada
     * @return <ul>
     *  <li>Admin: la instancia con el formato JSON especifico</li>
     *  </ul>
     */
    public static Tecnico byDTO(TecnicoDTO dto){
        return new Tecnico(dto.getUser(), "", dto.getNombre(), dto.getApellido(), dto.getEmail(), dto.getTelefono(), dto.getRol(), null, null);
    }
}
