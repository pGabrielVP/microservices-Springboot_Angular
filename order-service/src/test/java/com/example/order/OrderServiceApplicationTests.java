package com.example.order;

import com.example.order.event.OrderPlacedEvent;
import com.example.order.stubs.InventoryClientStub;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.hamcrest.Matchers;
import org.hamcrest.MatcherAssert;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.context.annotation.Import;

import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import org.mockito.Mockito;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

    @LocalServerPort
    private Integer port;

    @MockitoBean
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldPlaceOrder() {
        String requestBody = """
                            {
                                "skuCode": "iphone_15",
                                "price": 1000,
                                "quantity": 1,
                                "userDetails": {
                                    "email": "jane.doe@example.com",
                                    "firstName": "Jane",
                                    "lastName": "Doe"
                                  }
                            }
                             """;

        String topic = "order-placed";
        OrderPlacedEvent value = OrderPlacedEvent.builder()
                .email("jane.doe@example.com")
                .firstName("Jane")
                .lastName("Doe")
                .build();

        CompletableFuture<SendResult<String, OrderPlacedEvent>> future = new CompletableFuture<>();
        Mockito.when(kafkaTemplate.send(topic, value)).thenReturn(future);

        InventoryClientStub.stubInventoryCallTrueResponse("iphone_15", 1);

        var responseBody = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/order")
                .then()
                .statusCode(201)
                .extract().body().asString();

        MatcherAssert.assertThat(responseBody, Matchers.is("Pedido feito com sucesso"));
    }

    @Test
    void shouldNotPlaceOrder() {
        String requestBody = """
                            {
                                "skuCode": "iphone_15",
                                "price": 1000,
                                "quantity": 101
                            }
                             """;

        InventoryClientStub.stubInventoryCallFalseResponse("iphone_15", 101);

        var responseBody = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/order")
                .then()
                .statusCode(500)
                .extract().body().asString();
    }
}
