package com.ioc.dam_final_project.repository;

import com.ioc.dam_final_project.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository <Admin, Long> {

    List<Admin> findAdminByApellido(String apellido);
}
