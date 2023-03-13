package com.ioc.dam_final_project.repository;

import com.ioc.dam_final_project.model.Coordenadas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordenadasRepository extends JpaRepository <Coordenadas, Long> {
}
