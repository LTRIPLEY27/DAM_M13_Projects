package com.ioc.dam_final_project.model;

import com.ioc.dam_final_project.dto.UserDTO;
import com.ioc.dam_final_project.model.Enums.Rol;
import jakarta.persistence.*;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/********************************************************************************************************
* CLASE USER
*********************************************************************************************************
* SERA UNA CLASE PADRE CON 2 HEREDEROS: ADMIN Y TECNICO.
*
*   Notaciones:
*
 *  - He declarado a la clase como 'Entity' para su mappeo en la base de datos.
*   - He usado las notaciones propias de SpringBoot, en combinacion a Java 17 y Loombook, para potenciar al maximo la codificacion.
*
*   Atributos:
*
* - He declarado los atributos propios de la clase basica 'Usuario', que seran heredados por ADMIN/USER.
* - He declarado los atributos: Protected, ya que seran heredados.
* - Motivado a la aplicacion de la capa de seguridad, he implentado la Interface ' UserDetails' , con los metodos inherentes, para
*   proporcionar los metodos definidos en 'security' tomando valores de los atributos.
*
*   @author Isabel Calzadilla
 *  @version 1.0
 *  @see  UserDetails para la implementacion del session storage y validacion / autenticacion de Usuario
* */

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User implements UserDetails {
    /**
     * Id del usuario
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     * Username del usuario
     */
    @NotNull
    @Column(unique = true)
    protected String user;

    /**
     * Password del usuario
     */
    @NotNull
    protected String password;

    /**
     * Nombre del usuario
     */
    protected String nombre;

    /**
     * Apellido del usuario
     */
    protected String apellido;

    /**
     * Email del usuario
     */
    @NotNull
    @Column(unique = true)
    protected  String email;

    /**
     * Telefono del usuario
     */
    protected  String telefono;

    /**
     * Rol del usuario
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    protected Rol rol;

    /**
     * Constructor con 7 parametros
     * @param user nombre del usuario
     * @param password nombre del usuario
     * @param nombre edad del usuario
     * @param apellido del usuario
     * @param email del usuario
     * @param telefono del usuario
     * @param rol del usuario
     */
    public User(String user, String password, String nombre, String apellido, String email, String telefono, Rol rol) {
        this.user = user;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
    }

    public static User byDTO(UserDTO adminDTO){
        return new User(adminDTO.getId(), adminDTO.getUser(), adminDTO.getPassword(), adminDTO.getNombre(), adminDTO.getApellido(), adminDTO.getEmail(), adminDTO.getTelefono(), null);
    }

    /**
     * Atributos implementados de la Interfaz UserDetails
     * @return <ul>
     *  <li>SimpleGrantedAuthority: Devuelve una nueva instancia SimpleGrantedAuthority con el parametro del rol a delegar para autenticar</li>
     *  </ul>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
