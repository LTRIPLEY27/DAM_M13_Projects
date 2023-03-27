package com.ioc.dam_final_project.service;

import java.util.List;

public interface UserService {

    /**
     *
     */

    Object getProfile(String username);


    List<Object> registers(String username, String value);


    Object update(String username, Object object) throws Exception;

    void deleteRegister(String rol, String typus, Long id);

}
