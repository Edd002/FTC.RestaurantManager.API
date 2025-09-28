package com.fiap.tech.challenge.domain.integration.order;

import com.fiap.tech.challenge.domain.order.dto.OrderPostRequestDTO;
import com.fiap.tech.challenge.domain.order.dto.OrderResponseDTO;
import com.fiap.tech.challenge.domain.order.dto.OrderUpdateStatusPatchRequestDTO;
import com.fiap.tech.challenge.domain.order.dto.OrderUpdateTypePatchRequestDTO;
import com.fiap.tech.challenge.domain.order.enumerated.OrderStatusEnum;
import com.fiap.tech.challenge.domain.order.enumerated.OrderTypeEnum;
import com.fiap.tech.challenge.global.adapter.TimeDeserializerTypeAdapter;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse400;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse403;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse404;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse200;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse201;
import com.fiap.tech.challenge.global.base.response.success.pageable.BasePageableSuccessResponse200;
import com.fiap.tech.challenge.global.component.DatabaseManagementComponent;
import com.fiap.tech.challenge.global.component.HttpBodyComponent;
import com.fiap.tech.challenge.global.component.HttpHeaderComponent;
import com.fiap.tech.challenge.global.util.JsonUtil;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import com.fiap.tech.challenge.global.util.enumerated.DatePatternEnum;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.util.List;

@ExtendWith(value = {SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    private static final String PATH_RESOURCE_ORDER = "/mock/order/order.json";

    private final HttpHeaderComponent httpHeaderComponent;
    private final HttpBodyComponent httpBodyComponent;
    private final TestRestTemplate testRestTemplate;
    private final DatabaseManagementComponent databaseManagementComponent;

    @Autowired
    public OrderControllerTest(HttpHeaderComponent httpHeaderComponent, HttpBodyComponent httpBodyComponent, TestRestTemplate testRestTemplate, DatabaseManagementComponent databaseManagementComponent) {
        this.httpHeaderComponent = httpHeaderComponent;
        this.httpBodyComponent = httpBodyComponent;
        this.testRestTemplate = testRestTemplate;
        this.databaseManagementComponent = databaseManagementComponent;
    }

    @BeforeEach
    public void populateDatabase() {
        List<String> sqlFileScripts = List.of(
                "persistence/state/before_test_state.sql",
                "persistence/city/before_test_city.sql",
                "persistence/address/before_test_address.sql",
                "persistence/loadtable/before_test_load_table.sql",
                "persistence/usertype/before_test_user_type.sql",
                "persistence/user/before_test_user.sql",
                "persistence/jwt/before_test_jwt.sql",
                "persistence/menu/before_test_menu.sql",
                "persistence/restaurant/before_test_restaurant.sql",
                "persistence/restaurantuser/before_test_restaurant_user.sql",
                "persistence/menuitem/before_test_menuitem.sql",
                "persistence/order/before_test_order.sql"
        );
        databaseManagementComponent.populateDatabase(sqlFileScripts);
    }

    @AfterEach
    public void clearDatabase() {
        databaseManagementComponent.clearDatabase();
    }

    @DisplayName("Teste de sucesso - Criar um pedido")
    @Test
    public void createOrderSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        OrderPostRequestDTO orderPostRequestDTO = JsonUtil.objectFromJson("orderPostRequestDTO", PATH_RESOURCE_ORDER, OrderPostRequestDTO.class, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/orders", HttpMethod.POST, new HttpEntity<>(orderPostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse201<OrderResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {}, DatePatternEnum.DATE_FORMAT_HH_mm, new TimeDeserializerTypeAdapter());
        Assertions.assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.CREATED.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getHashId()));
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getRestaurant().getHashId()));
        Assertions.assertTrue(ValidationUtil.isNotBlank(responseObject.getItem().getUser().getHashId()));
    }

    @DisplayName(value = "Teste de falha - Criar um pedido com um usuário não autenticado como cliente")
    @Test
    public void createOrderWithNoClientUserFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        OrderPostRequestDTO orderPostRequestDTO = JsonUtil.objectFromJson("orderPostRequestDTO", PATH_RESOURCE_ORDER, OrderPostRequestDTO.class, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/orders", HttpMethod.POST, new HttpEntity<>(orderPostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse403 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
        Assertions.assertTrue(responseObject.getMessages().contains("O usuário não possui permissão para a operação solicitada."));
    }

    @DisplayName(value = "Teste de falha - Criar um pedido com um ou mais items do menu indisponíveis")
    @Test
    public void createOrderWithNoMenuItemAvailableFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        OrderPostRequestDTO orderPostRequestDTO = JsonUtil.objectFromJson("orderPostRequestDTOWithNoMenuItemAvailable", PATH_RESOURCE_ORDER, OrderPostRequestDTO.class, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/orders", HttpMethod.POST, new HttpEntity<>(orderPostRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse400 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
        Assertions.assertTrue(responseObject.getMessages().contains("Um ou mais items do menu selecionados para o pedido estão indisponíveis."));
    }

    @DisplayName("Teste de sucesso - Atualizar status de um pedido")
    @Test
    public void updateStatusOrderSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        final String EXISTING_ORDER_HASH_ID = "5c679ea52a6743b5b08b43cbb43ae9a7";
        OrderUpdateStatusPatchRequestDTO orderUpdateStatusPatchRequestDTO = JsonUtil.objectFromJson("orderUpdateStatusPatchRequestDTO", PATH_RESOURCE_ORDER, OrderUpdateStatusPatchRequestDTO.class, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/orders/change-status/" + EXISTING_ORDER_HASH_ID, HttpMethod.PATCH, new HttpEntity<>(orderUpdateStatusPatchRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse200<OrderResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {}, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss, new TimeDeserializerTypeAdapter());
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertEquals(EXISTING_ORDER_HASH_ID, responseObject.getItem().getHashId());
        Assertions.assertNotNull(responseObject.getItem().getStatus());
        Assertions.assertEquals(OrderStatusEnum.CONFIRMED, responseObject.getItem().getStatus());
    }

    @DisplayName("Teste de falha - Atualizar status de um pedido entregue")
    @Test
    public void updateStatusOrderDeliveredFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        final String EXISTING_DELIVERED_ORDER_HASH_ID = "c3db1768ee29420cb4eb58e8324943ce";
        OrderUpdateStatusPatchRequestDTO orderUpdateStatusPatchRequestDTO = JsonUtil.objectFromJson("orderUpdateStatusPatchRequestDTO", PATH_RESOURCE_ORDER, OrderUpdateStatusPatchRequestDTO.class, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/orders/change-status/" + EXISTING_DELIVERED_ORDER_HASH_ID, HttpMethod.PATCH, new HttpEntity<>(orderUpdateStatusPatchRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse400 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
        Assertions.assertTrue(responseObject.getMessages().contains("Pedidos entregues não podem ser atualizados."));
    }

    @DisplayName("Teste de falha - Atualizar status de um pedido cancelado")
    @Test
    public void updateStatusOrderCanceledFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        final String EXISTING_CANCELED_ORDER_HASH_ID = "d5d306b4a027436fa31f01dd493c52ad";
        OrderUpdateStatusPatchRequestDTO orderUpdateStatusPatchRequestDTO = JsonUtil.objectFromJson("orderUpdateStatusPatchRequestDTO", PATH_RESOURCE_ORDER, OrderUpdateStatusPatchRequestDTO.class, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/orders/change-status/" + EXISTING_CANCELED_ORDER_HASH_ID, HttpMethod.PATCH, new HttpEntity<>(orderUpdateStatusPatchRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse400 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
        Assertions.assertTrue(responseObject.getMessages().contains("Pedidos cancelados não podem ser atualizados."));
    }

    @DisplayName("Teste de falha - Cancelar um pedido em rota de entrega")
    @Test
    public void updateStatusOrderOnDeliveryRouteToCanceledFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        final String EXISTING_ON_DELIVERY_ROUTE_ORDER_HASH_ID = "900afe639d574545a3c2d1f30388e7a6";
        OrderUpdateStatusPatchRequestDTO orderUpdateStatusPatchRequestDTO = JsonUtil.objectFromJson("orderUpdateStatusPatchRequestDTOCanceled", PATH_RESOURCE_ORDER, OrderUpdateStatusPatchRequestDTO.class, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/orders/change-status/" + EXISTING_ON_DELIVERY_ROUTE_ORDER_HASH_ID, HttpMethod.PATCH, new HttpEntity<>(orderUpdateStatusPatchRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse400 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
        Assertions.assertTrue(responseObject.getMessages().contains("O pedido que está em rota de entrega não pode ser cancelado."));
    }

    @DisplayName("Teste de falha - Atualizar um pedido que está para entrega para aguardando buscar no local")
    @Test
    public void updateStatusOrderForDeliveryToForPickupFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        final String EXISTING_ON_DELIVERY_ROUTE_ORDER_HASH_ID = "900afe639d574545a3c2d1f30388e7a6";
        OrderUpdateStatusPatchRequestDTO orderUpdateStatusPatchRequestDTO = JsonUtil.objectFromJson("orderUpdateStatusPatchRequestDTOWaitingForPickup", PATH_RESOURCE_ORDER, OrderUpdateStatusPatchRequestDTO.class, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/orders/change-status/" + EXISTING_ON_DELIVERY_ROUTE_ORDER_HASH_ID, HttpMethod.PATCH, new HttpEntity<>(orderUpdateStatusPatchRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse400 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
        Assertions.assertTrue(responseObject.getMessages().contains("O pedido que está para entrega não pode ser atualizado para aguardando buscar no local."));
    }

    @DisplayName("Teste de falha - Retroceder status de um pedido ")
    @Test
    public void updateStatusOrderRewindStatusFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerBearerToken();
        final String EXISTING_CONFIRMED_ORDER_HASH_ID = "5c679ea52a6743b5b08b43cbb43ae9a7";
        OrderUpdateStatusPatchRequestDTO orderUpdateStatusPatchRequestDTO = JsonUtil.objectFromJson("orderUpdateStatusPatchRequestDTORequested", PATH_RESOURCE_ORDER, OrderUpdateStatusPatchRequestDTO.class, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/orders/change-status/" + EXISTING_CONFIRMED_ORDER_HASH_ID, HttpMethod.PATCH, new HttpEntity<>(orderUpdateStatusPatchRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse400 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
        Assertions.assertTrue(responseObject.getMessages().contains("O status do pedido não pode ser retrocedido."));
    }

    @DisplayName(value = "Teste de falha - Atualizar um status de pedido com um usuário que não é o dono do restaurante")
    @Test
    public void updateStatusOrderWithoutRestaurantUserAssociationFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithOwnerWithoutRestaurantBearerToken();
        final String EXISTING_ORDER_HASH_ID = "5c679ea52a6743b5b08b43cbb43ae9a7";
        OrderUpdateStatusPatchRequestDTO orderUpdateStatusPatchRequestDTO = JsonUtil.objectFromJson("orderUpdateStatusPatchRequestDTO", PATH_RESOURCE_ORDER, OrderUpdateStatusPatchRequestDTO.class, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/orders/change-status/" + EXISTING_ORDER_HASH_ID, HttpMethod.PATCH, new HttpEntity<>(orderUpdateStatusPatchRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse404 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
        Assertions.assertTrue(responseObject.getMessages().contains("Nenhuma associação para o usuário com o restaurante com hash id 6d4b62960a6aa2b1fff43a9c1d95f7b2 foi encontrada."));
    }

    @DisplayName("Teste de sucesso - Atualizar tipo de um pedido")
    @Test
    public void updateTypeOrderSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        final String EXISTING_ORDER_HASH_ID = "5c679ea52a6743b5b08b43cbb43ae9a7";
        OrderUpdateTypePatchRequestDTO orderUpdateTypePatchRequestDTO = JsonUtil.objectFromJson("orderUpdateTypePatchRequestDTO", PATH_RESOURCE_ORDER, OrderUpdateTypePatchRequestDTO.class, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/orders/change-type/" + EXISTING_ORDER_HASH_ID, HttpMethod.PATCH, new HttpEntity<>(orderUpdateTypePatchRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse200<OrderResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {}, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss, new TimeDeserializerTypeAdapter());
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertEquals(EXISTING_ORDER_HASH_ID, responseObject.getItem().getHashId());
        Assertions.assertNotNull(responseObject.getItem().getType());
        Assertions.assertEquals(OrderTypeEnum.DELIVERY, responseObject.getItem().getType());
    }

    @DisplayName("Teste de sucesso - Atualizar tipo de um pedido entregue")
    @Test
    public void updateTypeOrderDeliveredFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        final String EXISTING_DELIVERED_ORDER_HASH_ID = "c3db1768ee29420cb4eb58e8324943ce";
        OrderUpdateTypePatchRequestDTO orderUpdateTypePatchRequestDTO = JsonUtil.objectFromJson("orderUpdateTypePatchRequestDTO", PATH_RESOURCE_ORDER, OrderUpdateTypePatchRequestDTO.class, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/orders/change-type/" + EXISTING_DELIVERED_ORDER_HASH_ID, HttpMethod.PATCH, new HttpEntity<>(orderUpdateTypePatchRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse400 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
        Assertions.assertTrue(responseObject.getMessages().contains("Pedidos entregues não podem ser atualizados."));
    }

    @DisplayName("Teste de sucesso - Atualizar tipo de um pedido cancelado")
    @Test
    public void updateTypeOrderCanceledFailure() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        final String EXISTING_CANCELED_ORDER_HASH_ID = "d5d306b4a027436fa31f01dd493c52ad";
        OrderUpdateTypePatchRequestDTO orderUpdateTypePatchRequestDTO = JsonUtil.objectFromJson("orderUpdateTypePatchRequestDTO", PATH_RESOURCE_ORDER, OrderUpdateTypePatchRequestDTO.class, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss.getValue());
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/orders/change-type/" + EXISTING_CANCELED_ORDER_HASH_ID, HttpMethod.PATCH, new HttpEntity<>(orderUpdateTypePatchRequestDTO, headers), new ParameterizedTypeReference<>() {});
        BaseErrorResponse400 responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {});
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseObject.getStatus());
        Assertions.assertFalse(responseObject.isSuccess());
        Assertions.assertTrue(ValidationUtil.isNotEmpty(responseObject.getMessages()));
        Assertions.assertTrue(responseObject.getMessages().contains("Pedidos cancelados não podem ser atualizados."));
    }

    @DisplayName("Teste de sucesso - Cancelar um pedido")
    @Test
    public void cancelOrderSuccess() {
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        final String EXISTING_ORDER_HASH_ID = "5c679ea52a6743b5b08b43cbb43ae9a7";
        ResponseEntity<?> responseEntity = testRestTemplate.exchange("/api/v1/orders/cancel/" + EXISTING_ORDER_HASH_ID, HttpMethod.PATCH, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BaseSuccessResponse200<OrderResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {}, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss, new TimeDeserializerTypeAdapter());
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertEquals(EXISTING_ORDER_HASH_ID, responseObject.getItem().getHashId());
        Assertions.assertNotNull(responseObject.getItem().getStatus());
        Assertions.assertEquals(OrderStatusEnum.CANCELED, responseObject.getItem().getStatus());
    }

    @DisplayName(value = "Teste de sucesso - Pedido existe ao verificar por filtro de status")
    @Test
    public void findByFilterStatusSuccess() {
        final String hashIdRestaurant = "6d4b62960a6aa2b1fff43a9c1d95f7b2";
        final OrderStatusEnum orderStatus = OrderStatusEnum.CONFIRMED;
        URI uriTemplate = httpHeaderComponent.buildUriWithDefaultQueryParamsGetFilter("/api/v1/orders/filter")
                .queryParam("hashIdRestaurant", hashIdRestaurant)
                .queryParam("status", orderStatus)
                .build().encode()
                .toUri();
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        ResponseEntity<?> responseEntity = testRestTemplate.exchange(uriTemplate, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BasePageableSuccessResponse200<OrderResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {}, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss, new TimeDeserializerTypeAdapter());
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertEquals(1, responseObject.getList().size());
        Assertions.assertEquals(1, responseObject.getTotalElements());
        Assertions.assertEquals(orderStatus, responseObject.getList().stream().toList().get(NumberUtils.INTEGER_ZERO).getStatus());
    }

    @DisplayName(value = "Teste de sucesso - Pedido existe ao verificar por filtro de tipo")
    @Test
    public void findByFilterTypeSuccess() {
        final String hashIdRestaurant = "6d4b62960a6aa2b1fff43a9c1d95f7b2";
        final OrderTypeEnum orderType = OrderTypeEnum.DELIVERY;
        URI uriTemplate = httpHeaderComponent.buildUriWithDefaultQueryParamsGetFilter("/api/v1/orders/filter")
                .queryParam("hashIdRestaurant", hashIdRestaurant)
                .queryParam("type", orderType)
                .build().encode()
                .toUri();
        HttpHeaders headers = httpHeaderComponent.generateHeaderWithClientBearerToken();
        ResponseEntity<?> responseEntity = testRestTemplate.exchange(uriTemplate, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        BasePageableSuccessResponse200<OrderResponseDTO> responseObject = httpBodyComponent.responseEntityToObject(responseEntity, new TypeToken<>() {}, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss, new TimeDeserializerTypeAdapter());
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK.value(), responseObject.getStatus());
        Assertions.assertTrue(responseObject.isSuccess());
        Assertions.assertEquals(5, responseObject.getList().size());
        Assertions.assertEquals(5, responseObject.getTotalElements());
        Assertions.assertEquals(orderType, responseObject.getList().stream().toList().get(NumberUtils.INTEGER_ZERO).getType());
    }
}