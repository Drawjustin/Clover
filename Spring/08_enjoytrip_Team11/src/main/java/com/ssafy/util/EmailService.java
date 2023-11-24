package com.ssafy.util;

import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String text,String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("woori5030@naver.com");		// 보내는 이메일
        message.setTo(email);	// 받는 이메일
        message.setSubject("하이하이");		// 메일 제목
        message.setText("임시 비밀번호는 "+text+"입니다");		// 메일 내용
        javaMailSender.send(message);		// 실제 보내기
    }
}
