package com.ioc.dam_final_project.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/********************************************************************************************************
 *                                      *****   CLASE JwtAuthenticationFilter   *****
 *********************************************************************************************************
 * SERÁ UNA CLASE QUE HEREDARÁ DE 'OncePerRequestFilter', PARA ASEGURAR EN CADA REQUEST LA APLICACIÓN DE DICHOS FILTROS.
 *
 *   Notaciones :
 * ****************
 *   - He declarado a la clase como con las notaciones '@Component' ,para indicar que podrá ser referenciada.
 *
 *   - He usado las notaciones propias de SpringBoot, en combinación a Java 17 y Loombook, para potenciar al máximo la codificación.
 *
 *   Atributos :
 * * *************
 * - He declarado los atributos : Private final, ya que serán 2 Services alternos que necesitaré instanciar y con ello me aseguro de tal necesidad.
 * -
 *
 *   Métodos :
 * * *************
 * - He declarado los métodos de acceso como protected, ya que así lo proporciona la clase a heredar.
 * */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
