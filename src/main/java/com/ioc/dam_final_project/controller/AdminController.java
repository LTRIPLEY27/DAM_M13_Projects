package com.ioc.dam_final_project.controller;

import com.ioc.dam_final_project.model.Admin;
import com.ioc.dam_final_project.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping(path = "/admins")
    public List<Admin> getValue(){
        return adminRepository.findAll();
    }

}
