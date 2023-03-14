package com.ioc.dam_final_project.serviceImpl;

import com.ioc.dam_final_project.service.AdminService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("admin")
public class AdminServiceImpl implements AdminService {

}
