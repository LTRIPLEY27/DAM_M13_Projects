package com.ioc.dam_final_project.security.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService serviceAuth;

    public AuthenticationController(AuthenticationService serviceAuth) {
        this.serviceAuth = serviceAuth;
    }

    @PostMapping(value ="/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(serviceAuth.authenticate(request));
    }
}
