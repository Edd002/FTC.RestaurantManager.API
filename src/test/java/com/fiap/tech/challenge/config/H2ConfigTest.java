package com.fiap.tech.challenge.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

@Profile("test")
@Configuration
public class H2ConfigTest {

    /*
    Enquanto os testes estiverem em execução ou em pausa (thread breakpoint) é possível acessar a estrutura do banco de dados enquanto está em memória em http://localhost:8084/restaurant-manager/h2-console com as credenciais:
    Driver Class: org.h2.Driver
    JDBC URL: jdbc:h2:tcp://localhost:9092/mem:db
    User Name: sa
    Password:
    O breakpoint pode ser configurado para suspender apenas uma única thread para que o acesso ao H2-console seja possível (https://hrrbrt.medium.com/using-h2-during-test-debugging-in-spring-f6a3db355e3a).
    */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }
}