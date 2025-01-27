package com.example.webflux.Config.Mail;

import io.r2dbc.spi.Parameter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.properties.mail.smtp.starttls.enable}")
    private boolean starttls;

    @Value("${mail.properties.mail.smtp.auth}")
    private boolean auth;

    @Bean
    public JavaMailSender javaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        // JavaMail의 properties 설정 (SSL, TLS 설정 등)
        java.util.Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttls); // STARTTLS 활성화
        props.put("mail.smtp.ssl.trust", host); // SSL 인증 허용

        return mailSender;
    }
}
