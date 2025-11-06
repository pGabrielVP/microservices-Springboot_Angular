/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.product.repository;

import com.example.product.model.Product;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author paulo
 */
public interface ProductRepository extends MongoRepository<Product, String> {

}
