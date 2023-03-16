package com.ioc.dam_final_project.controller;

import com.ioc.dam_final_project.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository iUsuarioDAO;

    //private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository iUsuarioDAO/*, BCryptPasswordEncoder bCryptPasswordEncoder*/) {
        this.iUsuarioDAO = iUsuarioDAO;
        //this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /*@PostMapping(value = "/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody LoginRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    /*@Get*Mapping("/response-entity-builder-with-http-headers")
    public ResponseEntity<String> usingResponseEntityBuilderAndHttpHeaders() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Baeldung-Example-Header", "Value-ResponseEntityBuilderWithHttpHeaders");
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("Response with header using ResponseEntity");
    }

    @PostMapping("/usuarios/")
    public User saveUsuario(@RequestBody User user) {
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        iUsuarioDAO.save(user);
        return user;
    }

    @GetMapping("/usuarios/")
    public List<User> getAllUsuarios() {
        return iUsuarioDAO.findAll();
    }

    @GetMapping("/usuarios/{usuario}")
    public User getUsuario(@PathVariable String usuario) {
        return iUsuarioDAO.findUserByUser(usuario);
    }

    @DeleteMapping("/usuarios/{id}")
    public String eliminarUser(@PathVariable(name="id")Long id) {
        iUsuarioDAO.deleteById(id);
        return "User deleted.";
    }*/
}


