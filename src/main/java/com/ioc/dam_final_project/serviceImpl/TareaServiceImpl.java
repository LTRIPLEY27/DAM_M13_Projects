package com.ioc.dam_final_project.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ioc.dam_final_project.dto.TareaDTO;
import com.ioc.dam_final_project.model.Admin;
import com.ioc.dam_final_project.model.Tarea;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.repository.AdminRepository;
import com.ioc.dam_final_project.repository.TareaRepository;
import com.ioc.dam_final_project.repository.TecnicoRepository;
import com.ioc.dam_final_project.service.TareaService;
import com.ioc.dam_final_project.tools.Constantes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier(value = "tarea")
public class TareaServiceImpl implements TareaService, Constantes {

    // INYECCIÓN DE DEPENDENCIAS
    private final TecnicoRepository tecnicoRepository;
    private final AdminRepository adminRepository;
    private final TareaRepository tareaRepository;

    public TareaServiceImpl(TecnicoRepository tecnicoRepository, AdminRepository adminRepository, TareaRepository tareaRepository) {
        this.tecnicoRepository = tecnicoRepository;
        this.adminRepository = adminRepository;
        this.tareaRepository = tareaRepository;
    }

    /**
     * Metodo que recibe un Objeto para crear una tarea
     * @return <ul>
     *  <li>TareaDTO: Retorna un Tarea Entity con los valores editados para el formato JSON</li>
     *  </ul>
     */
    @Override
    public Tarea saveObject(String username, Long id, Object object) {
        var tarea = (Tarea) object;
        var tecnico = tecnicoRepository.findById(id).orElseThrow(); // TODO, VALIDAR QUÉ IDENTIFICACIÓN NOS PASARÁ EL ADMIN PARA EL TECNICO
        var admin = adminRepository.findAdminByEmail(username).orElseThrow();

        tarea.setAdmin(admin);
        tarea.setTecnico(tecnico);

        return tareaRepository.save(tarea);
    }

    @Override
    public List<TareaDTO> total() {
        var object = new ArrayList<TareaDTO>();
        tareaRepository.findAll().forEach(tarea -> object.add(TareaDTO.byModel(tarea)));
        return object;
    }

    @Override
    public List<TareaDTO> getTareaByTecnico(Tecnico tecnico) {

        var object = new ArrayList<TareaDTO>();
        tareaRepository.findTareaByTecnico(tecnico).forEach(tarea -> object.add(TareaDTO.byModel(tarea)));

        return object;
    }

    @Override
    public void deleteEntity(Long id) {
        tareaRepository.deleteById(id);
    }


    // TODO, MUY URGENTE, IDENTIFICAR FINALMENTE QUÉ CAMPOS DE TAREA SE HARÁN UPDATE CADA VEZ QUE SE DESEE?
    @Override
    public Object updateValue(Long id, Object object) throws JsonProcessingException {
        var tar = tareaRepository.findById(id).orElseThrow();
        var newTar = mapper.convertValue(object, TareaDTO.class);

        //Tecnico tecnico = tecnicoRepository.findTecnicoByUser(newTar.getTecnico()).orElseThrow();
        //Admin admin = adminRepository.findAdminByUser(newTar.getAdmin());

        tar.setName(newTar.getName()); // todo, chequear las validaciones en caso de campos null?
        tar.setTarea(newTar.getTarea());
        tar.setEstatus(newTar.getEstatus());
        tar.setFecha_culminacion(newTar.getFecha_culminacion());
        tar.setTecnico(tecnicoRepository.findTecnicoByUser(newTar.getTecnico()).orElseThrow());// transformar de dto a objeto
        tar.setAdmin(adminRepository.findAdminByUser(newTar.getAdmin()));//from dto
        //tar.setUbicacion(newTar.getUbicacion());
        //tar.setMensaje(newTar.getMensaje());

        tareaRepository.save(tar);
        return TareaDTO.byModel(tar);
    }

    @Override
    public TareaDTO searchById(Long id) {
        return TareaDTO.byModel(tareaRepository.findById(id).orElseThrow());
    }


    public boolean isExistence(Long id){
        return tareaRepository.findById(id).isPresent();
    }
}
