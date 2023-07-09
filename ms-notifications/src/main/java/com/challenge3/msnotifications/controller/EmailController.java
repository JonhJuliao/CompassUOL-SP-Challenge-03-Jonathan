package com.challenge3.msnotifications.controller;

import com.challenge3.msnotifications.payload.EmailDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class EmailController {

    private final RabbitTemplate rabbitTemplate;

    public EmailController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public ResponseEntity<String> sendEmailCreateUser(@RequestBody EmailDto emailDto) {
        rabbitTemplate.convertAndSend("amq.direct", "email", emailDto);
        return ResponseEntity.ok("Email sent successfully");
    }
}