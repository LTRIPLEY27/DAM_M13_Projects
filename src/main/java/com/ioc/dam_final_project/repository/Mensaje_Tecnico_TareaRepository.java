package com.ioc.dam_final_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Mensaje_Tecnico_TareaRepository extends JpaRepository <Mensaje_Tecnico_TareaRepository, Long> {
}
