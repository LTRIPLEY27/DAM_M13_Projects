package com.ioc.dam_final_project.security.authentication;

import com.ioc.dam_final_project.security.config.LogOutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService serviceAuth;
    private final LogOutService logOutService;

    public AuthenticationController(AuthenticationService serviceAuth, LogOutService logOutService) {
        this.serviceAuth = serviceAuth;
        this.logOutService = logOutService;
    }

    @PostMapping(value ="/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {

        return ResponseEntity.ok(serviceAuth.authenticate(request));
    }

    @PostMapping(value = "/logout")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> logOut(){

        logOutService.logout(null, null, null);
        return ResponseEntity.ok("bye bye cowboy");
    }
}
