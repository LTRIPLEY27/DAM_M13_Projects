package com.ioc.dam_final_project.security.config;

import com.ioc.dam_final_project.model.User;
import com.ioc.dam_final_project.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogOutService implements LogoutHandler {

    private final UserRepository userRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        jwt = authHeader.substring(7);

        // SETTEAMOS EL VALOR AUTHENTICADO A FALSE CON EL LOGOUT
        authentication.setAuthenticated(false);
    }
}
