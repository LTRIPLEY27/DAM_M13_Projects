package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.service.TareaService;
import com.ioc.dam_final_project.service.TecnicoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("tecnico")
public class TecnicoServiceimpl implements TecnicoService {
}
