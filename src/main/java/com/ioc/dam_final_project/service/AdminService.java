package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.dto.AdminDTO;
import com.ioc.dam_final_project.dto.TecnicoDTO;

import java.util.List;

public interface AdminService {

    AdminDTO profile(String email);

    AdminDTO update(Long id, Object object);

    List<AdminDTO> getAll();
}
