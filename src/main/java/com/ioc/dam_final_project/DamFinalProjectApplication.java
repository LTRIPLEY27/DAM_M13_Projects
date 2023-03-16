package com.ioc.dam_final_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DamFinalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DamFinalProjectApplication.class, args);
    }

}
