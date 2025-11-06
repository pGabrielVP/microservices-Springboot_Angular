package com.example.inventory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldBeAvailable() {
        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/inventory?skuCode=iphone_15&quantity=5")
                .then()
                .statusCode(200)
                .extract().response().as(Boolean.class);

        Assertions.assertTrue(response);
    }

    @Test
    void shouldNotBeAvailable() {
        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/inventory?skuCode=iphone_15&quantity=111")
                .then()
                .statusCode(200)
                .extract().response().as(Boolean.class);

        Assertions.assertFalse(response);
    }
}
