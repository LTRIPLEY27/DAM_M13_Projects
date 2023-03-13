package com.ioc.dam_final_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User{

    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY)
    private List <Tarea> tareaList;
}
