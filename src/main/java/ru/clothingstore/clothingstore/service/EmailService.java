package ru.clothingstore.clothingstore.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private String FROM = "ClothingStore";

    private final JavaMailSender javaMailSender;


    public void sendMessage(String email, String username) {
        SimpleMailMessage simpleMailMessage = null;
        try {
            simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(FROM);
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("Registration was successful!!! :)");
            simpleMailMessage.setText("Dear " + username + " thank you for registration!!!");
            javaMailSender.send(simpleMailMessage);

        } catch (Exception e) {
            logger.error("Something went wrong with this email: " + email);
        }
    }


}

