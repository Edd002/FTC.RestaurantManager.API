package com.fiap.tech.challenge;

import com.fiap.tech.challenge.config.properties.JwtSecurityProperty;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@Log
@EnableCaching
@ServletComponentScan
@SpringBootApplication(exclude = {RepositoryRestMvcAutoConfiguration.class})
@EnableConfigurationProperties(value = {JwtSecurityProperty.class})
public class FTCMsUserManagerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FTCMsUserManagerApiApplication.class, args);
    }
}