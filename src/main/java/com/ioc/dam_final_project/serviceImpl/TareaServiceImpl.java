package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.model.Mensaje;
import com.ioc.dam_final_project.model.Tarea;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.repository.AdminRepository;
import com.ioc.dam_final_project.repository.TareaRepository;
import com.ioc.dam_final_project.repository.TecnicoRepository;
import com.ioc.dam_final_project.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Qualifier(value = "tarea")
public class TareaServiceImpl implements TareaService {

    // INYECCIÓN DE DEPENDENCIAS
    @Autowired
    TecnicoRepository tecnicoRepository;
    @Autowired
    AdminRepository adminRepository;
    private final TareaRepository tareaRepository;

    public TareaServiceImpl(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    @Override
    public Tarea saveObject(Tarea tarea) {
        var tecnico = tecnicoRepository.findTecnicoByEmail("david@fantasymail.com");
        var admin = adminRepository.findAdminByEmail("eduard@fantasymail.com");

        tarea.setAdmin(admin.orElseThrow());
        tarea.setTecnico(tecnico.orElseThrow());

        return tareaRepository.save(tarea);
    }

    @Override
    public List<Tarea> total() {
        return tareaRepository.findAll();
    }

    @Override
    public List<Tarea> getTareaTec(Tecnico tecnico) {

        var tarea = tareaRepository.findTareaByTecnico(tecnico);
        System.out.println(tarea.get(0).getTecnico().getNombre());
        return tareaRepository.findTareaByTecnico(tecnico);
    }


}
