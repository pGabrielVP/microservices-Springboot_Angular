/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.order.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.util.logging.Slf4j;

import org.springframework.web.service.annotation.GetExchange;

import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author paulo
 */
@Slf4j
public interface InventoryClient {

    Logger log = LoggerFactory.getLogger(InventoryClient.class);

    @GetExchange(value = "/api/inventory")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);

    default boolean fallbackMethod(String code, Integer quantity, Throwable throwable) {
        log.info("Não foi possível recuperar o item com skuCode {}, motivo: {}", code, throwable.getMessage());
        return false;
    }
}
