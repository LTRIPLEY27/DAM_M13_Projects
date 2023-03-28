package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.dto.AdminDTO;
import com.ioc.dam_final_project.dto.TecnicoDTO;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.repository.AdminRepository;
import com.ioc.dam_final_project.service.AdminService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier(value = "admin")
public class AdminServiceImpl implements AdminService {

    private final TecnicoServiceimpl tecnicoServ;
    private final AdminRepository adminRepository;
    public AdminServiceImpl(TecnicoServiceimpl tecnico, AdminRepository adminRepository) {
        this.tecnicoServ = tecnico;
        this.adminRepository = adminRepository;
    }

    public Tecnico create(Tecnico tecnico){
        return tecnicoServ.saveObject(tecnico);
    }

    @Override
    public List<TecnicoDTO> all() {
        var tecnico = tecnicoServ.getAll();
        return tecnico;
    }

    @Override
    public AdminDTO profile(String email) {
        return AdminDTO.byModel(adminRepository.findAdminByEmail(email).orElseThrow());
    }

    @Override
    public AdminDTO update(Long id, Object object) {
        return null;
    }
}
