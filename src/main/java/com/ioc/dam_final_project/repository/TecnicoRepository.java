package com.ioc.dam_final_project.repository;

import com.ioc.dam_final_project.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TecnicoRepository extends JpaRepository <Tecnico, Long> {

    Optional <Tecnico> findTecnicoByEmail(String email);
    Optional <Tecnico> findTecnicoByTareas(String username);
}
