package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.service.UbicacionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("ubicacion")
public class UbicacionServiceImpl implements UbicacionService {
}
