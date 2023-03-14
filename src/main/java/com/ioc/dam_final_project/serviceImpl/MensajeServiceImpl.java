package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.service.MensajeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("mensaje")
public class MensajeServiceImpl implements MensajeService {
}
