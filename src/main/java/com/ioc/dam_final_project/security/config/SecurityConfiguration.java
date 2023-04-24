package com.ioc.dam_final_project.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 *                                      *****   CLASE DE CONFIGURACION DE LA SEGURIDAD A APLICAR   *****
 *
 * SERA UNA CLASE QUE BASICAMENTE PROPORCIONARA LA FILTRACION DE RUTAS MEDIANTE 'ROLES' INDICADOS EN OTROS SERVICIOS.
 *
 *   Notaciones :
 *
 *   - He declarado a la clase como con las notaciones '@Configuration' y '@EnableWebSecurity', para indicar que sea una de las iniciales al lanzarse la aplicacion a declarar pues conformara la configuracion global de las mismas.
 *   - He usado el Bean para invocar al metodo SecurityFilterChain para aplicar los filtros a usar en cada AntMatchers
 *   - He usado las notaciones propias de SpringBoot, en combinacion a Java 17 y Loombook, para potenciar al maximo la codificacion.
 *
 *   Atributos :
 *
 * - He declarado los atributos : Private final, ya que seran 2 Services alternos que necesitare instanciar y con ello me aseguro de tal necesidad.
 * -
 *
 *   Metodos :
 * * *************
 * - He declarado los metodos de acceso como publicos, ya que la idea inicial de este controlador es el acceso global y filtrar a los usuarios a partir de la respuesta.
 * */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration  {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**").permitAll()
               // .requestMatchers("/**").permitAll()
                 // --> OK
                .requestMatchers("/register/**").permitAll()  // --> OK
                //.requestMatchers("/register/**").hasAuthority("ADMIN")
                //.requestMatchers("/tecnico/**").hasAuthority("TECNIC")
                //.requestMatchers("/admin/**").permitAll() // --to restart users
                //.requestMatchers("/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        ;

        return http.build();
    }

}
