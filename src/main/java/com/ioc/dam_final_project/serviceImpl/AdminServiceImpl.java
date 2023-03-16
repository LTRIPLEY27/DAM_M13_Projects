package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.service.AdminService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier(value = "admin")
public class AdminServiceImpl implements AdminService {

    private final TecnicoServiceimpl tecnicoServ;
    public AdminServiceImpl(TecnicoServiceimpl tecnico) {
        this.tecnicoServ = tecnico;
    }

    public Tecnico create(Tecnico tecnico){
        return tecnicoServ.saveObject(tecnico);
    }

    public List<Tecnico> getAll(){
        return tecnicoServ.getAll();
    }
}
