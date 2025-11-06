/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.example.order.dto;

import java.math.BigDecimal;

/**
 *
 * @author paulo
 */
public record OrderRequest(
        Long id,
        String orderNumber,
        String skuCode,
        BigDecimal price,
        Integer quantity,
        UserDetails userDetails) {

    public record UserDetails(String email, String firstName, String lastName) {

    }
}
