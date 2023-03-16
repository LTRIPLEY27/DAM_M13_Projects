package com.ioc.dam_final_project.security.authentication;

import com.ioc.dam_final_project.model.User;
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
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    //String user, String password, String nombre, String apellido, String email, String telefono, Rol rol
    public AuthenticationResponse register(RegisterRequest request){
        String encodedPass = new BCryptPasswordEncoder().encode(request.getPassword());
        var user = new User(request.getUser(), encodedPass,  request.getNombre(), request.getApellido(), request.getEmail(), request.getTelefono(), request.getRol());

        var savedUser = repository.save(user);
        var jwt = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwt).build();
    }

    /*public  AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()

        ));

        System.out.println(request.getEmail());
        var user = repository.findUserByEmail(request.getEmail()).orElseThrow();
        var jwt = jwtService.generateToken(user);
        System.out.println("USER BY EMAIL" +user);
        System.out.println("USER BY EMAIL" + jwt);

        return AuthenticationResponse.builder().token(jwt).build();
    }*/

    public  AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()

        ));

        System.out.println(request.getEmail());
        var user = repository.findUserByEmail(request.getEmail()).orElseThrow();
        var jwt = jwtService.generateToken(user);
        System.out.println("USER BY EMAIL" +user);
        System.out.println("USER BY EMAIL" + jwt);

        return AuthenticationResponse.builder().token(jwt).build();
    }

    /*private void saveUserToken(User user, String jwt) {
        var token = Token.builder()
                .user(user)
                .token(jwt)
                .type(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }*/

    /*private void revokeAllUserToken(User user){
        var validToken = tokenRepository.findAllTokens(user.getId());

        if(validToken.isEmpty())
            return;

        validToken.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validToken);
    }*/
}
