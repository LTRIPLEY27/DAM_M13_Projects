package com.ioc.dam_final_project.security.authentication;

import com.ioc.dam_final_project.model.Admin;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.model.User;
import com.ioc.dam_final_project.repository.AdminRepository;
import com.ioc.dam_final_project.repository.TecnicoRepository;
import com.ioc.dam_final_project.security.config.*;
import com.ioc.dam_final_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AdminRepository adminRepository;
    private final TecnicoRepository tecnicoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){
        var jwt = "";
        String encodedPass = new BCryptPasswordEncoder().encode(request.getPassword());
        switch (request.getRol()){
            case ADMIN -> {
                var savedUser = adminRepository.save(new Admin(request.getUser(), encodedPass,  request.getNombre(), request.getApellido(), request.getEmail(), request.getTelefono(), request.getRol(), null));
                jwt = jwtService.generateToken(savedUser);
            }
            case TECNIC -> {
                var savedUser = tecnicoRepository.save(new Tecnico(request.getUser(), encodedPass,  request.getNombre(), request.getApellido(), request.getEmail(), request.getTelefono(), request.getRol(), null, null));
                jwt = jwtService.generateToken(savedUser);
            }
        }

        return AuthenticationResponse.builder().token(jwt).build();
    }

    public  AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var jwt = "";
        if(adminRepository.findAdminByEmail(request.getEmail()).isPresent()){
            jwt = jwtService.generateToken(adminRepository.findAdminByEmail(request.getEmail()).orElseThrow());
            System.out.println("ADMIN, BEARER TOKEN  " + jwt);
        } else if (tecnicoRepository.findTecnicoByEmail(request.getEmail()).isPresent()) {
            jwt = jwtService.generateToken(tecnicoRepository.findTecnicoByEmail(request.getEmail()).orElseThrow());
            System.out.println("TÉCNICO, BEARER TOKEN  " + jwt);
        }

        return AuthenticationResponse.builder().token(jwt).build();
    }

}
