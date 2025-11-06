/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.order.repository;

import com.example.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author paulo
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

}
