package com.fiap.tech.challenge.config.properties;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

@Profile("test")
@Configuration
public class H2ConfigTest {

    /*
    Enquanto os testes estiverem em execução ou debugando (breakpoint) é possível acessar a estrutura do banco de dados enquanto está em memória em http://localhost:8084/cuc/h2-console com as credenciais:

    Driver Class: org.h2.Driver
    JDBC URL: jdbc:h2:tcp://localhost:9092/mem:db
    User Name: sa
    Password:

    O breakpoint pode ser configurado para suspender toda a VM ou apenas uma única thread.
    No IntelliJ, é possível definir essa configuração clicando com o botão direito do mouse no respectivo breakpoint.
    Caso estejam configurados para suspender toda a VM e, cada breakpoint também bloqueará o acesso ao H2-Console.
    */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer(new String[]{"-tcp", "-tcpAllowOthers", "-tcpPort", "9092"});
    }
}