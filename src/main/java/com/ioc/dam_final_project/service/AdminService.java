package com.ioc.dam_final_project.service;

import com.ioc.dam_final_project.dto.AdminDTO;
import com.ioc.dam_final_project.dto.TecnicoDTO;

import java.util.List;

/**
 * Interface AdminService
 *
 * SERA UNA INTERFACE PROTOCOLO A IMPLEMENTAR POR EL SERVICES, extiende de la clase JPARepository para realizar el puente entre la entidad y la base de datos.
 *  @author Isabel Calzadilla
 *  @version  1.0
 */
public interface AdminService {

    AdminDTO profile(String email);

    List<AdminDTO> getAll();
}
