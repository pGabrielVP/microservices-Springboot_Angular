package com.example.product;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.hamcrest.Matchers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldCreateProduct() {
        String requestBody = """
                            {
                                 "name": "ProdutoNome",
                                 "description": "Descricao",
                                 "skuCode": "Telefone",
                                 "price": 1000
                            }
                             """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/product")
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("ProdutoNome"))
                .body("description", Matchers.equalTo("Descricao"))
                .body("skuCode", Matchers.equalTo("Telefone"))
                .body("price", Matchers.equalTo(1000));
    }

}
