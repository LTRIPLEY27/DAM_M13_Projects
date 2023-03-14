package com.ioc.dam_final_project.repository;

import com.ioc.dam_final_project.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeRepository extends JpaRepository <Mensaje, Long> {
}
