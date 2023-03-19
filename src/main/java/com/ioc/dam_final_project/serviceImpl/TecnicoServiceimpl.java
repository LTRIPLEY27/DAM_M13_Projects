package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.model.Mensaje;
import com.ioc.dam_final_project.model.Tarea;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.repository.MensajeRepository;
import com.ioc.dam_final_project.repository.TareaRepository;
import com.ioc.dam_final_project.repository.TecnicoRepository;
import com.ioc.dam_final_project.service.TecnicoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Qualifier(value = "tecnico")
public class TecnicoServiceimpl implements TecnicoService {

    private final TecnicoRepository tecnicoRepository;
    private final MensajeRepository mensajeRepository;
    private final TareaRepository tareaRepository;

    public TecnicoServiceimpl(TecnicoRepository tecnico, MensajeRepository mensajeRepository, TareaRepository tareaRepository) {
        this.tecnicoRepository = tecnico;
        this.mensajeRepository = mensajeRepository;
        this.tareaRepository = tareaRepository;
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

    public Mensaje posting(Mensaje mensaje) {

        var tarea = tareaRepository.findById(1L).orElseThrow();
        var tecnico = tecnicoRepository.findById(2L).orElseThrow();

        mensaje.setTecnico(tecnico);
        mensaje.setTarea(tarea);

        tarea.getMensaje().add(mensaje);
        tecnico.getMensaje().add(mensaje);

        tecnicoRepository.save(tecnico);
        tareaRepository.save(tarea);

        return mensajeRepository.save(mensaje);
    }
}
