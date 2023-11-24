//package com.ssafy.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//import java.util.Properties;
//
//@Configuration
//public class MailConfig {
//
//    @Bean
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.naver.com");
//        mailSender.setPort(465);
//        mailSender.setUsername("guswhd903@naver.com");
//        mailSender.setPassword("asdasdsdfsdf9");
//
//        Properties properties = mailSender.getJavaMailProperties();
//        properties.put("mail.smtp.auth", true);
//        properties.put("mail.smtp.ssl.enable", true);
//        properties.put("mail.smtp.ssl.trust", "smtp.naver.com");
//        properties.put("mail.smtp.starttls.enable", true);
//
//        return mailSender;
//    }
//}
