package com.fiap.tech.challenge.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Value("${server.swagger.base.url}")
    private String baseUrl;

    /*
    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl(baseUrl);
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components().addSecuritySchemes(securitySchemeName,
                        new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ))
                .info(new Info()
                        .title("Fiap Tech Challenge - User Manager API")
                        .version("v1.0")
                        .description("Documentação da API User Manager API.")
                        .contact(new Contact().name("Fiap Tech Challenge Team").email("fiaptechchallenge@tangerino.com.br")))
                .servers(List.of(server));
    }
    */
}