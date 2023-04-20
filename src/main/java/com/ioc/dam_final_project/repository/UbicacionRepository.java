package com.ioc.dam_final_project.repository;

import com.ioc.dam_final_project.model.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UbicacionRepository extends JpaRepository <Ubicacion, Long> {

    Optional<Ubicacion> findByTarea_Id(Long id);
}
