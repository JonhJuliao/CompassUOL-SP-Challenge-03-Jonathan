package com.challenge3.msnotifications.service.impl;

import com.challenge3.msnotifications.payload.EmailDto;
import com.challenge3.msnotifications.service.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmailCreatedUser(EmailDto emailDto) throws MessagingException{

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(emailDto.getFromEmail());
        helper.setReplyTo(emailDto.getFromEmail());
        helper.setTo(emailDto.getTo());
        helper.setSubject(emailDto.getSubject());
        helper.setText(emailDto.getBody(), emailDto.getContentType());

        javaMailSender.send(message);

    }
}
