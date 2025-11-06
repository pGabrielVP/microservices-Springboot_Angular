/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.example.product.dto;

import java.math.BigDecimal;

/**
 *
 * @author paulo
 */
public record ProductResponse(
        String id,
        String name,
        String description,
        String skuCode,
        BigDecimal price) {

}
