/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.order.service;

import com.example.order.client.InventoryClient;
import com.example.order.dto.OrderRequest;
import com.example.order.event.OrderPlacedEvent;
import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;

import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.stereotype.Service;

/**
 *
 * @author paulo
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest) {
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (isProductInStock) {
            Order order = Order.builder()
                    .orderNumber(UUID.randomUUID().toString())
                    .price(orderRequest.price())
                    .quantity(orderRequest.quantity())
                    .skuCode(orderRequest.skuCode())
                    .build();

            orderRepository.save(order);

            OrderPlacedEvent orderPlacedEvent = OrderPlacedEvent.builder()
                    .firstName(orderRequest.userDetails().firstName())
                    .lastName(orderRequest.userDetails().lastName())
                    .email(orderRequest.userDetails().email())
                    .orderNumber(order.getOrderNumber())
                    .build();

            kafkaTemplate.send("order-placed", orderPlacedEvent);
        } else {
            throw new RuntimeException("Produto com skuCode %s não está em estoque"
                    .formatted(orderRequest.skuCode()));
        }
    }
}
