/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.order.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 *
 * @author paulo
 */
public class InventoryClientStub {

    public static void stubInventoryCallTrueResponse(String skuCode, Integer quantity) {
        stubFor(get(urlEqualTo("/api/inventory?skuCode=%s&quantity=%d"
                .formatted(skuCode, quantity)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("true")));
    }

    public static void stubInventoryCallFalseResponse(String skuCode, Integer quantity) {
        stubFor(get(urlEqualTo("/api/inventory?skuCode=%s&quantity=%d"
                .formatted(skuCode, quantity)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("false")));
    }
}
