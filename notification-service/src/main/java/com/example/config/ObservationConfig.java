/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.config;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

import jakarta.annotation.PostConstruct;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

/**
 *
 * @author paulo
 */
@Configuration
@RequiredArgsConstructor
public class ObservationConfig {

    private final ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory;

    @PostConstruct
    public void setObservationForKafkaTemplate() {
        concurrentKafkaListenerContainerFactory.getContainerProperties().setObservationEnabled(true);
    }

    @Bean
    ObservedAspect observedAspect(ObservationRegistry registry) {
        return new ObservedAspect(registry);
    }
}
