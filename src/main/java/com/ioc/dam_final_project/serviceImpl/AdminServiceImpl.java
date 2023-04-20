package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.dto.AdminDTO;
import com.ioc.dam_final_project.dto.TecnicoDTO;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.repository.AdminRepository;
import com.ioc.dam_final_project.service.AdminService;
import com.ioc.dam_final_project.tools.Constantes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase AdminServiceImpl
 *
 * SERA UN SERVICES DE LA CLASE ADMIN, implementa a su vez la Interface 'AdminService' para gestionar los metodos alli suscritos.
 *
 *   Notaciones:
 *
 *   - He declarado a la clase como 'Service' para su mappeo en la base de datos.
 *   - He declarado a la clase con un 'Qualifier' para potenciar el polimorfismo y reuso de multiples Services.
 *   - He usado las notaciones propias de SpringBoot, en combinacion a Java 17 y Loombook, para potenciar al maximo la codificacion.

 *  @author Isabel Calzadilla
 *  @version  1.0
 *  @see  AdminService interface que implementa
 */
@Service
@Qualifier(value = "admin")
public class AdminServiceImpl implements AdminService, Constantes {

    /**
     * TecnicoServiceimpl, refiere al service de clase
     */
    private final TecnicoServiceimpl tecnicoServ;
    /**
     * AdminRepository, refiere al repositorio de clase
     */
    private final AdminRepository adminRepository;

    /**
     * Constructor con 2 parametros
     * @param tecnico entidad Service de la clase
     * @param adminRepository entidad Repository de la clase
     */
    public AdminServiceImpl(TecnicoServiceimpl tecnico, AdminRepository adminRepository) {
        this.tecnicoServ = tecnico;
        this.adminRepository = adminRepository;
    }


    /** Metodo 'profile'
     * Recibe un Usuario y retorna su Perfil
     * @return <ul>
     *  <li>AdminDTO: la instancia con el formato JSON especifico</li>
     *  </ul>
     */
    @Override
    public AdminDTO profile(String email) {
        return AdminDTO.byModel(adminRepository.findAdminByEmail(email).orElseThrow());
    }

    /** Metodo 'getAll'
     * @return <ul>
     *  <li>List de AdminDTO: Recorre a los usuarios y agrupa una Lista de aquellos con rol 'Admin', los cuales retorna</li>
     *  </ul>
     */
    @Override
    public List<AdminDTO> getAll() {
        var admins = new ArrayList<AdminDTO>();
        adminRepository.findAll().forEach(admin -> admins.add(AdminDTO.byModel(admin)));
        return admins;
    }
}
