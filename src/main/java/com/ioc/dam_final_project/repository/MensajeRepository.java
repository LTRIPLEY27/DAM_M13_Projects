package com.ioc.dam_final_project.repository;

import com.ioc.dam_final_project.dto.MensajeDTO;
import com.ioc.dam_final_project.model.Admin;
import com.ioc.dam_final_project.model.Mensaje;
import com.ioc.dam_final_project.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository <Mensaje, Long> {

    List<Mensaje> findMensajeByTecnico(Tecnico tecnico);
    List<Mensaje> findMensajeByAdmin(Admin admin);
}
