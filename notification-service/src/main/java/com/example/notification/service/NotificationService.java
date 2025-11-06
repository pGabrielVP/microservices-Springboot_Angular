/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.notification.service;

import com.example.order.event.OrderPlacedEvent;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;

/**
 *
 * @author paulo
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springshop@email.com");
            messageHelper.setTo(orderPlacedEvent.getEmail());
            messageHelper.setSubject(String.format("Seu pedido com OrderNumber %s foi feito com sucesso", orderPlacedEvent.getOrderNumber()));
            messageHelper.setText(String.format("""
                            Olá %s, %s.

                            Seu pedido de número %s foi feito com sucesso.

                            Obrigado
                            Spring Shop
                            """,
                                                orderPlacedEvent.getFirstName(),
                                                orderPlacedEvent.getLastName(),
                                                orderPlacedEvent.getOrderNumber()));
        };
        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            throw new RuntimeException("Ocorreu um erro ao enviar um email para springshop@email.com ", e);
        }
    }
}
