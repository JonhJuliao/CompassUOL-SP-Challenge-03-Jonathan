package com.challenge3.msnotifications.amqp;

import com.challenge3.msnotifications.payload.EmailDto;
import com.challenge3.msnotifications.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
@Slf4j
public class NotificationListener {

    private final EmailService emailService;

    public NotificationListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "email")
    public void processUserCreatedMessage(EmailDto message) throws MessagingException {
        log.info("Mensagem {}", message);
        emailService.sendEmailCreatedUser(message);
    }
}

