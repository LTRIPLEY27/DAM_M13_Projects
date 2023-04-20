package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.dto.MensajeDTO;
import com.ioc.dam_final_project.dto.TecnicoDTO;
import com.ioc.dam_final_project.model.Mensaje;
import com.ioc.dam_final_project.model.Tecnico;
import com.ioc.dam_final_project.repository.MensajeRepository;
import com.ioc.dam_final_project.repository.TareaRepository;
import com.ioc.dam_final_project.repository.TecnicoRepository;
import com.ioc.dam_final_project.service.AdminService;
import com.ioc.dam_final_project.service.TecnicoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase TecnicoServiceimpl
 *
 * SERA UN SERVICES DE LA CLASE Tecnico, implementa a su vez la Interface 'TecnicoService' para gestionar los metodos alli suscritos.
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
@Qualifier(value = "tecnico")
public class TecnicoServiceimpl implements TecnicoService {

    /**
     * TecnicoRepository, refiere al repositorio de clase
     */
    private final TecnicoRepository tecnicoRepository;
    /**
     * MensajeRepository, refiere al repositorio de clase
     */
    private final MensajeRepository mensajeRepository;
    /**
     * TareaRepository, refiere al repositorio de clase
     */
    private final TareaRepository tareaRepository;

    /**
     * Constructor con 3 parametros
     * @param tecnico entidad Repository de la clase
     * @param mensajeRepository entidad Repository de la clase
     * @param tareaRepository entidad Repository de la clase
     */
    public TecnicoServiceimpl(TecnicoRepository tecnico, MensajeRepository mensajeRepository, TareaRepository tareaRepository) {
        this.tecnicoRepository = tecnico;
        this.mensajeRepository = mensajeRepository;
        this.tareaRepository = tareaRepository;
    }

    /** Metodo 'getAll'
     * @return <ul>
     *  <li>List de TecnicoDTO: Recorre todas los Tecnicos contenidos en la base de datos, y los retorna</li>
     *  </ul>
     */
    @Override
    public List<TecnicoDTO> getAll() {
        var byDTO = new ArrayList<TecnicoDTO>();
        for(var i : tecnicoRepository.findAll()){
            byDTO.add(TecnicoDTO.byModel(i));
        }
        return byDTO;
    }

    /** Metodo 'myProfile'
     * Recibe un Usuario y retorna su Perfil
     * @return <ul>
     *  <li>TecnicoDTO: la instancia con el formato JSON especifico</li>
     *  </ul>
     */
    @Override
    public TecnicoDTO myProfile(String tecnico) {
        return TecnicoDTO.byModel(tecnicoRepository.findTecnicoByEmail(tecnico).orElseThrow());
    }

    /** Metodo 'deleteEntity'
     * Recibe un Id  y lo elimina de la base de datos.
     */
    @Override
    public void deleteEntity(Long id) {
        tecnicoRepository.deleteById(id);
    }

}
