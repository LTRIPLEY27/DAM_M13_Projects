package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.service.CoordenadaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier(value = "coordenada")
public class CoordenadaServiceImpl implements CoordenadaService  {
}
