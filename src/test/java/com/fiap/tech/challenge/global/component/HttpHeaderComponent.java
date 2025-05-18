package com.fiap.tech.challenge.global.component;

import com.fiap.tech.challenge.domain.jwt.dto.JwtGeneratePostRequestDTO;
import com.fiap.tech.challenge.domain.jwt.dto.JwtResponseDTO;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse201;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class HttpHeaderComponent {

    final String VALIDATE_JWT_URI = "/api/v1/jwts/validate";
    final String GENERATE_JWT_URI = "/api/v1/jwts/generate";
    final String INVALIDATE_JWT_URI = "/api/v1/jwts/invalidate";

    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_PREFIX_HEADER = "Bearer ";

    private final HttpBodyComponent httpBodyComponent;
    private final TestRestTemplate testRestTemplate;

    @Autowired
    public HttpHeaderComponent(HttpBodyComponent httpBodyComponent, TestRestTemplate testRestTemplate) {
        this.httpBodyComponent = httpBodyComponent;
        this.testRestTemplate = testRestTemplate;
    }

    public HttpHeaders generateHeaderWithoutBearerToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public HttpHeaders generateHeaderWithOwnerBearerToken() {
        return generateHeaderWithBearerToken(new JwtGeneratePostRequestDTO("admin", "admin"));
    }

    public HttpHeaders generateHeaderWithClientBearerToken() {
        return generateHeaderWithBearerToken(new JwtGeneratePostRequestDTO("client", "client"));
    }

    private HttpHeaders generateHeaderWithBearerToken(JwtGeneratePostRequestDTO jwtGeneratePostRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<?> responseEntity = testRestTemplate.exchange(GENERATE_JWT_URI, HttpMethod.POST, new HttpEntity<>(jwtGeneratePostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse201<JwtResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        headers.set(AUTH_HEADER, TOKEN_PREFIX_HEADER + responseObject.getItem().getBearerToken());
        return headers;
    }

    private void mockRequestURI(String requestURI) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI(requestURI);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    public UriComponentsBuilder buildUriWithDefaultQueryParamsGetFilter(String uriString) {
        return UriComponentsBuilder.fromUriString(uriString)
                .queryParam("pageNumber", 1)
                .queryParam("pageSize", 50);
    }
}