package com.challenge3.msnotifications.service;

import com.challenge3.msnotifications.payload.EmailDto;

import javax.mail.MessagingException;

public interface EmailService {

    void sendEmailCreatedUser(EmailDto emailDto) throws MessagingException;
}
