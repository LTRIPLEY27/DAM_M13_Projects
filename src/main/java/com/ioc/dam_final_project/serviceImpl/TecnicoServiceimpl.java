package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.repository.TecnicoRepository;
import com.ioc.dam_final_project.service.TecnicoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier(value = "tecnico")
public class TecnicoServiceimpl implements TecnicoService {

    private final TecnicoRepository tecnicoRepository;

    public TecnicoServiceimpl(TecnicoRepository tecnico) {
        this.tecnicoRepository = tecnico;
    }
    @Override
    public Tecnico saveObject(Tecnico tecnico) {
        return tecnicoRepository.save(tecnico);
    }

    @Override
    public List<Tecnico> getAll() {
        return tecnicoRepository.findAll();
    }

    @Override
    public Tecnico updateObject(Long id,Tecnico tecnico) throws Exception {

        if(!tecnicoRepository.findById(id).isPresent()){
            throw  new Exception("Error, not present ID in the database");
        }
        else{
            var objectToChange = tecnicoRepository.findById(id);
        }
        return null;
    }

    @Override
    public Tecnico getByEmail(String user) {
        return tecnicoRepository.findTecnicoByEmail(user).orElseThrow();
    }
}
