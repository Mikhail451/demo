package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSender {
    @Autowired
    private JavaMailSender jms;

    @Value("${spring.mail.username}")
    private String username;

    public void sendEmail(String email,String theme,String msg){
        SimpleMailMessage smm=new SimpleMailMessage();
        smm.setText(msg);
        smm.setSubject(theme);
        smm.setTo(email);
        smm.setFrom(username);
        jms.send(smm);
    }
}
