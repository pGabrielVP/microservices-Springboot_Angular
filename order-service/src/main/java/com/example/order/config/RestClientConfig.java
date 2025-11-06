/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.order.config;

import com.example.order.client.InventoryClient;

import java.time.Duration;

import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;

import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.client.ClientHttpRequestFactory;

import lombok.RequiredArgsConstructor;

import io.micrometer.observation.ObservationRegistry;

/**
 *
 * @author paulo
 */
@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;
    private final ObservationRegistry observationRegistry;

    @Bean
    public InventoryClient inventoryClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(inventoryServiceUrl)
                .requestFactory(getClientRequestFactory())
                .observationRegistry(observationRegistry)
                .build();

        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(InventoryClient.class);
    }

    private ClientHttpRequestFactory getClientRequestFactory() {
        var clientHttpRequestFactorySettings = ClientHttpRequestFactorySettings.defaults()
                .withConnectTimeout(Duration.ofSeconds(3))
                .withReadTimeout(Duration.ofSeconds(3));

        return ClientHttpRequestFactoryBuilder.detect()
                .build(clientHttpRequestFactorySettings);
    }
}
