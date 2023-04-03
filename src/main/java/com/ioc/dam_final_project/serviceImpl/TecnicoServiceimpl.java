package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.dto.MensajeDTO;
import com.ioc.dam_final_project.dto.TecnicoDTO;
import com.ioc.dam_final_project.model.Mensaje;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.repository.MensajeRepository;
import com.ioc.dam_final_project.repository.TareaRepository;
import com.ioc.dam_final_project.repository.TecnicoRepository;
import com.ioc.dam_final_project.service.TecnicoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<TecnicoDTO> getAll() {
        var byDTO = new ArrayList<TecnicoDTO>();
        for(var i : tecnicoRepository.findAll()){
            byDTO.add(TecnicoDTO.byModel(i));
        }
        return byDTO;
    }

    /*@Override
    public Tecnico updateObject(Long id,Tecnico tecnico) throws Exception {

        if(!tecnicoRepository.findById(id).isPresent()){
            throw  new Exception("Error, not present ID in the database");
        }
        else{
            var objectToChange = tecnicoRepository.findById(id);
        }
        return null;
    }*/

    @Override
    public Tecnico getByEmail(String user) {
        return tecnicoRepository.findTecnicoByEmail(user).orElseThrow();
    }

    @Override
    public TecnicoDTO myProfile(String tecnico) {
        return TecnicoDTO.byModel(tecnicoRepository.findTecnicoByEmail(tecnico).orElseThrow());
    }


    /**
     * Actualiza un objeto 'Tecnico' en la base de datos
     * @return <ul>
     *  <li>Tecnico: valores actuales</li>
     *  </ul>
     */
    /*@Override// verificar los campos a actualizar
    public TecnicoDTO update(Long id, Object object) {
        var aux = Tecnico.byDTO((TecnicoDTO) object);
        var tecnic = tecnicoRepository.findById(id).orElseThrow();

        tecnic.setUser(aux.getUser());
        tecnic.setPassword(new BCryptPasswordEncoder().encode(aux.getPassword()));
        tecnic.setNombre(aux.getNombre());
        tecnic.setApellido(aux.getApellido());
        tecnic.setEmail(aux.getEmail());
        tecnic.setTelefono(aux.getTelefono());
        tecnic.setRol(aux.getRol());


        return TecnicoDTO.byModel(tecnic);
    }*/

    @Override
    public void deleteEntity(Long id) {
        tecnicoRepository.deleteById(id);
    }

}
