package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.service.TareaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier(value = "tarea")
public class TareaServiceImpl implements TareaService {
}
