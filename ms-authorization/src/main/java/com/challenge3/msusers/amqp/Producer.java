package com.challenge3.msusers.amqp;

import com.challenge3.msusers.config.RabbitMQConstants;
import com.challenge3.msusers.payload.EmailDto;
import com.challenge3.msusers.payload.UserDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    private final AmqpTemplate amqpTemplate;

    private final RabbitMQConstants rabbitMQConstants;

    @Autowired
    public Producer(AmqpTemplate amqpTemplate, RabbitMQConstants rabbitMQConstants) {
        this.amqpTemplate = amqpTemplate;
        this.rabbitMQConstants = rabbitMQConstants;
    }

    public void sendMessageUserCreated(UserDto userDto) {
        EmailDto message = new EmailDto();
        message.setFromEmail("juliaodeveloper@gmail.com");
        message.setFromName("Jonathan");
        message.setReplyTo("juliaodeveloper@gmail.com");
        message.setTo(userDto.getEmail());
        message.setSubject("User Created");
        message.setBody("User created successfully");
        message.setContentType("User created or updated");

        amqpTemplate.convertAndSend(rabbitMQConstants.exchange,rabbitMQConstants.routingkey,message);
    }
}

