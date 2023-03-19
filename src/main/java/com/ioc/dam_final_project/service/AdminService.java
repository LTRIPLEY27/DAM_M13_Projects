package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.dto.TecnicoDTO;
import com.ioc.dam_final_project.model.Tecnico;

import java.util.List;

public interface AdminService {

    List<TecnicoDTO> all();
}
