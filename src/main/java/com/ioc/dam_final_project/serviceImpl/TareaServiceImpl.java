package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.dto.TareaDTO;
import com.ioc.dam_final_project.model.Tarea;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.model.Ubicacion;
import com.ioc.dam_final_project.repository.AdminRepository;
import com.ioc.dam_final_project.repository.TareaRepository;
import com.ioc.dam_final_project.repository.TecnicoRepository;
import com.ioc.dam_final_project.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

    /**
     * Metodo que recibe un Objeto para crear una tarea
     * @return <ul>
     *  <li>TareaDTO: Retorna un Tarea Entity con los valores editados para el formato JSON</li>
     *  </ul>
     */
    @Override
    public Tarea saveObject(String username, Long id, Object object) {
    //public TareaDTO saveObject(String username, Long id, Object object) {
        var tarea = (Tarea) object;
        var tecnico = tecnicoRepository.findById(id).orElseThrow(); // TODO, VALIDAR QUÉ IDENTIFICACIÓN NOS PASARÁ EL ADMIN PARA EL TECNICO
        var admin = adminRepository.findAdminByEmail(username).orElseThrow();

        tarea.setAdmin(admin);
        tarea.setTecnico(tecnico);
        tareaRepository.save(tarea);

        return tarea;
    }

    @Override
    public List<TareaDTO> total() {
        var object = new ArrayList<TareaDTO>();
        for (var i : tareaRepository.findAll()){
            object.add(TareaDTO.byModel(i));
        }
        return object;
    }

    @Override
    public List<TareaDTO> getTareaTec(Tecnico tecnico) {

        var object = new ArrayList<TareaDTO>();
        for (var i : tareaRepository.findTareaByTecnico(tecnico)){
            object.add(TareaDTO.byModel(i));
        }

        return object;
    }

    @Override
    public void deleteEntity(Long id) {
        tareaRepository.deleteById(id);
    }

    @Override
    public TareaDTO updateValue(Long id, Object object) {
        var tar = tareaRepository.findById(id).orElseThrow();
        var newTar = Tarea.byDTO((TareaDTO) object);

        tar.setName(newTar.getName());
        tar.setTarea(newTar.getTarea());
        tar.setEstatus(newTar.getEstatus());
        tar.setTecnico(newTar.getTecnico());// transformar de dto a objeto
        tar.setAdmin(newTar.getAdmin());//from dto
        tar.setUbicacion(newTar.getUbicacion());
        tar.setMensaje(newTar.getMensaje());

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
