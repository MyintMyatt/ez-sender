package com.ezsender.EzSender.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailSenderService {

    private final JavaMailSender mailSender;

    public void sendOtp(String to, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
