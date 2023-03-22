package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.repository.MensajeRepository;
import com.ioc.dam_final_project.service.MensajeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier(value = "mensaje")
public class MensajeServiceImpl implements MensajeService {

    private final MensajeRepository mensajeRepository;

    public MensajeServiceImpl(MensajeRepository mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }

    @Override
    public void deleteEntity(Long id) {
        mensajeRepository.delete(mensajeRepository.findById(id).orElseThrow());
    }
}
