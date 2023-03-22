package com.ioc.dam_final_project.model;

import com.ioc.dam_final_project.model.Enums.Rol;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/********************************************************************************************************
*                                      *****   CLASE USER   *****
*********************************************************************************************************
* SERÁ UNA CLASE PADRE CON 2 HEREDEROS : ADMIN Y TÉCNICO.
*
*   Notaciones :
* *****************
 *  - He declarado a la clase como 'Entity' para su mappeo en la base de datos.
*   - He usado las notaciones propias de SpringBoot, en combinación a Java 17 y Loombook, para potenciar al máximo la codificación.
*
*   Atributos :
* * *************
* - He declarado los atributos propios de la clase básica 'Usuario', que serán heredados por ADMIN/USER.
* - He declarado los atributos : Protected, ya que serán heredados.
* - Motivado a la aplicación de la capa de seguridad, he implentado la Interface ' UserDetails' , con los métodos inherentes, para
*   proporcionar los métodos definidos en 'security' tomando valores de los atributos.
*
*
* */

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String user;
    protected String password;
    protected String nombre;
    protected String apellido;
    protected  String email;
    protected  String telefono;
    @Enumerated(EnumType.STRING)
    protected Rol rol;

    public User(String user, String password, String nombre, String apellido, String email, String telefono, Rol rol) {
        this.user = user;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
    }

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
